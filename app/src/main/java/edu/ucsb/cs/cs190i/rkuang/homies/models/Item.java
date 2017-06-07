package edu.ucsb.cs.cs190i.rkuang.homies.models;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ricky on 6/5/17.
 */

public class Item {

    private User user;
    private Date date;
    private String description;
    private String id;

    public Item() {

    }

    public Item(User user, String description) {
        Calendar c = Calendar.getInstance();

        this.user = user;
        this.date = new Date(c.getTimeInMillis());
        this.description = description;
        this.id = UUID.randomUUID().toString();
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "{user = "+ getUser() + ", " +
                "date = "+ getDate() + ", " +
                "item = "+ getDescription() +
                "}";
    }

    public String getId() {
        return id;
    }
}
