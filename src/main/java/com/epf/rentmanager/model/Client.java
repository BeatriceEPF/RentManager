package com.epf.rentmanager.model;

import java.time.LocalDate;

public class Client {

    // Attributs \\

    private int id;
    private String name;
    private String firstName;
    private String email;
    private LocalDate birthdate;


    // Constructeurs \\

    public Client(int id, String name, String firstName, String email, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.email = email;
        this.birthdate = birthdate;
    }

    public Client(){}


    // Méthodes \\


    // Getters \\

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }


    // Setters \\


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    @Override
    public String toString() {
        return "Client n°" + this.id + " : \n" +
                "nom = " + this.name + '\n' +
                "prenom = " + this.firstName + '\n' +
                "email = " + this.email + '\n' +
                "naissance = " + this.birthdate;
    }
}
