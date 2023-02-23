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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.gjylibrary.GjySerialnumberLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.minecraft.mcustom.R;
import com.minecraft.mcustom.activity.InformationActivity;
import com.minecraft.mcustom.entity.AllPlayer;
import com.minecraft.mcustom.util.file.DataFileUtility;
import com.minecraft.mcustom.activity.WebViewActivity;
import com.minecraft.mcustom.entity.Result;
import com.minecraft.mcustom.util.gson.JsonBean;
import com.minecraft.mcustom.util.http.HttpUrl;
import com.minecraft.mcustom.util.https.HttpsUtil;

import org.angmarch.views.NiceSpinner;

import java.util.List;
import java.util.Objects;

public class Register extends DialogFragment {

    final private static Gson gson= JsonBean.getGson();

    private String playerName;

    protected FragmentActivity mActivity;

    public void setmActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

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
        NiceSpinner niceSpinner = view.findViewById(R.id.player_name);
        new Thread(()->{
            String jsonAllPlayer;
            try {
                jsonAllPlayer = HttpsUtil.HttpsPost("", getContext(), "get", "all_player");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            AllPlayer allPlayer = null;
            try {
                allPlayer = gson.fromJson(jsonAllPlayer,AllPlayer.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            assert allPlayer != null;

            if (allPlayer.getCode() == 200) {
                List allPlayerList = allPlayer.getPlayer();
                allPlayerList.add(0, "请选择玩家");
                niceSpinner.postDelayed(() -> {
                    niceSpinner.attachDataSource(allPlayerList);
                    niceSpinner.setOnSpinnerItemSelectedListener((parent, view1, position, id) -> {
                        String item = (String) parent.getItemAtPosition(position);
                        playerName = item;
                        if (!Objects.equals(item, "请选择玩家")) {
                            new Thread(()->{
                                String json;
                                try {
                                    json = HttpsUtil.HttpsPost("{'code':200,'playerID':'"+item+"'}", getContext(), "send", "verification");
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                Result result = gson.fromJson(json, Result.class);
                                niceSpinner.postDelayed(() -> {
                                    switch (result.getCode()) {
                                        case 200:
                                            Toast.makeText(getActivity(), "验证码发送成功,可能会有10秒延迟", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 800:
                                            Toast.makeText(getActivity(), "玩家不存在,请刷新", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 878:
                                            String date = (String) result.getResult();
                                            Toast.makeText(getActivity(), "验证码已发送,请等待" + date + "秒", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }, 10);
                            }).start();
                            }
                        });
                    }, 10);
            }
        }).start();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) GjySerialnumberLayout wind = view.findViewById(R.id.verification_code);
        wind.setOnInputListener(code -> {
            if (code.length()==6&&!Objects.equals(playerName, "请选择玩家")) {
                new Thread(()->{
                    Gson gson= JsonBean.getGson();
                    if (!code.contains("0")) {
                        String post;
                        try {
                            post = HttpsUtil.HttpsPost("{'code':" + code
                                    + ",'id':'"
                                    + playerName
                                    + "'}", getContext(), "register", "code");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        Result result = null;
                        try {
                            result = gson.fromJson(post, Result.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        assert result != null;
                        Result finalResult = result;
                        wind.postDelayed(() -> {
                            switch (finalResult.getCode()) {
                                case 200:
                                    String token = (String) finalResult.getResult();
                                    DataFileUtility.saveFileToData("fixBer", token, getContext());
                                    Intent intent = new Intent(getActivity(), InformationActivity.class);
                                    intent.putExtra("extra_data","off");
                                    startActivity(intent);
                                    break;
                                case 400:
                                    Toast.makeText(getActivity(), "玩家不存在", Toast.LENGTH_SHORT).show();
                                    break;
                                case 800:
                                    Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
                                    break;
                                case 878:
                                    Toast.makeText(getActivity(), "玩家已注册", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        },10);
                    }

                }).start();
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvLoginPrivacyPolicy = view.findViewById(R.id.verification_about);
        tvLoginPrivacyPolicy.setText("注册即表示您已阅读并同意");
        SpannableString clickString1 = new SpannableString("用户协议");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("extra_data", HttpUrl.getBaseUrl());
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
                intent.putExtra("extra_data", HttpUrl.getBaseUrl());
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