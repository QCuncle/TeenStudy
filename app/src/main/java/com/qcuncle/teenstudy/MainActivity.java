package com.qcuncle.teenstudy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et1, et2, et3;
    private Button studyBtn;
    public static String jishu, qishu, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        et1 = (EditText) findViewById(R.id.jishu);
        et2 = (EditText) findViewById(R.id.qishu);
        studyBtn = (Button) findViewById(R.id.Btn);
        et3 = (EditText) findViewById(R.id.study_address);
        et1.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)}); //限制输入位数
        et2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(2)});

        studyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取青年大学校季数，期数和地址
                jishu = et1.getText().toString();
                qishu = et2.getText().toString();
                path = et3.getText().toString().trim();
                //跳转到大学习界面
                if (jishu.length() == 0 || qishu.length() == 0) {
                    Toast.makeText(getApplicationContext(), "请输入季数和期数", Toast.LENGTH_LONG).show();
                } else if (!path.startsWith("http:")) {
                    Toast.makeText(getApplicationContext(), "请查看链接是否输入正确", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MainActivity.this, StudyImage.class);
                    startActivity(intent);
                    //MainActivity.this.finish();
                }
            }
        });
    }
}
