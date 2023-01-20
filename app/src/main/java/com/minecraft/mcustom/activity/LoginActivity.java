package com.minecraft.mcustom.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.minecraft.mcustom.util.StatusBarUtil;

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
import android.view.Window;
import android.widget.TextView;

import com.minecraft.mcustom.R;
import com.minecraft.mcustom.entity.Data;
import com.minecraft.mcustom.entity.Result;
import com.minecraft.mcustom.entity.User;
import com.minecraft.mcustom.util.RSAUtils;
import com.minecraft.mcustom.util.DatabaseUtil;
import com.minecraft.mcustom.util.jgeUtil;
import com.minecraft.mcustom.util.lisUtil;
import com.minecraft.mcustom.util.getKey;

import java.security.PrivateKey;

public class LoginActivity extends AppCompatActivity {

    private TextView tvLoginPrivacyPolicy;
    private TextView verificationSkip;
    private TextView forgetSkip;
    private PrivateKey key;
    @SuppressLint("StaticFieldLeak")
    public static LoginActivity instance = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                lisUtil.useDo.add(new String("您于" + lisUtil.getDate() + "登录"));
            }
        }).start();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //login
        setContentView(R.layout.login_layout);
        setTvLoginPrivacyPolicySpecialText();
        setVerificationSkipText();
        setForgetSkipText();
        try {
            key = getKey.getPublicKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
        instance = this;
        final Button test = findViewById(R.id.test55);
        final Button login = findViewById(R.id.login);
        final EditText userId = findViewById(R.id.userID);
        final EditText pwd = findViewById(R.id.pwd);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = userId.getText().toString();
                if(!jgeUtil.checkString(id)){
                    if (!id.equals("")) {
                        Toast.makeText(LoginActivity.this,"用户名非法",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(LoginActivity.this,"用户名为空",Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                String encData = RSAUtils.decrypt(String.format("{'id': %s, 'password': %s}", id, pwd.getText().toString()), (PrivateKey) key);

                Result result=DatabaseUtil.other(new Data(encData), "user", "login");

                boolean flag = result.getCode()==200;
                Toast.makeText(LoginActivity.this, flag?"登录成功":"用户名或密码错误", Toast.LENGTH_SHORT).show();
                if(flag){
                    User.setAid(id);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void setForgetSkipText() {
        forgetSkip = findViewById(R.id.forgetPassword);
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
        verificationSkip = findViewById(R.id.verification);
        verificationSkip.setText(" ");
        SpannableString clickString1 = new SpannableString("验证码登录");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(LoginActivity.this, "页面跳转", Toast.LENGTH_SHORT).show();
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
        tvLoginPrivacyPolicy = findViewById(R.id.protocol);
        tvLoginPrivacyPolicy.setText("登录即表示您已阅读并同意");
        SpannableString clickString1 = new SpannableString("用户协议");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(LoginActivity.this, "跳转默认浏览器", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(LoginActivity.this, "跳转默认浏览器", Toast.LENGTH_SHORT).show();
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