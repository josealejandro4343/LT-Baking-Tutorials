package com.example.ltbakingtutorials;

public class ModelVideo {
    String RecipeName, Video, Credits;

    public ModelVideo() {

    }

    public ModelVideo(String recipeName, String video, String credits) {
        RecipeName = recipeName;
        Video = video;
        Credits = credits;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }

    public String getVideo() {
        return Video;
    }

    public void setVideo(String video) {
        Video = video;
    }

    public String getCredits() {
        return Credits;
    }

    public void setCredits(String credits) {
        Credits = credits;
    }
}
