package com.firaz.tictactoe;

// Code mainly provided by Florian Walther
// https://codinginflow.com

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerVersusPlayer extends AppCompatActivity implements View.OnClickListener {

    DataHelper dbHelper;

    private boolean player1Turn = true, nextTurn;

    private int roundCount;
    private int player1Score, player2Score;

    private TextView tvPlayer1Score, tvPlayer2Score;
    private TextView tvPlayer1Name, tvPlayer2Name, tvPlayerNameTurn;
    private Button[][] buttons = new Button[3][3];
    private Button btnDismissDialog;
    private ImageView imgWinner;

    public static String PLAYER_ONE_NAME = "player_one_name";
    public static String PLAYER_TWO_NAME = "player_two_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3x3_pvp);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_main);

        dbHelper = new DataHelper(this);
        tvPlayer1Score = findViewById(R.id.score_p1);
        tvPlayer2Score = findViewById(R.id.score_p2);
        tvPlayer1Name = findViewById(R.id.name_p1);
        tvPlayer2Name = findViewById(R.id.name_p2);
        tvPlayerNameTurn = findViewById(R.id.turn_player);
        TextView tvStatus = findViewById(R.id.turn_status);

        String player1Name = getIntent().getStringExtra(PLAYER_ONE_NAME);
        String player2Name = getIntent().getStringExtra(PLAYER_TWO_NAME);

        tvPlayerNameTurn.setText(player1Name);
        tvPlayer1Name.setText(player1Name);
        tvPlayer2Name.setText(player2Name);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_"+ i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        Button buttonNewGame = findViewById(R.id.button_new);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetGame();
            }
        });

        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pg = new Intent(PlayerVersusPlayer.this, PlayGame.class);
                startActivity(pg);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("x");
        } else {
            ((Button) v).setText("o");
        }
        ((Button) v).setTextColor(Color.parseColor("#000000"));

        if (roundCount == 0 && player1Turn) {
            nextTurn = false;
        }
        if (roundCount == 0 && !player1Turn) {
            nextTurn = true;
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }
    }

    private boolean checkForWin() {
        String[][] Board = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Board[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (Board[i][0].equals(Board[i][1])
                    && Board[i][0].equals(Board[i][2])
                    && !Board[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (Board[0][i].equals(Board[1][i])
                    && Board[0][i].equals(Board[2][i])
                    && !Board[0][i].equals("")) {
                return true;
            }
        }

        if (Board[0][0].equals(Board[1][1])
                && Board[0][0].equals(Board[2][2])
                && !Board[0][0].equals("")) {
            return true;
        }

        if (Board[0][2].equals(Board[1][1])
                && Board[0][2].equals(Board[2][0])
                && !Board[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Score++;
        String player1Name = getIntent().getStringExtra(PLAYER_ONE_NAME);
        updateScoreToDatabase(player1Score, player1Name);
        updatePointsText();
        showPlayer1WinnerDialog();
        resetBoard();
    }

    private void player2Wins() {
        player2Score++;
        String player2Name = getIntent().getStringExtra(PLAYER_TWO_NAME);
        updateScoreToDatabase(player2Score, player2Name);
        updatePointsText();
        showPlayer2WinnerDialog();
        resetBoard();
    }

    private void draw() {
        showDrawDialog();
        resetBoard();
    }

    private void updatePointsText() {
        tvPlayer1Score.setText("" +player1Score);
        tvPlayer2Score.setText("" +player2Score);
    }

    private void updateScoreToDatabase(int playerScore, String playerName) {
        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            String[] selectionArgs = {playerName};

            contentValues.put("score", playerScore);
            db.update("scoreboard", contentValues, "player_name = ?", selectionArgs);

        } catch (Exception e) {
            Log.e("tag", "error "+e);
        }

//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        String[] selectionArgs = {playerName};
//
//        contentValues.put("score", playerScore);
//        db.update("scoreboard", contentValues, "player_name = ?", selectionArgs);
    }

    private void resetBoard() {
        String player1Name = getIntent().getStringExtra(PLAYER_ONE_NAME);
        String player2Name = getIntent().getStringExtra(PLAYER_TWO_NAME);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = nextTurn;

        if (player1Turn) {
            tvPlayerNameTurn.setText(player1Name);
        } else {
            tvPlayerNameTurn.setText(player2Name);
        }
    }

    private void ResetGame() {
        player1Score = 0;
        player2Score = 0;
        updatePointsText();
        resetBoard();
    }

    private void showPlayer1WinnerDialog() {
        String player1Name = getIntent().getStringExtra(PLAYER_ONE_NAME);

        final Dialog player1WinnerDialog = new Dialog(PlayerVersusPlayer.this);
        player1WinnerDialog.setContentView(R.layout.dialog_winner);
        player1WinnerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        player1WinnerDialog.setCancelable(false);

        imgWinner = player1WinnerDialog.findViewById(R.id.winner_img);
        TextView tvPlayerName= player1WinnerDialog.findViewById(R.id.player_name);
        TextView tvPlayerWon = player1WinnerDialog.findViewById(R.id.won_text);
        btnDismissDialog = player1WinnerDialog.findViewById(R.id.dismiss_dialog);
        imgWinner.setImageResource(R.drawable.ic_cross);
        tvPlayerName.setText(player1Name);

        btnDismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player1WinnerDialog.dismiss();
            }
        });

        player1WinnerDialog.show();
    }

    private void showPlayer2WinnerDialog() {
        String player2Name = getIntent().getStringExtra(PLAYER_TWO_NAME);

        final Dialog player2WinnerDialog = new Dialog(PlayerVersusPlayer.this);
        player2WinnerDialog.setContentView(R.layout.dialog_winner);
        player2WinnerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        player2WinnerDialog.setCancelable(false);

        imgWinner = player2WinnerDialog.findViewById(R.id.winner_img);
        TextView tvPlayerName= player2WinnerDialog.findViewById(R.id.player_name);
        TextView tvPlayerWon = player2WinnerDialog.findViewById(R.id.won_text);
        btnDismissDialog = player2WinnerDialog.findViewById(R.id.dismiss_dialog);
        imgWinner.setImageResource(R.drawable.ic_circle);
        tvPlayerName.setText(player2Name);

        btnDismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player2WinnerDialog.dismiss();
            }
        });

        player2WinnerDialog.show();
    }

    private void showDrawDialog() {
        final Dialog drawDialog = new Dialog(PlayerVersusPlayer.this);
        drawDialog.setContentView(R.layout.dialog_winner);
        drawDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        drawDialog.setCancelable(false);

        imgWinner = drawDialog.findViewById(R.id.winner_img);
        TextView tvDraw = drawDialog.findViewById(R.id.won_text);
        btnDismissDialog = drawDialog.findViewById(R.id.dismiss_dialog);
        imgWinner.setImageResource(R.drawable.ic_circlecross);
        tvDraw.setText(getText(R.string.draw_round_text));

        btnDismissDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawDialog.dismiss();
            }
        });

        drawDialog.show();
    }
}