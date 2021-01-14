package com.firaz.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GivePlayerName extends AppCompatActivity implements View.OnClickListener {

    private EditText edtPlayer1Name, edtPlayer2Name;
    private Button btnPlayGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_player_name);

        edtPlayer1Name = findViewById(R.id.edit_p1_name);
        edtPlayer2Name = findViewById(R.id.edit_p2_name);
        btnPlayGame = findViewById(R.id.play_game);

        btnPlayGame.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play_game) {
            String player1Name = edtPlayer1Name.getText().toString().trim();
            String player2Name = edtPlayer2Name.getText().toString().trim();
            boolean isEmptyFields = false;

            if (TextUtils.isEmpty(player1Name)) {
                isEmptyFields = true;
                edtPlayer1Name.setError(getText(R.string.name_error));
            }
            if (TextUtils.isEmpty(player2Name)) {
                isEmptyFields = true;
                edtPlayer2Name.setError(getText(R.string.name_error));
            }
            if(!isEmptyFields) {
                //Explicit Intent (Move act with data)
                Intent toPlayTheGame = new Intent(GivePlayerName.this, PlayerVersusPlayer.class);
                toPlayTheGame.putExtra(PlayerVersusPlayer.PLAYER_ONE_NAME, player1Name);
                toPlayTheGame.putExtra(PlayerVersusPlayer.PLAYER_TWO_NAME, player2Name);
                startActivity(toPlayTheGame);
            }
        }
    }
}