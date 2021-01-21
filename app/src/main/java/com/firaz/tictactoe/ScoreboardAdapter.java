package com.firaz.tictactoe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ScoreboardAdapter extends ArrayAdapter<ListScoreboard> {

    private final Context mContext;

    public ScoreboardAdapter(Context context, int resource, List<ListScoreboard> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.adapter_view_layout, parent, false);
        }
        ViewHolder viewHolder = new ViewHolder(convertView);

        ListScoreboard listScoreboard = getItem(position);
        viewHolder.bind(listScoreboard);
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvName, tvScore;

        ViewHolder (View view) {
            tvName = view.findViewById(R.id.tv_name);
            tvScore = view.findViewById(R.id.tv_score);
        }

        void bind (ListScoreboard listScoreboard) {
            tvName.setText(listScoreboard.getName());
            tvScore.setText(listScoreboard.getScore());
        }
    }
}