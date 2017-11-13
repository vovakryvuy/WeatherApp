package com.kryvuy.weatherapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.kryvuy.weatherapp.R;
import com.kryvuy.weatherapp.data_base.DatabaseWetherTwelveHour;

/**
 * Created by Volodymyr on 11/10/2017.
 */

public class DialogSpecialView_12Hours extends DialogFragment {
    private Handler handler = new Handler();
    private String mTime;
    private double mTemperature;
    private double mSpeedWind;




    public void setData(DatabaseWetherTwelveHour wetherTwelveHour) {
        this.mTime = wetherTwelveHour.getTime();
        this.mTemperature = wetherTwelveHour.getTemperature();
        this.mSpeedWind = wetherTwelveHour.getSpeedWind();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        try{
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }catch (NullPointerException e){
            e.getStackTrace();
        }

        dialog.setContentView(R.layout.dialog_special_wether_12hours);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_special_wether_12hours,container,false);
        TextView viewTime = (TextView) view.findViewById(R.id.text_special_dialog_time_12hours);
        TextView viewTemperature = (TextView) view.findViewById(R.id.text_special_dialog_temperature_12hours);
        TextView viewSpeedWind = (TextView) view.findViewById(R.id.text_special_dialog_speed_wind_12hours);
        viewTime.setText(mTime);
        viewTemperature.setText(String.valueOf(mTemperature));
        viewSpeedWind.setText(String.valueOf(mSpeedWind));
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}
