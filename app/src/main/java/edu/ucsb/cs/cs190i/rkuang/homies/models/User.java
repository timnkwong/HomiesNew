package edu.ucsb.cs.cs190i.rkuang.homies.models;

/**
 * Created by ricky on 6/5/17.
 */

public class User {

    private String name;
    private String imageURL;

    public User() {

    }

    public User(String name) {
        this.name = name;
    }

    public User(String name, String url) {
        this.name = name;
        this.imageURL = url;
    }


    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }
}
