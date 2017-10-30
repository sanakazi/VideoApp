package com.example.sanakazi.videoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LaunchActivity extends AppCompatActivity {
    Button ivPlay,ivPlay1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ivPlay=(Button)findViewById(R.id.ivPlay);
        ivPlay1=(Button)findViewById(R.id.ivPlay1);
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LaunchActivity.this,BasicVideoActivity.class);
                startActivity(i);
            }
        });

        ivPlay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LaunchActivity.this,VideoControlsActivity.class);
                startActivity(i);
            }
        });
    }
}
