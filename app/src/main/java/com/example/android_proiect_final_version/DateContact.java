package com.example.android_proiect_final_version;

import java.io.Serializable;

public class DateContact implements Serializable {
    private String id;
    private String entitate;
    private String mail;
    private String telefon;

    public String getEntitate() {
        return entitate;
    }

    public void setEntitate(String entitate) {
        this.entitate = entitate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Date de contact " + entitate + ":\n" +
                "\tMail: " + mail +
                "\n\tNr de telefon: "+telefon;
    }
}
