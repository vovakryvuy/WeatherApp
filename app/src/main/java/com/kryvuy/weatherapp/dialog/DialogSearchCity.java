package com.kryvuy.weatherapp.dialog;

import android.app.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import com.kryvuy.weatherapp.MainActivity;
import com.kryvuy.weatherapp.R;

import java.util.List;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Володимир on 9/12/2017.
 */

public class DialogSearchCity extends DialogFragment {
    ListView listView;
    List<String> mListCityCountry;

    public void setListCityCountry(List<String> listCityCountry){
        mListCityCountry = listCityCountry;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Context context = getContext();
        Dialog dialog = new Dialog(getActivity());
        try{
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }catch (NullPointerException e){
            e.getStackTrace();
        }
        dialog.setContentView(R.layout.dialog_fragment_search_list_city);
        dialog.getWindow().setLayout(getScreenWidthInDPs(context)+50,getScreenWidthInDPs(context)*2);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_search_list_city,container,false);
        listView = (ListView) view.findViewById(R.id.list_dialog_fragment_city);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mListCityCountry != null && !mListCityCountry.isEmpty()){
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_list_item_1,mListCityCountry);
            listView.setAdapter(adapter);
        }

    }
    private int getScreenWidthInDPs(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);
        try{
            windowManager.getDefaultDisplay().getMetrics(dm);
        }catch (NullPointerException e){
            e.getStackTrace();
        }
        return Math.round(dm.widthPixels / dm.density);
    }


}
