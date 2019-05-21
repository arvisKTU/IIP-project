package com.example.iip_projektas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class LoseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lose);

        Bundle bundle;
        bundle = getIntent().getExtras();
        int random_number = bundle.getInt("skaicius");
        int score = bundle.getInt("score");
        int highscore=0;
        SharedPreferences sharedPref = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        highscore=sharedPref.getInt("highscore",0);

        if (score>highscore)
        {
            highscore=score;
            SharedPreferences.Editor sharedPrefEditor = getSharedPreferences("Settings",Context.MODE_PRIVATE).edit();
            sharedPrefEditor.putInt("highscore",highscore);
            sharedPrefEditor.commit();
        }
        SharedPreferences.Editor sharedPrefEditor = getSharedPreferences("Settings",Context.MODE_PRIVATE).edit();
        sharedPrefEditor.putInt("score",score);
        sharedPrefEditor.commit();

        TextView resField = findViewById(R.id.loseText);
        resField.setText("You lost. My number was: "+random_number);

        TextView scoreField = findViewById(R.id.loseScore);
        scoreField.setText("Score: "+score);

        TextView hiField = findViewById(R.id.loseHi);
        hiField.setText("Highscore: "+highscore);
    }
}
