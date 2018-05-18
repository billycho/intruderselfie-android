package com.qwersoft.intruderselfie;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by IT02106 on 16/05/2018.
 */

public class PhoneUnlockedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            CameraManager mgr = new CameraManager(context);
            mgr.takePhoto();

            Log.d("asdaxxx", "Phone unlocked");
            Toast.makeText(context,"Unlocked",Toast.LENGTH_SHORT).show();

        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Log.d("asdaxxx", "Phone locked");
        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
            Log.d("asdaxxx", "Power On");
            Toast.makeText(context,"Power On",Toast.LENGTH_SHORT).show();

        }


    }
}
