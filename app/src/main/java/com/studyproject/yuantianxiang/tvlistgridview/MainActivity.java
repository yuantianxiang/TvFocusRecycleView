package com.studyproject.yuantianxiang.tvlistgridview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_leftRight,btn_leftRightTop;
    Intent intent=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_leftRight= (Button) findViewById(R.id.btn_leftRight);
        btn_leftRightTop= (Button) findViewById(R.id.btn_leftRightTop);

        //左右布局
        btn_leftRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(MainActivity.this,LeftRightActivity.class);
                startActivity(intent);
            }
        });
        //左右上布局
        btn_leftRightTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(MainActivity.this,LeftTopRightActivity.class);
                startActivity(intent);
            }
        });
    }

}
