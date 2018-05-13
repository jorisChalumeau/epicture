package eu.epitech.spartan.epicture.modele;

import android.annotation.SuppressLint;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Picture {
    private String title;
    private String description;
    private Uri image;
    private int score;
    private int views;
    private String datetime;

    public Picture() {}

    public Picture(Uri img) {
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

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
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
        this.datetime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(dateTimeInS));
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
}
