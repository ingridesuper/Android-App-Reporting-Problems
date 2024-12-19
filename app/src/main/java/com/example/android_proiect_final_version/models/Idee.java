package com.example.android_proiect_final_version.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.android_proiect_final_version.enums.Sector;

import java.io.Serializable;

@Entity (tableName = "idei", foreignKeys = @ForeignKey(entity = Utilizator.class, parentColumns = "username", childColumns = "autorUsername", onDelete = ForeignKey.CASCADE))
public class Idee implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String titlu;
    private String descriere;
    @NonNull
    private String autorUsername;
    private Sector sector;

    public Idee(String titlu, String descriere, @NonNull String autorUsername, Sector sector) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.autorUsername = autorUsername;
        this.sector = sector;
    }

    @NonNull
    public String getAutorUsername() {
        return autorUsername;
    }

    public void setAutorUsername(@NonNull String autorUsername) {
        this.autorUsername = autorUsername;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    public String getTitlu() {
        return titlu;
    }

    public void setTitlu(String titlu) {
        this.titlu = titlu;
    }

    @Override
    public String toString() {
        return "Idee{" +
                "autorUsername='" + autorUsername + '\'' +
                ", id=" + id +
                ", titlu='" + titlu + '\'' +
                ", descriere='" + descriere + '\'' +
                ", sector=" + sector +
                '}';
    }
}
