package com.example.vismaya.courtcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

public class MainActivity extends AppCompatActivity {

    int aScore;
    int bScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void reset(View view) {
        aScore = 0;
        bScore = 0;
        adisplayScore(aScore);
        bdisplayScore(bScore);
    }

    public void athreeScore(View view) {
        aScore = aScore + 3;
        adisplayScore(aScore);
    }

    public void atwoScore(View view) {
        aScore = aScore + 2;
        adisplayScore(aScore);
    }

    public void aoneScore(View view) {
        aScore = aScore + 1;
        adisplayScore(aScore);
    }

    public void bthreeScore(View view) {
        bScore = bScore + 3;
        bdisplayScore(bScore);
    }

    public void btwoScore(View view) {
        bScore = bScore + 2;
        bdisplayScore(bScore);
    }

    public void boneScore(View view) {
        bScore = bScore + 1;
        bdisplayScore(bScore);
    }

    private void adisplayScore(int number){
        TextView afinalScore = (TextView) findViewById(R.id.team_a_score);
        afinalScore.setText(String.valueOf(number));
    }

    private void bdisplayScore(int number){
        TextView bfinalScore = (TextView) findViewById(R.id.team_b_score);
        bfinalScore.setText(String.valueOf(number));
    }


}
