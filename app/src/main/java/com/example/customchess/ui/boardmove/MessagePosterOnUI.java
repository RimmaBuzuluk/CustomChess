package com.example.customchess.ui.boardmove;

import android.content.Context;
import android.widget.Toast;

public class MessagePosterOnUI implements Runnable {

    private String message;
    private Context context;

    public MessagePosterOnUI(Context context, String message) {
        this.context = context;
        this.message = message;
    }

    @Override
    public void run() {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
