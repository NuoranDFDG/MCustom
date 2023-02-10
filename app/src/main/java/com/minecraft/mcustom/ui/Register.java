package com.minecraft.mcustom.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.gjylibrary.GjySerialnumberLayout;
import com.google.gson.Gson;
import com.minecraft.mcustom.R;
import com.minecraft.mcustom.util.jgeUtil;
import com.minecraft.mcustom.activity.WebViewActivity;
import com.minecraft.mcustom.entity.Result;
import com.minecraft.mcustom.util.gson.JsonBean;
import com.minecraft.mcustom.util.http.HttpUrl;
import com.minecraft.mcustom.util.http.OKHttpUtil;

public class Register extends DialogFragment {

    final private static Gson gson= JsonBean.getGson();

    protected FragmentActivity mActivity;

    public void setmActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    //自定义样式，注：此处主要设置弹窗的宽高
    @Override
    public int getTheme() {
        return R.style.InputDialogStyle;
    }

    @SuppressLint("CommitTransaction")
    public void show(){
        mActivity.runOnUiThread(() -> {
            if (isActivityAlive()){
                FragmentManager fm = mActivity.getSupportFragmentManager();
                Fragment prev = fm.findFragmentByTag(getClass().getSimpleName());
                if (prev != null) fm.beginTransaction().remove(prev);
                if (!Register.this.isAdded()) {
                    Register.super.show(fm, getClass().getSimpleName());
                }
            }
        });
    }

    @Override
    public void dismiss() {
        mActivity.runOnUiThread(() -> {
            if (isActivityAlive()) {
                Register.super.dismissAllowingStateLoss();
            }
        });
    }

    private boolean isActivityAlive() {
        return mActivity != null && !mActivity.isFinishing() && !mActivity.isDestroyed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register,container,false);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) GjySerialnumberLayout wind = view.findViewById(R.id.verification_code);
        wind.setOnInputListener(new GjySerialnumberLayout.OnInputListener() {
            @Override
            public void onSucess(String code) {
                if (code.length()==6&&jgeUtil.isInteger(code)) {
                    Gson gson= JsonBean.getGson();
                    String post = OKHttpUtil.postAsyncRequest(HttpUrl.getBaseUrl(), "{'code':" + code
                            + "}", "register", "code");
                    if (post!=null) {
                        Result result = gson.fromJson(post, Result.class);
                        switch (result.getCode()) {
                            case 200:

                            case 400:
                        }
                    }
                }
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvLoginPrivacyPolicy = view.findViewById(R.id.verification_about);
        tvLoginPrivacyPolicy.setText("注册即表示您已阅读并同意");
        SpannableString clickString1 = new SpannableString("用户协议");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("extra_data", "http://101.43.157.180/user/agreement");
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLoginPrivacyPolicy.append(clickString1);
        tvLoginPrivacyPolicy.append(new SpannableString("和"));
        SpannableString clickString2 = new SpannableString("隐私策略");
        clickString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("extra_data", "http://101.43.157.180/privacy/policy");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLoginPrivacyPolicy.append(clickString2);
        tvLoginPrivacyPolicy.setHighlightColor(Color.TRANSPARENT);
        tvLoginPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog;
        dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        return dialog;
    }

}