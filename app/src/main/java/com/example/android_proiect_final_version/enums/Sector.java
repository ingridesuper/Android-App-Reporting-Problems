package com.example.android_proiect_final_version.enums;


public enum Sector {
    UNU(1),
    DOI(2),
    TREI(3),
    PATRU(4),
    CINCI(5),
    SASE(6);

    private final int numar;

    // constructor
    Sector(int numar) {
        this.numar = numar;
    }

    // metoda pentru a obține nr asociat sectorului
    public int getNumar() {
        return this.numar;
    }

    @Override
    public String toString() {
        return "Sector " + numar;
    }
}
