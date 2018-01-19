package hoshiko.mytrip;

import android.net.Uri;

/**
 * Created by An on 1/20/2018.
 */

public class Image {
    private String imageName;
    private Uri imageUrl;

    public Image() {
    }

    public Image(String imageName, Uri imageUrl) {
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Uri getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        this.imageUrl = imageUrl;
    }
}
