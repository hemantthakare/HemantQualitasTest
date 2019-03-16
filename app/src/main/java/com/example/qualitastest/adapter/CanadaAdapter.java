package com.example.qualitastest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qualitastest.R;
import com.example.qualitastest.model.CanadaDeatilsPojo;

import java.util.List;


public class CanadaAdapter extends RecyclerView.Adapter<CanadaAdapter.CanadaHolder> {

    private Context context;
    private List<CanadaDeatilsPojo> rowList;

    public CanadaAdapter(Context context, List<CanadaDeatilsPojo> rowList) {
        this.context = context;
        this.rowList = rowList;
    }


    @Override
    public CanadaHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        return new CanadaHolder(LayoutInflater.from(context).inflate(R.layout.item_canada, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(CanadaHolder holder, int i) {

        if (!TextUtils.isEmpty(rowList.get(i).getTitle()) || !TextUtils.isEmpty(rowList.get(i).getDescription()) || !TextUtils.isEmpty(rowList.get(i).getImg_url())) {
            holder.txt_title.setText(rowList.get(i).getTitle());
            holder.txt_description.setText(rowList.get(i).getDescription());

            //here i am not using placeholder and error methods to show error icon and placeholder icon
            Glide.with(context)
                    .load(rowList.get(i).getImg_url())
                    .fitCenter()
                    .override(170, 130)
                    .into(holder.img_imageHref);
        }

    }

    @Override
    public int getItemCount() {
        return rowList.size();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void clear() {
        int size = rowList.size();
        rowList.clear();
        notifyItemRangeChanged(0, size);
    }

    class CanadaHolder extends RecyclerView.ViewHolder {

        private ImageView img_imageHref;
        private TextView txt_title, txt_description;

        public CanadaHolder(View itemView) {
            super(itemView);

            img_imageHref = itemView.findViewById(R.id.img_imageHref_id);
            txt_title = itemView.findViewById(R.id.txt_title_id);
            txt_description = itemView.findViewById(R.id.txt_description_id);
        }
    }

}
