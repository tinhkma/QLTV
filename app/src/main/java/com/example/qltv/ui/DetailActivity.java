package com.example.qltv.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.qltv.R;
import com.example.qltv.model.BookModel;

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

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.img_thumball)
    ImageView imgThumball;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.bt_edit)
    Button btEdit;
    @BindView(R.id.bt_read)
    Button btRead;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.ig_status)
    ImageView igStatus;
    @BindView(R.id.ln_rent)
    LinearLayout lnRent;
    private Realm mRealm;
    private boolean statusActive = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .schemaVersion(0)
                .migration(new MyMigration())
                .build();
        mRealm = Realm.getDefaultInstance();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        imageView4.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
        imgThumball.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));

        Intent intent = getIntent();

        String title = intent.getExtras().getString("title");
        int ig_thumball = intent.getExtras().getInt("ig_thumball");
        String des = intent.getExtras().getString("des");
        String author = intent.getExtras().getString("author");
        int amount = intent.getExtras().getInt("amount");
        boolean status = intent.getExtras().getBoolean("status");
        int position = intent.getExtras().getInt("position");
        getSupportActionBar().setTitle(title);

        if (status) {
            tvStatus.setText("Status: Online");
            igStatus.setImageResource(R.drawable.ic_onlinr);
        } else if (!status) {
            tvStatus.setText("Status: Offline");
            igStatus.setImageResource(R.drawable.ic_offline);
        }

        tvTitle.setText(title);
        Glide.with(this).load(ig_thumball).into(imgThumball);
        tvAmount.setText("Amount: " + amount);
        tvAuthor.setText(author);
        tvDes.setText(des);

        //Active
        igStatus.setOnClickListener(v -> {
            RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();
            for (BookModel bookModel : bookModelRealmResults) {
                if (bookModel.getName().equals(title)) {
                    if (status) {
                        statusActive = false;
                        tvStatus.setText("Status: Offline");
                        igStatus.setImageResource(R.drawable.ic_offline);
                        List<BookModel> bookModelList = new ArrayList<>();
                        bookModelList.add(new BookModel(
                                bookModel.getId(),
                                bookModel.getName(),
                                bookModel.getAuthor(),
                                "Book",
                                bookModel.getAmount(),
                                bookModel.getCurrentAmount(),
                                false,
                                bookModel.getCoverBook(),
                                bookModel.getThumballBook(),
                                bookModel.getDescription(),
                                false,
                                false));
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(bookModelList);
                        mRealm.commitTransaction();

                        Toast.makeText(this, "Save Success !", Toast.LENGTH_SHORT).show();
                    } else {
                        statusActive = true;
                        tvStatus.setText("Status: Online");
                        igStatus.setImageResource(R.drawable.ic_onlinr);
                        List<BookModel> bookModelList = new ArrayList<>();
                        bookModelList.add(new BookModel(
                                bookModel.getId(),
                                bookModel.getName(),
                                bookModel.getAuthor(),
                                "Book",
                                bookModel.getAmount(),
                                bookModel.getCurrentAmount(),
                                true,
                                bookModel.getCoverBook(),
                                bookModel.getThumballBook(),
                                bookModel.getDescription(),
                                false,
                                true));
                        mRealm.beginTransaction();
                        mRealm.copyToRealmOrUpdate(bookModelList);
                        mRealm.commitTransaction();

                        Toast.makeText(this, "Save Success !", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(bookModelRealmResults);
            mRealm.commitTransaction();
            Toast.makeText(this, "Active Success !", Toast.LENGTH_SHORT).show();
        });

        //Edit Book
        btEdit.setOnClickListener(v->{
            Intent intentDetail = new Intent(this, AddDeleteBookActivity.class);
            RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();
            for (BookModel bookModel : bookModelRealmResults) {
                if (bookModel.getName().equals(title)) {
                    intentDetail.putExtra("statusEdit", "edit");
                    intentDetail.putExtra("id", bookModel.getId());
                    intentDetail.putExtra("name", bookModel.getName());
                    intentDetail.putExtra("author", bookModel.getId());
                    intentDetail.putExtra("kind", bookModel.getKind());
                    intentDetail.putExtra("amount", bookModel.getAmount());
                    intentDetail.putExtra("currentAmount", bookModel.getCurrentAmount());
                    intentDetail.putExtra("status", bookModel.isStatus());
                    intentDetail.putExtra("coverBook", bookModel.getCoverBook());
                    intentDetail.putExtra("thumballBook", bookModel.getThumballBook());
                    intentDetail.putExtra("description", bookModel.getDescription());
                    intentDetail.putExtra("trending", bookModel.isTrending());
                    intentDetail.putExtra("visible", bookModel.isVisible());

                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, imgThumball, "sharedName");

                    startActivity(intentDetail, options.toBundle());
                    startActivity(intentDetail);
                }
            }
        });

        //Delete
        btRead.setOnClickListener(v-> {
            Intent intentDetail = new Intent(this, AddDeleteBookActivity.class);
            RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();
            for (BookModel bookModel : bookModelRealmResults) {
                if (bookModel.getName().equals(title)) {
                    mRealm = Realm.getDefaultInstance();
                    mRealm.beginTransaction();
                    bookModel.deleteFromRealm();
                    mRealm.commitTransaction();
                    mRealm.close();
                    break;
                }
            }
            finish();
            startActivity(new Intent(this, ManagerBookActivity.class));

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, ManagerBookActivity.class));
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
