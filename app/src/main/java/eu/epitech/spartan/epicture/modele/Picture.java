package eu.epitech.spartan.epicture.modele;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Picture {
    private String title;
    private String description;
    private String image;
    private int score;
    private int views;
    private String datetime;

    public Picture() {}

    public Picture(String img) {
        this.image = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDatetime() {
        return datetime;
    }

    /**
     * converts a long int (in s) into a true datetime format (mm/dd/yyyy hh:mm:ss)
     */
    @SuppressLint("SimpleDateFormat")
    public void setDateTime(Long dateTimeInS) {
        Date date = new Date(dateTimeInS*1000);
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        this.datetime = format.format(date);
        format.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));//your zone
        this.datetime = format.format(date);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    @Override
    public String toString() {
        return "\ntitle: "+title+"\ndescription: "+description+"\nimage: "+image+"\nscore: "+score
                +"\nviews: "+views+"\ndatetime: "+datetime;
    }
}
