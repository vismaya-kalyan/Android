package com.example.vismaya.taptap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                if (v.getId() == R.id.button) {
                    int i = Integer.parseInt(((Button) v).getText().toString());
                    ((Button) v).setText(Integer.toString(i));
                    Random gen = new Random();
                    if ((gen.nextInt(10 - 1 + 1) + 1 == 10) || (i < 50)) {
                        crack();
                        ((Button) v).setOnClickListener(null);
                    }
                }
            }
        });
    }


    private void crack() {
        visualFX();
        touchFX();
    }

    private void visualFX() {
        findViewById(R.id.activity_main).setBackgroundResource(R.drawable.cracked_screen);
    }

    private void touchFX() {
        Vibrator mv = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mv.vibrate(new long[]{ 0, 50, 0 }, -1);
    }
}
