package com.example.android_proiect_final_version.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.android_proiect_final_version.enums.CategorieProblema;
import com.example.android_proiect_final_version.enums.Sector;

import java.io.Serializable;
@Entity (tableName = "probleme", foreignKeys = @ForeignKey(entity = Utilizator.class, parentColumns = "username", childColumns = "autorUsername", onDelete = ForeignKey.CASCADE))
public class Problema implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String titlu;
    private String descriere;
    @NonNull
    private String autorUsername;
    private Sector sector;
    private String adresa;
    private CategorieProblema categorieProblema;

    public Problema(String titlu, String descriere, String autorUsername, Sector sector, String adresa, CategorieProblema categorieProblema) {
        this.titlu = titlu;
        this.descriere = descriere;
        this.autorUsername = autorUsername;
        this.sector = sector;
        this.adresa = adresa;
        this.categorieProblema = categorieProblema;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getAutorUsername() {
        return autorUsername;
    }

    public void setAutorUsername(String autorUsername) {
        this.autorUsername = autorUsername;
    }

    public CategorieProblema getCategorieProblema() {
        return categorieProblema;
    }

    public void setCategorieProblema(CategorieProblema categorieProblema) {
        this.categorieProblema = categorieProblema;
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
        return "Problema{" +
                "adresa='" + adresa + '\'' +
                ", id=" + id +
                ", titlu='" + titlu + '\'' +
                ", descriere='" + descriere + '\'' +
                ", autorUsername='" + autorUsername + '\'' +
                ", sector=" + sector +
                ", categorieProblema=" + categorieProblema +
                '}';
    }
}
