/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.entity;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Nicola Natale
 */
public class AutoParcheggiate {
    private int idTicket;
    private LocalDateTime ingresso;
    private String targa;
    private String marca;
    private String modello;
    private String luogoParcheggio;
    private int idPark;

    public AutoParcheggiate(int idTicket, LocalDateTime ingresso, String targa, String marca, String modello, String luogoParcheggio,int idPark) {
        this.idTicket = idTicket;
        this.ingresso = ingresso;
        this.targa = targa;
        this.marca = marca;
        this.modello = modello;
        this.luogoParcheggio = luogoParcheggio;
        this.idPark = idPark;
    }

    public int getIdPark() {
        return idPark;
    }
   
    public int getIdTicket() {
        return idTicket;
    }

    public LocalDateTime getIngresso() {
        return ingresso;
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

    public String getLuogoParcheggio() {
        return luogoParcheggio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.idTicket;
        hash = 53 * hash + Objects.hashCode(this.targa);
        hash = 53 * hash + this.idPark;
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
        final AutoParcheggiate other = (AutoParcheggiate) obj;
        if (this.idTicket != other.idTicket) {
            return false;
        }
        if (this.idPark != other.idPark) {
            return false;
        }
        if (!Objects.equals(this.targa, other.targa)) {
            return false;
        }
        return true;
    }
    
}
