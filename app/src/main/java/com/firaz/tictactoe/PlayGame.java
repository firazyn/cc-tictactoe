package com.firaz.tictactoe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class PlayGame extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_main);

        Button buttonPVP = findViewById(R.id.btn_pvsp);
        Button buttonPVC = findViewById(R.id.btn_pvsc);

        buttonPVP.setOnClickListener(this);
        buttonPVC.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_pvsp) {
            Intent gvn = new Intent(PlayGame.this, GivePlayerName.class);
            startActivity(gvn);
        } else {
            Intent pvc = new Intent(PlayGame.this, PlayerVersusComputer.class);
            startActivity(pvc);
        }
    }
}