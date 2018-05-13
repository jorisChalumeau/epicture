package eu.epitech.spartan.epicture.modele;

import android.net.Uri;

public class Picture {
    private String title;
    private String description;
    private Uri image;

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
}
