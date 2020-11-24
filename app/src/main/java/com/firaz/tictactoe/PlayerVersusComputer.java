package com.firaz.tictactoe;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class PlayerVersusComputer extends AppCompatActivity implements View.OnClickListener {
    public Minimax minimax = new Minimax();
    private boolean player1Turn = true;

    private int roundCount;
    private int player1Score, player2Score;
    int row, col;
    String[][] board = {{"_","_","_"},
                        {"_","_","_"},
                        {"_","_","_"}};

    private TextView tvPlayer1Score, tvPlayer2Score;
    private TextView tvPlayer1Name, tvPlayer2Name, tvAI;
    private Button[][] buttons = new Button[3][3];

    private String player1Name = "John";
    private String player2Name = "Jane";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_3x3_pvc);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_main);

        tvPlayer1Score = findViewById(R.id.score_p1);
        tvPlayer2Score = findViewById(R.id.score_p2);
        tvPlayer1Name = findViewById(R.id.name_p1);
        tvPlayer2Name = findViewById(R.id.name_p2);
        tvAI = findViewById(R.id.ai_text);

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
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        String buttonID = getResources().getResourceEntryName(v.getId()); //button_ij
        int pointerOne = Integer.parseInt(buttonID.substring(7, buttonID.length()-1)); //dapat nilai i
        int pointerTwo = Integer.parseInt(buttonID.substring(buttonID.length()-1)); //dapat nilai j

        if (player1Turn) {
            ((Button) v).setText("x");
            board[pointerOne][pointerTwo] = "x";
        } else {
            ((Button) v).setText("o");
            board[pointerOne][pointerTwo] = "o";
            checkBestMove();
            //mungkin nanti disini
            //tambahin fungsi evaluasi
        }
        ((Button) v).setTextColor(Color.parseColor("#000000"));

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 9) {
            draw();
            //mungkin nanti disini
            //tambahin var isMoveLeft = false;
        } else {
            player1Turn = !player1Turn;
        }
    }

    protected void checkBestMove() {
        minimax.setBoard(board);
        row = minimax.getCol();
        col = minimax.getRow();
        tvAI.setText(row+"R"+" "+col+"C");
    }

    private boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1])
                    && board[i][0].equals(board[i][2])
                    && !board[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i].equals(board[1][i])
                    && board[0][i].equals(board[2][i])
                    && !board[0][i].equals("")) {
                return true;
            }
        }

        if (board[0][0].equals(board[1][1])
                && board[0][0].equals(board[2][2])
                && !board[0][0].equals("")) {
            return true;
        }

        if (board[0][2].equals(board[1][1])
                && board[0][2].equals(board[2][0])
                && !board[0][2].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Score++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Score++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        tvPlayer1Score.setText("" +player1Score);
        tvPlayer2Score.setText("" +player2Score);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                board[i][j] = "_";
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void ResetGame() {
        player1Score = 0;
        player2Score = 0;
        updatePointsText();
        resetBoard();
    }
}