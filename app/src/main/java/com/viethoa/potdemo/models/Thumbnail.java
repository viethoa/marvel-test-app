package com.viethoa.potdemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by VietHoa on 09/11/2016.
 */

public class Thumbnail implements Serializable {

    @SerializedName("path")
    private String path;
    @SerializedName("extension")
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }


    public String getThumbnailUrl() {
        return String.format("%s.%s", path, extension);
    }
}
