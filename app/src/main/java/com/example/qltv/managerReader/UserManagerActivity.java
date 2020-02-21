package com.example.qltv.managerReader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.R;
import com.example.qltv.adapter.PeopleReaderdAdapter;
import com.example.qltv.model.PeopleLeaseModel;
import com.example.qltv.ui.ItemClick;
import com.example.qltv.ui.ManagerBookActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserManagerActivity extends AppCompatActivity {

    Toolbar toolbarpp;
    RecyclerView rcvPeople;
    private List<PeopleLeaseModel> peopleLeaseModelList;
    private List<PeopleLeaseModel.idBook> idBookList;
    private List<PeopleLeaseModel.idBook> idBookList1;
    private List<PeopleLeaseModel.idBook> idBookList2;
    private List<PeopleLeaseModel.idBook> idBookList3;
    private PeopleReaderdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);

        rcvPeople = findViewById(R.id.rcv_people);
        toolbarpp = findViewById(R.id.toolbarpp);

        setSupportActionBar(toolbarpp);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initData();

        initRcv();

    }

    private void initRcv() {
        adapter = new PeopleReaderdAdapter(this, peopleLeaseModelList);
        LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvPeople.setLayoutManager(manager);
        rcvPeople.setAdapter(adapter);
    }

    private void initData() {
        peopleLeaseModelList = new ArrayList<>();
        idBookList = new ArrayList<>();
        idBookList1 = new ArrayList<>();
        idBookList2 = new ArrayList<>();
        idBookList3 = new ArrayList<>();

        idBookList.add(new PeopleLeaseModel.idBook("gt002", "27/07/2019", "29/07/2019"));
        idBookList.add(new PeopleLeaseModel.idBook("gt001", "27/07/2019", "29/07/2019"));
        idBookList.add(new PeopleLeaseModel.idBook("gt004", "27/07/2019", "29/07/2019"));
        idBookList.add(new PeopleLeaseModel.idBook("gt006", "27/07/2019", "29/07/2019"));

        idBookList1.add(new PeopleLeaseModel.idBook("gt006", "27/07/2019", "29/07/2019"));

        idBookList2.add(new PeopleLeaseModel.idBook("gt002", "27/07/2019", "29/07/2019"));
        idBookList2.add(new PeopleLeaseModel.idBook("gt004", "27/07/2019", "29/07/2019"));
        idBookList2.add(new PeopleLeaseModel.idBook("gt006", "27/07/2019", "29/07/2019"));

        idBookList3.add(new PeopleLeaseModel.idBook("gt002", "27/07/2019", "29/07/2019"));
        idBookList3.add(new PeopleLeaseModel.idBook("gt001", "27/07/2019", "29/07/2019"));

        peopleLeaseModelList.add(new PeopleLeaseModel("pp001", "Truong Xuan Tinh", "0124512587", 0, idBookList));
        peopleLeaseModelList.add(new PeopleLeaseModel("pp002", "Nguyen Thuc Duong", "0124558745", 1, idBookList1));
        peopleLeaseModelList.add(new PeopleLeaseModel("pp003", "Bui Thanh Hung", "0124598745", 0, idBookList2));
        peopleLeaseModelList.add(new PeopleLeaseModel("pp003", "Nguyen Thi Giang", "0123658587", 3, idBookList3));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (android.R.id.home): {
                startActivity(new Intent(this, ManagerBookActivity.class));
                finish();
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
