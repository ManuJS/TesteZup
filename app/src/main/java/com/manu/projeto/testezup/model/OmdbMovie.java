package com.manu.projeto.testezup.model;

import com.google.gson.annotations.SerializedName;


public class OmdbMovie{

    @SerializedName("Title")
    private String title;


    @SerializedName("Poster")
    private String posterPath;


    @SerializedName("Year")
    private String releaseDate;


    @SerializedName("imdbID")
    private String id;

    @SerializedName("Type")
    private String type;

    public OmdbMovie() {
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.id = id;
        this.title = title;
        this.type = type;
    }


    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
