package com.kryvuy.weatherapp.dialog;

import android.app.Dialog;
import android.app.usage.NetworkStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.kryvuy.weatherapp.MainActivity;
import com.kryvuy.weatherapp.R;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Володимир on 9/1/2017.
 */

public class Dialog_No_Network extends DialogFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fragment_no_internet);
        dialog.getWindow().setLayout(getScreenWidthInDPs(context),getScreenWidthInDPs(context));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.dialog_fragment_no_internet,container,false);

        ImageView buttonOnWifi = (ImageView) view.findViewById(R.id.button_dialog_internet_wifi);
        buttonOnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                startActivity(intent);
            }
        });

        ImageView buttonOnMobilDate = (ImageView) view.findViewById(R.id.button_dialog_internet_mobil_date);
        buttonOnMobilDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openMonilDateSitting();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNetWork()){
            onDestroy();
        }
    }

    private boolean isNetWork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    private int getScreenWidthInDPs(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        return Math.round(dm.widthPixels / dm.density);
    }

    private void openMonilDateSitting(){
        if(Build.VERSION.SDK_INT > 15)
        {
            Intent intent = new Intent(android.provider.Settings.ACTION_NETWORK_OPERATOR_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            if(Build.VERSION.SDK_INT > 15)
            {
                try
                {
                    Intent intent = new Intent("android.settings.DATA_ROAMING_SETTINGS");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                catch (Exception e) {
                    Log.d(MainActivity.LOG_TAG,"ERROR "+e.getMessage());
                }
            }
            else
            {
                Intent intent=new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                ComponentName cName = new ComponentName("com.android.phone","com.android.phone.Settings");
                intent.setComponent(cName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }

}
