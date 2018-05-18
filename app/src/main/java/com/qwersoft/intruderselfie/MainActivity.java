package com.qwersoft.intruderselfie;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button;

    boolean cameraServiceRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if(isCameraServiceRunning())
        {
            cameraServiceRunning = true;
            button.setText("Disable iSelfie");
        }
        else
        {
            cameraServiceRunning = false;
            button.setText("Enable iSelfie");
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(cameraServiceRunning)
                {
                    stopBackgroundService();
                    cameraServiceRunning = false;
                    button.setText("Enable iSelfie");
                }
                else
                {
                    startBackgroundService();
                    cameraServiceRunning = true;

                }
            }
        });
    }

    private void startBackgroundService()
    {

        if(checkPermission())
        {
            requestPermission();
        }
        else
        {
            Intent i = new Intent(this, CameraService.class);
            startService(i);
            button.setText("Disable iSelfie");
        }

    }



    private void stopBackgroundService()
    {
        Intent i = new Intent(this, CameraService.class);
        stopService(i);
    }
    private boolean checkPermission()
    {
        int cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int storagePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(cameraPermission == PackageManager.PERMISSION_DENIED || storagePermission == PackageManager.PERMISSION_DENIED)
        {
            return true;
        } else
        {
            return false;
        }
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    startBackgroundService();
                    button.setText("Disable iSelfie");
                } else {
                    //not granted
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean isCameraServiceRunning() {
        Class<?> serviceClass = CameraService.class;
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
