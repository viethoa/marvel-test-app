package com.viethoa.potdemo.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by VietHoa on 08/11/2016.
 */

public class Character implements Serializable {
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("description")
    private String description;
    @SerializedName("thumbnail")
    private Thumbnail thumbnail;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public boolean equals(Object object) {
        boolean equal = false;

        if (object instanceof Character) {
            Character character = (Character) object;
            equal = character.getId() == this.id;
        }

        return equal;
    }
}
