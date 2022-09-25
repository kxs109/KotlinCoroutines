package com.kxs109.kotlincoroutines.workmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

public class CameraReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getData() == null) return;
        Cursor cursor = context.getContentResolver().query(intent.getData(),
                null, null, null, null);
        cursor.moveToFirst();

        int index = cursor.getColumnIndex("_data");
        String path = cursor.getString(index);
    }
}