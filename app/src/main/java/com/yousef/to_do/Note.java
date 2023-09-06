package com.yousef.to_do;

public class Note {
    private int id;
    private String name;
    private String body;

    public Note() {
    }

    public Note(String name, String body) {
        this.name = name;
        this.body = body;
    }

    public Note(int id, String name, String body) {
        this.id = id;
        this.name = name;
        this.body = body;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
