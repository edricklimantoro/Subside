package com.example.subside.main_activity_fragments.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subside.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>{
    private static ArrayList<ItemModel> dataItem;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textMajor;
        ImageView imageIcon;
        TextView textID;
        TextView textIG;
        TextView textEmail;
        TextView textPhone;
        TextView textLinkedin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.text_name);
            textMajor = itemView.findViewById(R.id.text_major);
            imageIcon = itemView.findViewById(R.id.imageList);
            textIG=itemView.findViewById(R.id.text_ig);
            textID=itemView.findViewById(R.id.text_id);
            textEmail=itemView.findViewById(R.id.text_email);
            textPhone=itemView.findViewById(R.id.text_phone);
            textLinkedin=itemView.findViewById(R.id.text_linkedin);
        }
    }

    public AdapterRecyclerView(ArrayList<ItemModel> dataItem){
        this.dataItem=dataItem;
    }


    @NonNull
    @Override
    public AdapterRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerView.ViewHolder holder, int position) {

        TextView textName=holder.textName;
        TextView textMajor=holder.textMajor;
        ImageView imageIcon = holder.imageIcon;
        TextView textID= holder.textID;
        TextView textIG= holder.textIG;
        TextView textEmail= holder.textEmail;
        TextView textPhone= holder.textPhone;
        TextView textLinkedin= holder.textLinkedin;


        textName.setText(dataItem.get(position).getName());
        textMajor.setText(dataItem.get(position).getMajor());
        imageIcon.setImageResource(dataItem.get(position).getImage());
        textID.setText(dataItem.get(position).getId());
        textIG.setText(dataItem.get(position).getIg());
        textEmail.setText(dataItem.get(position).getEmail());
        textPhone.setText(dataItem.get(position).getPhone());
        textLinkedin.setText(dataItem.get(position).getLinkedin());
    }

    @Override
    public int getItemCount() {
        return dataItem.size();
    }

    public void setFilter(ArrayList<ItemModel> filterModel){
        dataItem=new ArrayList<>();
        dataItem.addAll(filterModel);
        notifyDataSetChanged();
    };

}
