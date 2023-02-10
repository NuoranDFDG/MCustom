package com.minecraft.mcustom.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.minecraft.mcustom.ui.Register;
import com.minecraft.mcustom.util.HashUtil;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.widget.TextView;

import com.minecraft.mcustom.R;
import com.minecraft.mcustom.entity.Result;
import com.minecraft.mcustom.entity.User;
import com.minecraft.mcustom.util.DatabaseUtil;
import com.minecraft.mcustom.util.jgeUtil;
import com.minecraft.mcustom.util.lisUtil;

public class LoginActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static LoginActivity instance = null;
    private boolean isRefuse;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Aauthority.instance.finish();
        }
        new Thread(() -> {
            // 交互
            lisUtil.useDo.add("您于" + lisUtil.getDate() + "登录");
        }).start();

        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //login
        setContentView(R.layout.login_layout);
        setTvLoginPrivacyPolicySpecialText();
        setVerificationSkipText();
        setForgetSkipText();
        instance = this;
        final Button login = findViewById(R.id.login);
        final EditText userId = findViewById(R.id.userID);
        final EditText pwd = findViewById(R.id.pwd);
        login.setOnClickListener(view -> {
            String id = userId.getText().toString();
            if(!jgeUtil.checkString(id)){
                if (!id.equals("")) {
                    userId.setError("用户名非法");
                } else {
                    userId.setError("用户名为空");
                }
                return;
            }
            String userIDText = userId.getText().toString();
            String passwordIDText = pwd.getText().toString();
            String hash = HashUtil.getHash(userIDText+passwordIDText);
            User user = new User(userIDText, passwordIDText, hash);
            Result result=DatabaseUtil.other(user, "user", "login");

            assert result != null;
            boolean flag = result.getCode()==200;
            Toast.makeText(LoginActivity.this, flag?"登录成功":"用户名或密码错误", Toast.LENGTH_SHORT).show();
            if(flag){
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setForgetSkipText() {
        TextView forgetSkip = findViewById(R.id.forgetPassword);
        forgetSkip.setText(" ");
        SpannableString clickString1 = new SpannableString("忘记密码");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(LoginActivity.this, "页面跳转", Toast.LENGTH_SHORT).show();
                // Intent intent = new Intent(LoginActivity.this, ResetActivity.class);
                // startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgetSkip.append(clickString1);
        forgetSkip.setHighlightColor(Color.TRANSPARENT);
        forgetSkip.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void setVerificationSkipText() {
        TextView verificationSkip = findViewById(R.id.verification);
        verificationSkip.setText(" ");
        SpannableString clickString1 = new SpannableString("验证码注册");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Register register = new Register();
                register.setmActivity(LoginActivity.this);
                register.show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        verificationSkip.append(clickString1);
        verificationSkip.setHighlightColor(Color.TRANSPARENT);
        verificationSkip.setMovementMethod(LinkMovementMethod.getInstance());
    }
    private void setTvLoginPrivacyPolicySpecialText() {
        TextView tvLoginPrivacyPolicy = findViewById(R.id.protocol);
        tvLoginPrivacyPolicy.setText("登录即表示您已阅读并同意");
        SpannableString clickString1 = new SpannableString("用户协议");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
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
                Intent intent = new Intent(LoginActivity.this, WebViewActivity.class);
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

    }


}