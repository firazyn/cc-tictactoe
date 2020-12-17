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
    private int row, col;
    char[][] board = {{'_','_','_'},
                        {'_','_','_'},
                        {'_','_','_'}};

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
        int pointerOne = Character.getNumericValue(buttonID.charAt(buttonID.length()-2)); //dapat nilai i
        int pointerTwo = Character.getNumericValue(buttonID.charAt(buttonID.length()-1)); //dapat nilai j

        if (player1Turn) {
            ((Button) v).setText("x");
            board[pointerOne][pointerTwo] = 'x';
        } else {
            ((Button) v).setText("o");
            board[pointerOne][pointerTwo] = 'o';
            minimax.setBoard(board);
            tvAI.setText(minimax.bestMoveRow +"R"+" "+ minimax.bestMoveCol +"C");
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
                board[i][j] = '_';
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

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}