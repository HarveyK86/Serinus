package org.canary.serinus.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "Post")
public final class Post {

    @Id
    @Lightweight
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Lightweight
    @Column(name = "Title")
    private String title;

    @Column(name = "Body")
    private String body;

    @Column(name = "Created")
    private long created;

    @Column(name = "Edited")
    private long edited;

    public Post() {
        super();
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(final String body) {
        this.body = body;
    }

    public long getCreated() {
        return this.created;
    }

    public void setCreated(final long created) {
        this.created = created;
    }

    public void setCreated(final Date created) {
        if(created != null) {
            this.created = created.getTime();
        }
    }

    public long getEdited() {
        return this.edited;
    }

    public void setEdited(final long edited) {
        this.edited = edited;
    }

    public void setEdited(final Date edited) {
        if(edited != null) {
            this.edited = edited.getTime();
        }
    }

}