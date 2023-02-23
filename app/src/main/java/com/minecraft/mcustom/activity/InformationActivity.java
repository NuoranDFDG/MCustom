package com.minecraft.mcustom.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gjylibrary.GjySerialnumberLayout;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.minecraft.mcustom.R;
import com.minecraft.mcustom.entity.Result;
import com.minecraft.mcustom.util.file.DataFileUtility;
import com.minecraft.mcustom.util.gson.JsonBean;
import com.minecraft.mcustom.util.https.HttpsUtil;
import com.minecraft.mcustom.util.jgeUtil;

import java.util.Objects;

public class InformationActivity extends AppCompatActivity {

    private String code;

    public static InformationActivity instance;
    final private static Gson gson= JsonBean.getGson();

    @SuppressLint("SdCardPath")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        instance=this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = getIntent();
            if (Objects.equals(intent.getStringExtra("extra_data"), "off")) {
                LoginActivity.instance.finish();
            }

        }
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_information);
        Toolbar toolbar = findViewById(R.id.toolbar_info);
        toolbar.setTitle("MCustom");
        toolbar.inflateMenu(R.menu.menu_browser);

        EditText user = findViewById(R.id.user_id);
        EditText pwd = findViewById(R.id.user_pwd);
        EditText mail = findViewById(R.id.user_mailbox);
        GjySerialnumberLayout verification = findViewById(R.id.verification_code);
        Button mailbox = findViewById(R.id.mailbox_verification);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button infoRun = findViewById(R.id.info_run);

        String token = DataFileUtility.readFileToData("fixBer", getApplicationContext());
        infoRun.setOnClickListener(view -> {
            String user_text = user.getText().toString();
            String pwd_text = pwd.getText().toString();
            if (!user_text.equals("") &&jgeUtil.checkUserString(user_text)) {
                if (!pwd_text.equals("") &&jgeUtil.checkUserString(pwd_text)) {
                    new Thread(() -> {
                        String result;
                        try {
                            result = HttpsUtil.HttpsPost("{'name':'"
                                    + user.getText().toString()
                                    + "','pwd':'"
                                    + pwd.getText().toString()
                                    + "','token':'"
                                    + token
                                    + "'}", getApplicationContext(), "set", "info");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        Result rs = null;
                        try {
                            rs = gson.fromJson(result, Result.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        assert rs != null;
                        switch (rs.getCode()) {
                            case 200:
                                String tokenUser = (String) rs.getResult();
                                DataFileUtility.saveFileToData("token", tokenUser, getApplicationContext());
                                if (!mail.getText().toString().equals("") &&code.length()==4) {
                                    new Thread(()->{
                                        String resultMailbox;
                                        try {
                                            resultMailbox = HttpsUtil.HttpsPost("{'mailbox':'"
                                                            + mail.getText().toString()
                                                            + "','code':'"
                                                            + code
                                                            + "','token':'"
                                                            + token
                                                            + "'}", InformationActivity.this, "verification", "mailbox");
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                        Result rsMailbox = null;
                                        try {
                                            rsMailbox = gson.fromJson(resultMailbox, Result.class);
                                        } catch (JsonSyntaxException e) {
                                            e.printStackTrace();
                                        }
                                        assert rsMailbox != null;
                                        switch (rsMailbox.getCode()) {
                                            case 200:
                                                boolean delete = DataFileUtility.deleteSingleFile("/data/data/com.minecraft.mcustom/files/fixBer");
                                                if (delete) {
                                                    view.postDelayed(() -> Toast.makeText(InformationActivity.this, "欢迎使用", Toast.LENGTH_SHORT).show(), 10);
                                                }
                                                Intent intent = new Intent(InformationActivity.this, MainActivity.class);
                                                intent.putExtra("extra_data","offI");
                                                startActivity(intent);
                                                break;
                                            case 458:
                                                view.postDelayed(() -> Toast.makeText(InformationActivity.this, "验证码不存在或已过期", Toast.LENGTH_SHORT).show(), 10);
                                                break;
                                            case 800:
                                                Result finalRsMailbox = rsMailbox;
                                                view.postDelayed(() -> Toast.makeText((Context) InformationActivity.this, (CharSequence) finalRsMailbox.getResult(), Toast.LENGTH_SHORT).show(), 10);
                                                break;
                                        }
                                    }).start();
                                } else {
                                    Intent intent = new Intent(InformationActivity.this, MainActivity.class);
                                    intent.putExtra("extra_data","offI");
                                    startActivity(intent);
                                }
                                break;
                            case 400:
                                view.postDelayed(() -> Toast.makeText(InformationActivity.this, "格式错误", Toast.LENGTH_SHORT).show(), 10);
                                break;
                            case 800:
                                view.postDelayed(() -> Toast.makeText(InformationActivity.this, "请求拒绝", Toast.LENGTH_SHORT).show(), 10);
                                break;
                        }
                    }).start();
                } else {
                    pwd.setError("密码格式不正确");
                    pwd.setText("");
                }
            } else {
                user.setError("用户名格式不正确");
                user.setText("");
            }

        });
        verification.setOnInputListener(code -> {
            if (code.length()==4) {
                this.code = code;
            }
        });
        mailbox.setOnClickListener(view -> new Thread(() -> {
            if (jgeUtil.checkMailboxString(mail.getText().toString())) {
                String result;
                try {
                    result = HttpsUtil.HttpsPost("{'mailbox':'"
                            + mail.getText().toString()
                            + "','token':'"
                            + token
                            + "'}", InformationActivity.this, "send", "mailbox");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Result rs = null;
                try {
                    rs = gson.fromJson(result, Result.class);
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                }
                assert rs != null;
                switch (rs.getCode()) {
                    case 200:
                        view.postDelayed(() -> Toast.makeText(InformationActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show(), 10);
                        break;
                    case 800:
                        view.postDelayed(() -> Toast.makeText(InformationActivity.this, "验证失败", Toast.LENGTH_SHORT).show(), 10);
                        break;
                    case 878:
                        String date = (String) rs.getResult();
                        view.postDelayed(() -> Toast.makeText(InformationActivity.this, "验证码已发送,请等待" + date + "秒", Toast.LENGTH_SHORT).show(), 10);
                        break;
                    case 755:
                        view.postDelayed(() -> Toast.makeText(InformationActivity.this, "此邮箱已被绑定", Toast.LENGTH_SHORT).show(), 10);
                }
            } else {
                view.postDelayed(() -> {
                    mail.setError("邮箱格式错误");
                    mail.setText("");
                }, 10);
            }
        }).start());
    }
}