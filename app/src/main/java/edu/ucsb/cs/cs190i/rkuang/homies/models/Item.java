package edu.ucsb.cs.cs190i.rkuang.homies.models;

import java.util.Calendar;
import java.util.UUID;

/**
 * Created by ricky on 6/5/17.
 */

public class Item {

    private User user;
    private long date;
    private String description;
    private String id;

    public Item() {

    }

    public Item(User user, String description, String id) {
        Calendar c = Calendar.getInstance();

        this.user = user;
        this.date = c.getTimeInMillis();
        this.description = description;
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public long getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "{user = "+ getUser() + ", " +
                "date = "+ getDate() + ", " +
                "item = "+ getDescription() +
                "}";
    }

    @Override
    public boolean equals(Object obj) {
        Item o = (Item) obj;
        return this.id.equals(o.getId());
    }
}
