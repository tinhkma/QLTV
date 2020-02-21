package com.example.qltv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.qltv.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.bt_mr)
    Button btMr;
    @BindView(R.id.cardView)
    CardView cardView;
    @BindView(R.id.bt_mb)
    Button btMb;
    @BindView(R.id.cardView3)
    CardView cardView3;
    @BindView(R.id.bt_mbr)
    Button btMbr;
    @BindView(R.id.cardView2)
    CardView cardView2;
    @BindView(R.id.bt_rp)
    Button btRp;
    @BindView(R.id.cardView4)
    CardView cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        btMb.setOnClickListener(v->{
            startActivity(new Intent(this, ManagerBookActivity.class));
        });
        btMr.setOnClickListener(v->{
            startActivity(new Intent(this, ManagerBookActivity.class));
        });
        btMbr.setOnClickListener(v->{
            startActivity(new Intent(this, ManagerBookActivity.class));
        });
        btRp.setOnClickListener(v->{
            startActivity(new Intent(this, ManagerBookActivity.class));
        });

    }
}
