package com.example.customchess;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customchess.engine.movements.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class CageAdapter extends RecyclerView.Adapter<CageAdapter.ViewHolder> {

    public interface OnItemSelected {
        void onItemClicked(Position position, int index, int imageResourceId);
    }

    private OnItemSelected  activity;
    private ArrayList<Cage> cageList;
    private Hashtable<Integer, Integer> teamsImages;

    public CageAdapter(OnItemSelected context, Hashtable<Integer, Integer> teamsImages, ArrayList<Cage> list) {
        cageList = list;
        this.teamsImages = teamsImages;
        try {
            this.activity = context;
        } catch (ClassCastException exception) {
            throw new ClassCastException(exception.getMessage() + " invalid typecast");
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageButton piece;
        private int         imageResource;
        private Position    position;

        public void hide() {
            imageResource = 0;
            piece.setImageResource(imageResource);
        }

        public void draw(int imageId) {
            imageResource = imageId;
            piece.setImageResource(imageId);
        }

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            piece = itemView.findViewById(R.id.chess_cage);

            piece.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.onItemClicked(position, cageList.indexOf((Cage) view.getTag()), imageResource);
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
        holder.itemView.setTag(cageList.get(position));

        holder.imageResource = 0;
        holder.piece.setBackgroundColor(cageList.get(position).getColorID());
        holder.position = cageList.get(position).getPosition();

        if (teamsImages.containsKey(position)) {
            int imageId = teamsImages.get(position);
            holder.piece.setImageResource(imageId);
            holder.imageResource = imageId;
//            holder.piece.setTag(imageId);
        }

//        for (int i = 0; i < 8; i++) {
//            if (position == (1 + i * 8)) {
//                holder.piece.setImageResource(R.drawable.white_pawn);
//            } else if (position == (6 + i * 8)) {
//                holder.piece.setImageResource(R.drawable.black_pawn);
//            }
//        }

    }

    @Override
    public int getItemCount() {
        return cageList.size();
    }

}
