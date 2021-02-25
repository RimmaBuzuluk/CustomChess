package com.example.customchess.ui.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.customchess.R;
import com.example.customchess.ui.CageAdapter;
import com.example.customchess.ui.MovementHandler;

public class PromotionDialogFragment extends DialogFragment implements View.OnClickListener {
    public interface PromotionDialogListener {
        void applyChoice(String piece, MovementHandler.TempPosition start,
                         CageAdapter.ViewHolder startHolder, CageAdapter.ViewHolder destinationHolder);
    }

    private PromotionDialogListener context;
    private CageAdapter.ViewHolder startHolder;
    private CageAdapter.ViewHolder destinationHolder;
    private MovementHandler.TempPosition start;

    public PromotionDialogFragment(PromotionDialogListener context,
                                   CageAdapter.ViewHolder startHolder,
                                   CageAdapter.ViewHolder destinationHolder,
                                   MovementHandler.TempPosition start) {
        this.startHolder = startHolder;
        this.destinationHolder = destinationHolder;
        this.start = start;
        try {
            this.context = (PromotionDialogListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog();
        setCancelable(false);
        View view = inflater.inflate(R.layout.fragment_dialog, container, false);
        view.findViewById(R.id.btn_promotion_queen).setOnClickListener(this);
        view.findViewById(R.id.btn_promotion_bishop).setOnClickListener(this);
        view.findViewById(R.id.btn_promotion_knight).setOnClickListener(this);
        view.findViewById(R.id.btn_promotion_rook).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        context.applyChoice(((Button) view).getText().toString(),
                    start, startHolder, destinationHolder);
        dismiss();
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
