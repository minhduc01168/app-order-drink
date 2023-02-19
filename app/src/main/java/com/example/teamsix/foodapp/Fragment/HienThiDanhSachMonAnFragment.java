package com.example.teamsix.foodapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.teamsix.foodapp.CustomAdapter.AdapterHienThiDanhSachMonAn;
import com.example.teamsix.foodapp.DAO.MonAnDAO;
import com.example.teamsix.foodapp.DTO.MonAnDTO;
import com.example.teamsix.foodapp.R;
import com.example.teamsix.foodapp.SoLuongActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment{
    GridView gridView;
    MonAnDAO monAnDAO;
    List<MonAnDTO> monAnDTOList;
    AdapterHienThiDanhSachMonAn adapterHienThiDanhSachMonAn;
    int maban;

    // theem vao
   // int maquyen;
    //SharedPreferences sharedPreferences;
    //them vao

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hienthithucdon, container, false);

        //them vao
        //sharedPreferences = getActivity().getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
        //maquyen = sharedPreferences.getInt("maquyen", 0);
        //them vao

        gridView = (GridView) view.findViewById(R.id.gvHienThiThucDon);
        monAnDAO = new MonAnDAO(getActivity());

        Bundle bundle = getArguments();
        if(bundle != null){
            final int maloai = bundle.getInt("maloai");
            maban = bundle.getInt("maban");

            monAnDTOList = monAnDAO.LayDanhSachMonAnTheoLoai(maloai);

            adapterHienThiDanhSachMonAn = new AdapterHienThiDanhSachMonAn(getActivity(), R.layout.custom_layout_hienthidanhsachmonan, monAnDTOList);
            gridView.setAdapter(adapterHienThiDanhSachMonAn);
            adapterHienThiDanhSachMonAn.notifyDataSetChanged();
            registerForContextMenu(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (maban != 0){
                            Intent iSoLuong = new Intent(getActivity(), SoLuongActivity.class);
                            iSoLuong.putExtra("maban", maban);
                            iSoLuong.putExtra("mamonan", monAnDTOList.get(position).getMaMonAn());

                            startActivity(iSoLuong);
                        }
                    }
                }
            );

        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("hienthiloai", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.edit_context_menu, menu);
        menu.removeItem(R.id.itSua);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int vitri = menuInfo.position;
        int mamonan = monAnDTOList.get(vitri).getMaMonAn();

        switch(id){
            case R.id.itSua:

                break;
            case R.id.itXoa:
                boolean kiemtra = monAnDAO.XoaMonAn(mamonan);
                if (kiemtra){
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.xoathanhcong), Toast.LENGTH_SHORT).show();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    if (Build.VERSION.SDK_INT >= 26){
                        transaction.setReorderingAllowed(false);
                    }
                    transaction.detach(this).attach(this).commit();
                }else
                    Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.loi) + maban, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }


}
