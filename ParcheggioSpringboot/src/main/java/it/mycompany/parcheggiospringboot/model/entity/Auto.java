/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.entity;

import java.util.Objects;

/**
 *
 * @author Nicola Natale
 */
public class Auto {
    private int id; 
    private String targa;
    private String marca;
    private String modello;
    private boolean parcheggiata;
    private float mediaOre;
    
    public Auto(){
        super();
    }

    public Auto(int id, String targa, String marca, String modello, boolean parcheggiata, float mediaOre) {
        this.id = id;
        this.targa = targa;
        this.marca = marca;
        this.modello = modello;
        this.parcheggiata = parcheggiata;
        this.mediaOre = mediaOre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getTarga() {
        return targa;
    }

    public String getMarca() {
        return marca;
    }

    public String getModello() {
        return modello;
    }

    public boolean isParcheggiata() {
        return parcheggiata;
    }

    public void setParcheggiata(boolean parcheggiata) {
        this.parcheggiata = parcheggiata;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.targa);
        hash = 67 * hash + Objects.hashCode(this.marca);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auto other = (Auto) obj;
        if (!Objects.equals(this.targa, other.targa)) {
            return false;
        }
        if (!Objects.equals(this.marca, other.marca)) {
            return false;
        }
        return true;
    }

    public float getMediaOre() {
        return mediaOre;
    }
 
}
