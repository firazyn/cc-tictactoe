package com.firaz.tictactoe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_main);
    }

    public void pvp(View v) {
        Intent pvp = new Intent(PlayGame.this, PlayerVersusPlayer.class);
        startActivity(pvp);
    }

    public void pvc(View v) {
        Intent pvc = new Intent(PlayGame.this, PlayerVersusComputer.class);
        startActivity(pvc);
    }
}