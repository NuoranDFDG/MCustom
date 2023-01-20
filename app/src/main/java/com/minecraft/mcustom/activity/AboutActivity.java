package com.minecraft.mcustom.activity;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.minecraft.mcustom.R;

public class AboutActivity extends AppCompatActivity {

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        setContentView(R.layout.about);
        setTvLoginPrivacyPolicySpecialText();
        ImageView about1 = findViewById(R.id.github_about1);
        Glide.with(this).load("https://avatars.githubusercontent.com/nuoranDFDG").into(about1);
        findViewById(R.id.about_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(0, 0);
            }
        });

    }

    private void setTvLoginPrivacyPolicySpecialText() {
        TextView tvLoginPrivacyPolicy = findViewById(R.id.github_main);
        tvLoginPrivacyPolicy.setText("在 ");
        SpannableString clickString1 = new SpannableString("github");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(AboutActivity.this, "跳转默认浏览器", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLoginPrivacyPolicy.append(clickString1);
        tvLoginPrivacyPolicy.append(new SpannableString(" 查看源码"));
        tvLoginPrivacyPolicy.setHighlightColor(Color.TRANSPARENT);
        tvLoginPrivacyPolicy.setMovementMethod(LinkMovementMethod.getInstance());

    }

}