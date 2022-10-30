package vietmobi.net.noteapp;

import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;

public interface RecyclerViewInterface {
    void onItemClick(int position);
    void onItemLongClick(int position);

    void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, View view);
}
