package edu.ucsb.cs.cs190i.rkuang.homies.models;

/**
 * Created by ricky on 6/5/17.
 */

public class User {

    private String name;
    private String imageURL;
    private String uid;

    public User() {

    }

    public User(String name, String url, String uid) {
        this.name = name;
        this.imageURL = url;
        this.uid = uid;
    }


    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getUid() {
        return uid;
    }

    @Override
    public boolean equals(Object obj) {
        User o = (User) obj;
        return this.uid.equals(o.uid);
    }
}
