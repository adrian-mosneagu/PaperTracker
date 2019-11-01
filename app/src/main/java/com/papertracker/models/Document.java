package com.papertracker.models;

import java.util.Date;

public class Document {
    private String name;
    private Date expirationDate;

    public String getName() {
        return name;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document(String name, Date expirationDate) {
        this.name = name;
        this.expirationDate = expirationDate;
    }

}
