package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Animation topA , buttonA;
    ImageView image;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this , Second.class));
                finish();
            }
        } , 5000);

        topA = AnimationUtils.loadAnimation(this , R.anim.top_a);
        buttonA = AnimationUtils.loadAnimation(this , R.anim.bottom_a);

        image = findViewById(R.id.imageV);
        image.setAnimation(topA);

        text = findViewById(R.id.textV);
        text.setAnimation(buttonA);
    }
}