package model;

/**
 * Created by Billy on 9/13/2017.
 */

public class MyVideo {
    private String title;
    private String thumnail;
    private String id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumnail() {
        return thumnail;
    }

    public void setThumnail(String thumnail) {
        this.thumnail = thumnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MyVideo(String title, String thumnail, String id) {

        this.title = title;
        this.thumnail = thumnail;
        this.id = id;
    }
}

