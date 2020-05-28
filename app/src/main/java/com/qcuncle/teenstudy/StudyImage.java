package com.qcuncle.teenstudy;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;

public class StudyImage extends AppCompatActivity {
    private ImageView imageView;
    private String str1 = "m.html",str2 = "images/end.jpg";
    private String str3;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //实现状态栏文字颜色为暗色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_study_image);
        init();
    }
    private void init() {
        //设置imageView
        imageView = findViewById(R.id.imageView);
        try {
            str3 = MainActivity.path.replaceAll(str1,"");
            url = str3 + str2;
            Glide.with(this).load(url).into(imageView);
            //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //设置标题栏
        ImageButton imgBtn_back = (ImageButton) findViewById(R.id.include_activity_title).findViewById(R.id.imgBtn_back);
        ImageButton imgBtn_menu = (ImageButton) findViewById(R.id.include_activity_title).findViewById(R.id.imgBtn_menu);
        TextView title = (TextView) findViewById(R.id.include_activity_title).findViewById(R.id.tv_title);
        int num1 = Integer.valueOf(MainActivity.jishu);
        int num2 = Integer.valueOf(MainActivity.qishu);
        String jishu = b(num1);
        String qishu = b(num2);
        String string = "“青年大学习”第" + jishu + "季" + "第" + qishu + "期";
        title.setText(string);
        imgBtn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StudyImage.this.finish();
            }
        });
        imgBtn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlterDialog();
            }
        });
    }
    /**
     * 普通dialog
     */
    private void showAlterDialog(){
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(StudyImage.this);
        alterDiaglog.setIcon(R.drawable.user);//图标
        alterDiaglog.setTitle("开发者：QCuncle");//文字
        alterDiaglog.setMessage("version 1.0");//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton("更新github@qcuncle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://github.com/QCuncle/TeenStudy");//此处填链接
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        //显示
        alterDiaglog.show();
    }


    public void onBackPressed() {
        StudyImage.this.finish();
    }

    public  final String b(int i){
        if(i<=10) {
            return c(i);
        }
        StringBuilder a = new StringBuilder(d(i/10));
        a.append(c(i%10));
        return a.toString();
    }
    public final String c(int i) {
        switch (i) {
            case 1:
                return "一";
            case 2:
                return "二";
            case 3:
                return "三";
            case 4:
                return "四";
            case 5:
                return "五";
            case 6:
                return "六";
            case 7:
                return "七";
            case 8:
                return "八";
            case 9:
                return "九";
            case 10:
                return "十";
            default:
                return "";
        }
    }
    public String d(int i) {
        switch (i) {
            case 1:
                return "十";
            case 2:
                return "二十";
            case 3:
                return "三十";
            case 4:
                return "四十";
            case 5:
                return "五十";
            case 6:
                return "六十";
            case 7:
                return "七十";
            case 8:
                return "八十";
            case 9:
                return "九十";
            default:
                return "";
        }
    }
}