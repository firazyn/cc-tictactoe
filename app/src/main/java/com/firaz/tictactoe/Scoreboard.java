package com.firaz.tictactoe;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ListView;

import java.util.ArrayList;

public class Scoreboard extends AppCompatActivity {
    String[] name, score;
    Cursor cursor;
    DataHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoreboard);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar_main);

        ListView scoreListView = findViewById(R.id.lv_list);
        ArrayList<ListScoreboard> listScoreboards = new ArrayList<>();
        ScoreboardAdapter scoreboardAdapter = new ScoreboardAdapter(this, R.layout.adapter_view_layout, listScoreboards);
        scoreListView.setAdapter(scoreboardAdapter);

        dbHelper = new DataHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM scoreboard ORDER BY score DESC", null);
        name = new String[cursor.getCount()];
        score = new String[cursor.getCount()];
        cursor.moveToFirst();

        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            name[i] = cursor.getString(1);
            score[i] = cursor.getString(2);

            ListScoreboard listScoreboard = new ListScoreboard();
            listScoreboard.setName(name[i]);
            listScoreboard.setScore(score[i]);
            listScoreboards.add(listScoreboard);
        }

    }
}