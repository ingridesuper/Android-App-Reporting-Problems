package com.example.android_proiect_final_version.enums;

public enum CategorieProblema {
    PISTA("Pista De Biciclete Blocata"),
    RECICLARE("Reciclare inexistenta/necorespunzatoare"),
    SEMAFOR("Semafor necorespunzator"),
    ACCESIBILITATE("Accesibilitate inexistenta/necorespunzatoare"),
    ALTELE("Altele");
    private final String nume;

    CategorieProblema(String nume){
        this.nume=nume;
    }

    public String getNume() {
        return nume;
    }

}
