package com.example.test.webview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1=(Button)findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        btn2=(Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(this);
        btn3=(Button)findViewById(R.id.btn3);
        btn3.setOnClickListener(this);
        btn4=(Button)findViewById(R.id.btn4);
        btn4.setOnClickListener(this);
        btn5=(Button)findViewById(R.id.btn5);
        btn5.setOnClickListener(this);
        btn6=(Button)findViewById(R.id.btn6);
        btn6.setOnClickListener(this);
        btn7=(Button)findViewById(R.id.btn7);
        btn7.setOnClickListener(this);
        btn8=(Button)findViewById(R.id.btn8);
        btn8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                Intent intent1=new Intent(MainActivity.this,WebViewActivity.class);
                intent1.putExtra("url","net");
                startActivity(intent1);
                break;
            case R.id.btn2:
                Intent intent2=new Intent(MainActivity.this,WebViewActivity.class);
                intent2.putExtra("url","assets");
                startActivity(intent2);
                break;
            case R.id.btn3:
                Intent intent3=new Intent(MainActivity.this,WebViewActivity.class);
                intent3.putExtra("url","sdcard");
                startActivity(intent3);
                break;
            case R.id.btn4:
                Intent intent4=new Intent(MainActivity.this,WebViewActivity.class);
                intent4.putExtra("url","AcallJS1");
                startActivity(intent4);
                break;
            case R.id.btn5:
                Intent intent5=new Intent(MainActivity.this,WebViewActivity.class);
                intent5.putExtra("url","AcallJS2");
                startActivity(intent5);
                break;
            case R.id.btn6:
                Intent intent6=new Intent(MainActivity.this,WebViewActivity.class);
                intent6.putExtra("url","JScallA1");
                startActivity(intent6);
                break;
            case R.id.btn7:
                Intent intent7=new Intent(MainActivity.this,WebViewActivity.class);
                intent7.putExtra("url","JScallA2");
                startActivity(intent7);
                break;
            case R.id.btn8:
                Intent intent8=new Intent(MainActivity.this,WebViewActivity.class);
                intent8.putExtra("url","JScallA3");
                startActivity(intent8);
                break;
        }
    }
}
