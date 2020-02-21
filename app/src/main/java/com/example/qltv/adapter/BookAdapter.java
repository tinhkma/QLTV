package com.example.qltv.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.R;
import com.example.qltv.model.BookModel;
import com.example.qltv.ui.BookItemClickListener;
import com.example.qltv.ui.DetailActivity;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHoler> {

    private Context mContext;
    private List<BookModel> mList;
    BookItemClickListener movieItemClickListener;

    public BookAdapter(Context mContext, List<BookModel> mList, BookItemClickListener movieItemClickListener) {
        this.mContext = mContext;
        this.mList = mList;
        this.movieItemClickListener = movieItemClickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_book, parent, false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
        holder.itemBookTitle.setText(mList.get(position).getName());
        holder.itemBookImage.setImageResource(mList.get(position).coverBook);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {
        ImageView itemBookImage;
        TextView itemBookTitle;
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public ViewHoler(@NonNull View itemView) {
            super(itemView);

            itemBookImage = itemView.findViewById(R.id.item_book_image);
            itemBookTitle = itemView.findViewById(R.id.item_book_title);

            itemView.setOnClickListener(v->{

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("title", mList.get(getAdapterPosition()).getName());
                intent.putExtra("ig_thumball", mList.get(getAdapterPosition()).getCoverBook());
                intent.putExtra("des", mList.get(getAdapterPosition()).getDescription());
                intent.putExtra("author", mList.get(getAdapterPosition()).getAuthor());
                intent.putExtra("amount", mList.get(getAdapterPosition()).getAmount());
                intent.putExtra("status", mList.get(getAdapterPosition()).isVisible());
                intent.putExtra("position", getAdapterPosition());

                Log.d("0o0o0o", "ViewHoler: " + getAdapterPosition());

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) mContext,
                        itemBookImage, "sharedName");

                mContext.startActivity(intent, options.toBundle());

                movieItemClickListener.onBookClick(mList.get(getAdapterPosition()), itemBookImage);
            });
        }
    }
}
