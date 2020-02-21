package com.example.qltv.managerBook;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.R;
import com.example.qltv.adapter.BookAdapter;
import com.example.qltv.model.BookModel;
import com.example.qltv.ui.BookItemClickListener;
import com.example.qltv.ui.ManagerBookActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

public class ManagerBookDrActivity extends AppCompatActivity implements BookItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.rcv_visible_book)
    RecyclerView rcvVisibleBook;
    @BindView(R.id.rcv_all_book)
    RecyclerView rcvAllBook;
    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.rcv_trending)
    RecyclerView rcvTrending;
    @BindView(R.id.textView10)
    TextView textView10;
    private Realm mRealm;
    private BookAdapter adapter;
    private List<BookModel> listVisibleBook;
    private List<BookModel> listTrendingBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_book2);
        ButterKnife.bind(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .schemaVersion(0)
                .migration(new MyMigration())
                .build();
        mRealm = Realm.getDefaultInstance();

        innitRealm();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void innitRealm() {
        listTrendingBook = new ArrayList<>();
        listVisibleBook = new ArrayList<>();

        RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();

        for (BookModel abc : bookModelRealmResults){
            if (abc.isTrending()){
                listTrendingBook.add(abc);
            }
            if (!abc.isVisible()){
                listVisibleBook.add(abc);
            }
        }

        //RecyleView Visible
        adapter = new BookAdapter(this, listVisibleBook, this);
        LinearLayoutManager lnVisible = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvVisibleBook.setLayoutManager(lnVisible);
        rcvVisibleBook.setAdapter(adapter);

        //RecycleView Trending Book
        adapter = new BookAdapter(this, listTrendingBook, this);
        LinearLayoutManager lnTrending = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rcvTrending.setLayoutManager(lnTrending);
        rcvTrending.setAdapter(adapter);

        //RecycleView All
        adapter = new BookAdapter(this, bookModelRealmResults, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, LinearLayoutManager.VERTICAL, false);
        rcvAllBook.setLayoutManager(gridLayoutManager);
        rcvAllBook.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBookClick(BookModel books, ImageView imgBook) {

    }


    private class MyMigration implements RealmMigration {

        @Override
        public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();
        }
    }
}
