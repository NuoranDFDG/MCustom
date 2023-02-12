package com.minecraft.mcustom.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.minecraft.mcustom.R;
import com.minecraft.mcustom.activity.WebViewActivity;

import java.io.IOException;

public class About extends DialogFragment {

    private MediaPlayer mediaPlayer;
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
                if (!About.this.isAdded()) {
                    About.super.show(fm, getClass().getSimpleName());
                }
            }
        });
    }

    @Override
    public void dismiss() {
        mActivity.runOnUiThread(() -> {
            if (isActivityAlive()) {
                About.super.dismissAllowingStateLoss();
            }
        });
    }

    private boolean isActivityAlive() {
        return mActivity != null && !mActivity.isFinishing() && !mActivity.isDestroyed();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about,container,false);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("http://music.163.com/song/media/outer/url?id=425828457.mp3");
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        ImageView about1 = view.findViewById(R.id.github_about1);
        Glide.with(this).load("https://avatars.githubusercontent.com/nuoranDFDG").into(about1);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView forgetSkip1 = view.findViewById(R.id.about_name1);
        forgetSkip1.setText(" ");
        SpannableString clickString1 = new SpannableString("nuoranDFDG");
        clickString1.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("extra_data", "https://github.com/nuoranDFDG");
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgetSkip1.append(clickString1);
        forgetSkip1.setHighlightColor(Color.TRANSPARENT);
        forgetSkip1.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView about2 = view.findViewById(R.id.github_about2);
        Glide.with(this).load("https://avatars.githubusercontent.com/RikkaApps").into(about2);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView forgetSkip2 = view.findViewById(R.id.about_name2);
        forgetSkip2.setText(" ");
        SpannableString clickString2 = new SpannableString("RikkaApps");
        clickString2.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("extra_data", "https://github.com/RikkaApps");
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgetSkip2.append(clickString2);
        forgetSkip2.setHighlightColor(Color.TRANSPARENT);
        forgetSkip2.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView about3 = view.findViewById(R.id.github_about3);
        Glide.with(this).load("https://avatars.githubusercontent.com/babynanxi").into(about3);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView forgetSkip3 = view.findViewById(R.id.about_name3);
        forgetSkip3.setText(" ");
        SpannableString clickString3 = new SpannableString("babynanxi");
        clickString3.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("extra_data", "https://github.com/babynanxi");
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString3.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        forgetSkip3.append(clickString3);
        forgetSkip3.setHighlightColor(Color.TRANSPARENT);
        forgetSkip3.setMovementMethod(LinkMovementMethod.getInstance());

        TextView tvLoginPrivacyPolicy = view.findViewById(R.id.github_main);
        tvLoginPrivacyPolicy.setText("在 ");
        SpannableString clickString = new SpannableString("github");
        clickString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("extra_data", "https://github.com/nuoranDFDG/MCustom");
                startActivity(intent);
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getResources().getColor(R.color.textButton));
            }
        }, 0, clickString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLoginPrivacyPolicy.append(clickString);
        tvLoginPrivacyPolicy.append(new SpannableString(" 查看源码"));
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
    
}