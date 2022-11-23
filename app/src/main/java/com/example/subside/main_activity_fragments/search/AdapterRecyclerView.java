package com.example.subside.main_activity_fragments.search;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subside.ProfileDisplay;
import com.example.subside.R;
import java.util.ArrayList;

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder>{
    private static ArrayList<ItemModel> dataItem;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textName;
        TextView textMajor;
        ImageView imageIcon;
        TextView textID;
        TextView textIG;
        TextView textEmail;
        TextView textPhone;
        TextView textLinkedin;
        CardView profileItem;


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
            profileItem=itemView.findViewById(R.id.ProfileItem);

        }
    }

    public AdapterRecyclerView(Context context, ArrayList<ItemModel> dataItem){
        this.context= context;
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

        holder.profileItem.setOnClickListener(v -> {
            Intent profileIntent = new Intent(context,ProfileDisplay.class);
            profileIntent.putExtra("profile_image",dataItem.get(position).getImage());
            profileIntent.putExtra("profile_name",dataItem.get(position).getName());
            profileIntent.putExtra("profile_major",dataItem.get(position).getMajor());
            profileIntent.putExtra("profile_Id",dataItem.get(position).getId());
            profileIntent.putExtra("profile_Ig",dataItem.get(position).getIg());
            profileIntent.putExtra("profile_email",dataItem.get(position).getEmail());
            profileIntent.putExtra("profile_phone",dataItem.get(position).getPhone());
            profileIntent.putExtra("profile_linkedin",dataItem.get(position).getLinkedin());
            context.startActivity(profileIntent);

        });

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
