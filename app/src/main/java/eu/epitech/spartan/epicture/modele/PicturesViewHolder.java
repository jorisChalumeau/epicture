package eu.epitech.spartan.epicture.modele;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PicturesViewHolder extends RecyclerView.ViewHolder {

    public ImageView picture;
    public TextView title;
    public TextView description;
    public TextView score;
    public TextView views;
    public TextView dateTime;

    public PicturesViewHolder(View itemView) {
        super(itemView);
    }

}
