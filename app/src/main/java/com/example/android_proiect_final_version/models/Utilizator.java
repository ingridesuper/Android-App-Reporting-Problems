package com.example.android_proiect_final_version.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.android_proiect_final_version.enums.Sector;

import java.io.Serializable;

@Entity (tableName = "utilizatori")
public class Utilizator implements Serializable {
    @PrimaryKey (autoGenerate = false)
    @NonNull
    String username;
    private String parola;
    private String nume;
    private String prenume;
    private Sector sector;

    public Utilizator(String username, String parola, String nume, String prenume, Sector sector) {
        this.username = username;
        this.parola=parola;
        this.nume = nume;
        this.prenume = prenume;
        this.sector = sector;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Utilizator{" +
                "nume='" + nume + '\'' +
                ", username='" + username + '\'' +
                ", prenume='" + prenume + '\'' +
                ", sector=" + sector +
                '}';
    }
}
