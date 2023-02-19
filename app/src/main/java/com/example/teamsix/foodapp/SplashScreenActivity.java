package com.example.teamsix.foodapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.teamsix.foodapp.DAO.NhanVienDAO;
import com.example.teamsix.foodapp.DAO.QuyenDAO;
import com.example.teamsix.foodapp.DTO.NhanVienDTO;
import com.example.teamsix.foodapp.Database.CreateDatabase;

public class SplashScreenActivity extends AppCompatActivity {
    private SharedPreferences mMoLanDau;
    private SharedPreferences.Editor editor;

    private QuyenDAO quyenDAO;
    private NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        @SuppressLint("CommitPrefEdits") Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                Log.d("kiemtra", e.getMessage());
            } finally {
                mMoLanDau = getSharedPreferences("SPR_MOLANDAU", 0);
                if (mMoLanDau != null) {
                    boolean firstOpen = mMoLanDau.getBoolean("MOLANDAU", true);

                    if (firstOpen){
                        CreateDatabase createDatabase = new CreateDatabase(this);
                        createDatabase.open();

                        quyenDAO = new QuyenDAO(this);
                        quyenDAO.ThemQuyen("Quản lý");
                        quyenDAO.ThemQuyen("Nhân viên");
                        quyenDAO.ThemQuyen("Admin");

                        nhanVienDAO = new NhanVienDAO(this);
                        NhanVienDTO nhanVienDTO = new NhanVienDTO();
                        nhanVienDTO.setTENDANGNHAP("admin");
                        nhanVienDTO.setFULLNAME("Nguyen Minh Duc");
                        nhanVienDTO.setCMND("111111111");
                        nhanVienDTO.setGIOITINH("Nam");
                        nhanVienDTO.setMATKHAU("admin");
                        nhanVienDTO.setNGAYSINH("27/06/2001");
                        nhanVienDTO.setMAQUYEN(2); // sua tu 0 thanh 2

                        nhanVienDAO.ThemNV(nhanVienDTO);

                        editor = mMoLanDau.edit();
                        editor.putBoolean("MOLANDAU", false);
                        editor.apply();

                    }

                    Intent iMoLanDau = new Intent(this, DangNhapActivity.class);
                    startActivity(iMoLanDau);
                    finish();
                }
            }
        });
        thread.start();
    }
}