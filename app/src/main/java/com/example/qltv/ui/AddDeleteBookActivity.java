package com.example.qltv.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class AddDeleteBookActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tx_id)
    EditText txId;
    @BindView(R.id.tx_name)
    EditText txName;
    @BindView(R.id.tx_author)
    EditText txAuthor;
    @BindView(R.id.tx_amount)
    EditText txAmount;
    @BindView(R.id.sw_status)
    Switch swStatus;
    @BindView(R.id.bn_choose_cover)
    Button bnChooseCover;
    @BindView(R.id.bn_choose_thumball)
    Button bnChooseThumball;
    @BindView(R.id.tx_des)
    EditText txDes;
    @BindView(R.id.bt_canlce)
    Button btCanlce;
    @BindView(R.id.bt_add)
    Button btAdd;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.views1)
    View views1;
    @BindView(R.id.ln0)
    LinearLayout ln0;
    @BindView(R.id.ln1)
    LinearLayout ln1;
    @BindView(R.id.ln2)
    LinearLayout ln2;
    @BindView(R.id.ln3)
    LinearLayout ln3;
    @BindView(R.id.tx_current_amount)
    EditText txCurrentAmount;
    @BindView(R.id.ln10)
    LinearLayout ln10;
    @BindView(R.id.ln4)
    LinearLayout ln4;
    @BindView(R.id.ln5)
    LinearLayout ln5;
    @BindView(R.id.ln6)
    LinearLayout ln6;
    @BindView(R.id.ln7)
    LinearLayout ln7;
    private Realm mRealm;
    private Intent intent;
    private static boolean status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delete_book);
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
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            getSupportActionBar().setTitle("Add Book");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        try {
            if (getIntent().getExtras().getString("statusEdit").equals("edit")) {
                initEdit();
            }
        } catch (Exception ex) {
            initText();
        }

        bnChooseThumball.setOnClickListener(v->{
            Intent getIntent = new Intent();
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Picture");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, PICK_IMAGE);
        });
        bnChooseCover.setOnClickListener(v->{
            Intent getIntent = new Intent();
            getIntent.setType("image/*");

            Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pickIntent.setType("image/*");

            Intent chooserIntent = Intent.createChooser(getIntent, "Select Picture");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

            startActivityForResult(chooserIntent, PICK_IMAGE);
        });
    }

    private void initEdit() {
        String id = (String) getIntent().getSerializableExtra("id");
        String name = (String) getIntent().getSerializableExtra("name");
        String author = (String) getIntent().getSerializableExtra("author");
        String kind = (String) getIntent().getSerializableExtra("kind");
        int amount = (int) getIntent().getSerializableExtra("amount");
        int currentAmount = (int) getIntent().getSerializableExtra("currentAmount");
        boolean statusIntent = (boolean) getIntent().getSerializableExtra("status");
        int coverBook = (int) getIntent().getSerializableExtra("coverBook");
        int thumballBook = (int) getIntent().getSerializableExtra("thumballBook");
        String description = (String) getIntent().getSerializableExtra("description");
        boolean trending = (boolean) getIntent().getSerializableExtra("trending");
        textView6.setText("Edit");
        getSupportActionBar().setTitle("Edit: " + name);

        txId.setText(id);
        txName.setText(name);
        txAuthor.setText(author);
        txDes.setText(description);
        txAmount.setText(String.valueOf(amount));
        txCurrentAmount.setText(String.valueOf(amount));
        swStatus.setChecked(statusIntent);

        swStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                status = true;
            } else {
                status = false;
            }
        });

        btAdd.setOnClickListener(v->{
            RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();
            List<BookModel> bookModelList = new ArrayList<>();
            bookModelList.add(new BookModel(
                    txId.getText().toString(),
                    txName.getText().toString(),
                    txAuthor.getText().toString(),
                    "Book",
                    Integer.parseInt(txAmount.getText().toString()),
                    Integer.parseInt(txAmount.getText().toString()),
                    status,
                    coverBook,
                    thumballBook,
                    txDes.getText().toString(),
                    trending,
                    status));
            mRealm.beginTransaction();
            mRealm.copyToRealmOrUpdate(bookModelList);
            mRealm.commitTransaction();
            Toast.makeText(this, "Save Success !", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().length() == 0;
    }

    private void initText() {

        ln10.setVisibility(View.GONE);

        RealmResults<BookModel> bookModelRealmResults = mRealm.where(BookModel.class).findAll();

        txId.setHint(bookModelRealmResults.get(bookModelRealmResults.size() - 1).getId());
        btCanlce.setOnClickListener(v -> ClearText());
        btAdd.setOnClickListener(v -> {
            swStatus.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    status = true;
                } else {
                    status = false;
                }
            });
            if (!isEmpty(txAmount) && !isEmpty(txDes) && !isEmpty(txAuthor) && !isEmpty(txId) && !isEmpty(txName)) {
                List<BookModel> bookModelList = new ArrayList<>();
                bookModelList.add(new BookModel(
                        txId.getText().toString(),
                        txName.getText().toString(),
                        txAuthor.getText().toString(),
                        "Book",
                        Integer.parseInt(txAmount.getText().toString()),
                        Integer.parseInt(txAmount.getText().toString()),
                        status,
                        R.drawable.cosmos,
                        R.drawable.cosmos,
                        txDes.getText().toString(),
                        false,
                        status));
                mRealm.beginTransaction();
                mRealm.copyToRealmOrUpdate(bookModelList);
                mRealm.commitTransaction();

                Toast.makeText(this, "Save Success !", Toast.LENGTH_SHORT).show();
                ClearText();
            } else {
                Toast.makeText(this, "No Empty !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ClearText() {
        txAmount.setText("");
        txAuthor.setText("");
        txDes.setText("");
        txId.setText("");
        txName.setText("");
        swStatus.setChecked(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                onBackPressed();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE){
            Uri selectdImage = data.getData();

            String[] filePathColum = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectdImage, filePathColum, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColum[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Log.d("0o0o0o", "onActivityResult: " + picturePath);
        }
    }
}
