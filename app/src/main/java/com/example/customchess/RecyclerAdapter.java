package com.example.customchess;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.movements.Position;
import com.example.customchess.engine.movements.Verticals;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public interface OnItemSelected {
        void onItemClicked(int position);
    }

    private OnItemSelected activity;
    private ArrayList<Integer> colorList;

    public RecyclerAdapter(Context context, ArrayList<Integer> list) {
        colorList = list;
        try {
            this.activity = (OnItemSelected) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(exception.getMessage() + " some happened wrong");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton piece;
        private Position    position;

        private void calculatePosition(View view) {
            int pos = (Integer) view.getTag();

            int horizontal = 8 - (pos % 8);
            int verticalRes = 0;
            int vertical = 0;

            for (int i = 0; verticalRes != pos; i++) {
                verticalRes = (8 - horizontal) + 8 * i;
                vertical = i;
            }
            Verticals[] verticals = Verticals.values();

            this.position = new Position(verticals[7 - vertical], horizontal);
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            piece = itemView.findViewById(R.id.chess_cage);
            position = null;

            piece.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (position == null) {
                        calculatePosition(view);
                    }

                    activity.onItemClicked(0);
                    Log.d("fuck", "position -> " + position);
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chess_cage, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemView.setTag(63 - position);

        holder.piece.setBackgroundColor(colorList.get(position));
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

}
