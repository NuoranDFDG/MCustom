package com.minecraft.mcustom.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.minecraft.mcustom.activity.MainActivity;
import com.minecraft.mcustom.entity.Result;
import com.minecraft.mcustom.util.file.DataFileUtility;
import com.minecraft.mcustom.util.gson.JsonBean;
import com.minecraft.mcustom.util.http.HttpUrl;
import com.minecraft.mcustom.util.http.OKHttpUtil;
import com.minecraft.mcustom.util.jgeUtil;

public class Mailbox extends DialogFragment {

    private String code;
    protected FragmentActivity mActivity;
    final private static Gson gson= JsonBean.getGson();

    public void setmActivity(FragmentActivity mActivity) {
        this.mActivity = mActivity;
    }

    //自定义样式，注：此处主要设置弹窗的宽高
    @Override
    public int getTheme() {
        return R.style.InputDialogStyle;
    }

    @SuppressLint("CommitTransaction")
    public void show() {
        mActivity.runOnUiThread(() -> {
            if (isActivityAlive()) {
                FragmentManager fm = mActivity.getSupportFragmentManager();
                Fragment prev = fm.findFragmentByTag(getClass().getSimpleName());
                if (prev != null) fm.beginTransaction().remove(prev);
                if (!Mailbox.this.isAdded()) {
                    Mailbox.super.show(fm, getClass().getSimpleName());
                }
            }
        });
    }

    @Override
    public void dismiss() {
        mActivity.runOnUiThread(() -> {
            if (isActivityAlive()) {
                Mailbox.super.dismissAllowingStateLoss();
            }
        });
    }

    private boolean isActivityAlive() {
        return mActivity != null && !mActivity.isFinishing() && !mActivity.isDestroyed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.binding_mailbox_page, container, false);
        String token = DataFileUtility.readFileToData("fixBer", getContext());
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText mail = v.findViewById(R.id.user_mailbox_get);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) GjySerialnumberLayout verification = v.findViewById(R.id.verification_code_get);
        Button mailbox = v.findViewById(R.id.mailbox_verification_get);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button infoRun = v.findViewById(R.id.mailbox_verification_run);
        verification.setOnInputListener(code -> {
            if (code.length()==4) {
                this.code = code;
            }
        });
        infoRun.setOnClickListener(view -> {
            if (!mail.getText().toString().equals("") && code.length() == 4) {
                new Thread(() -> {
                    String resultMailbox = OKHttpUtil.postAsyncRequest(HttpUrl.getBaseUrl(), "{'mailbox':'"
                            + mail.getText().toString()
                            + "','code':'"
                            + code
                            + "','token':'"
                            + token
                            + "'}", "verification", "mailbox");
                    if (resultMailbox != null) {
                        Result rsMailbox = null;
                        try {
                            rsMailbox = gson.fromJson(resultMailbox, Result.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        assert rsMailbox != null;
                        switch (rsMailbox.getCode()) {
                            case 200:
                                @SuppressLint("SdCardPath") boolean delete = DataFileUtility.deleteSingleFile("/data/data/com.minecraft.mcustom/files/fixBer");
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                                break;
                            case 458:
                                view.postDelayed(() -> Toast.makeText(getContext(), "验证码不存在或已过期", Toast.LENGTH_SHORT).show(), 10);
                                break;
                            case 800:
                                Result finalRsMailbox = rsMailbox;
                                view.postDelayed(() -> Toast.makeText((Context) getContext(), (CharSequence) finalRsMailbox.getResult(), Toast.LENGTH_SHORT).show(), 10);
                                break;
                        }
                    }
                }).start();
            } else {
                view.postDelayed(() -> Toast.makeText((Context) getContext(), (CharSequence) "请输入正确的信息", Toast.LENGTH_SHORT).show(), 10);
            }
        });
        mailbox.setOnClickListener(view -> new Thread(() -> {
            if (jgeUtil.checkMailboxString(mail.getText().toString())) {
                String result = OKHttpUtil.postAsyncRequest(HttpUrl.getBaseUrl(), "{'mailbox':'"
                        + mail.getText().toString()
                        + "','token':'"
                        + token
                        + "'}", "send", "mailbox");
                if (result != null) {
                    Result rs = null;
                    try {
                        rs = gson.fromJson(result, Result.class);
                    } catch (JsonSyntaxException e) {
                        e.printStackTrace();
                    }
                    assert rs != null;
                    switch (rs.getCode()) {
                        case 200:
                            view.postDelayed(() -> Toast.makeText(getContext(), "验证码已发送", Toast.LENGTH_SHORT).show(), 10);
                            break;
                        case 800:
                            view.postDelayed(() -> Toast.makeText(getContext(), "验证失败", Toast.LENGTH_SHORT).show(), 10);
                            break;
                        case 878:
                            String date = (String) rs.getResult();
                            view.postDelayed(() -> Toast.makeText(getContext(), "验证码已发送,请等待" + date + "秒", Toast.LENGTH_SHORT).show(), 10);
                            break;
                        case 755:
                            view.postDelayed(() -> Toast.makeText(getContext(), "此邮箱已被绑定", Toast.LENGTH_SHORT).show(), 10);
                    }
                }
            } else {
                view.postDelayed(() -> {
                    mail.setError("邮箱格式错误");
                    mail.setText("");
                }, 10);
            }
        }).start());
        return v;
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