package com.example.customchess;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.movements.Position;

import java.util.ArrayList;

public class CageAdapter extends RecyclerView.Adapter<CageAdapter.ViewHolder> {

    public interface OnItemSelected {
        void onItemClicked(Position position);
    }

    private OnItemSelected activity;
    private ArrayList<Cage> colorList;

    public CageAdapter(Context context, ArrayList<Cage> list) {
        colorList = list;
        try {
            this.activity = (OnItemSelected) context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(exception.getMessage() + " invalid typecast");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageButton piece;
        private Position    position;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            piece = itemView.findViewById(R.id.chess_cage);

            piece.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClicked(position);
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
        holder.itemView.setTag(colorList.get(position));

        holder.piece.setBackgroundColor(colorList.get(position).getColorID());
        holder.position = colorList.get(position).getPosition();
    }

    @Override
    public int getItemCount() {
        return colorList.size();
    }

}
