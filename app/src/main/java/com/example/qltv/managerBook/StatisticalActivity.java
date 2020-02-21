package com.example.qltv.managerBook;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.qltv.R;
import com.example.qltv.model.BookModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmResults;
import io.realm.RealmSchema;

public class StatisticalActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.tv_sum_book)
    TextView tvSumBook;
    @BindView(R.id.tv_num_read)
    TextView tvNumRead;
    @BindView(R.id.tv_borr)
    TextView tvBorr;
    @BindView(R.id.tx_sum_book)
    TextView txSumBook;
    @BindView(R.id.tx_book_visible)
    TextView txBookVisible;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistical);
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    private void innitRealm() {
        RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();
        tvSumBook.setText("- Sum = " + (bookModelRealmResults.size()+1) + " book");
        int sum_all_book = 0;
        int sum_all_book_visible = 0;

        for (BookModel bookModel : bookModelRealmResults){
            sum_all_book = sum_all_book + bookModel.getAmount();
            if (bookModel.isVisible()){
                sum_all_book_visible++;
            }
        }
        txBookVisible.setText("- Book Visible: " + sum_all_book_visible);
        tvSumBook.setText("- All Book: " + sum_all_book);
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

    private class MyMigration implements RealmMigration {

        @Override
        public void migrate(@NonNull DynamicRealm realm, long oldVersion, long newVersion) {
            // DynamicRealm exposes an editable schema
            RealmSchema schema = realm.getSchema();
        }
    }
}
