package com.example.teamsix.foodapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.teamsix.foodapp.R;
import com.example.teamsix.foodapp.TrangChuActivity;

public class HienThiThongKeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithongke, container, false);
        setHasOptionsMenu(true);
        ((TrangChuActivity) getActivity()).getSupportActionBar().setTitle(R.string.thongke);
        return view;
    }
}
