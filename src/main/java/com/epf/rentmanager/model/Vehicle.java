package com.epf.rentmanager.model;

public class Vehicle {


    // Attributs \\

    private int id;
    private String constructeur;
    private String modele;
    private int nb_places;

    // Constructeurs \\

    public Vehicle(){}

    public Vehicle(int id, String constructeur, String modele, int nb_places) {
        this.id = id;
        this.constructeur = constructeur;
        this.modele = modele;
        this.nb_places = nb_places;
    }


    // Méthodes \\


    // Getters \\

    public int getId() {
        return id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public String getModele() {
        return modele;
    }

    public int getNb_places() {
        return nb_places;
    }


    // Setters \\

    public void setId(int id) {
        this.id = id;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }


    public void setNb_places(int nb_places) {
        this.nb_places = nb_places;
    }

    @Override
    public String toString() {
        return "Vehicle n°" + this.id + " : \n" +
                "constructeur = " + this.constructeur + '\n' +
                ", constructeur='" + this.constructeur + '\'' +
                ", nb_places=" + this.nb_places;
    }
}
