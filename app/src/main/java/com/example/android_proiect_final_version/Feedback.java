package com.example.android_proiect_final_version;

import java.io.Serializable;

public class Feedback implements Serializable {
    String id;
    String text;
    String titlu;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    @Override
    public String toString() {
        return titlu+":\n\t "+text;
    }
}
