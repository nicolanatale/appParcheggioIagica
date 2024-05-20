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
public class Ticket {
    private int id;
    private LocalDateTime ingresso;
    private LocalDateTime uscita;
    private float prezzo;
    private int idParcheggio;
    private String idAuto;
    private int orePassate;

    public Ticket() {
        super();
    }
    
    public Ticket(int id, LocalDateTime ingresso, int idParcheggio, String idAuto) {
        this.id = id;
        this.ingresso = ingresso;
        this.idParcheggio = idParcheggio;
        this.idAuto = idAuto;
    }

    public Ticket(int id, LocalDateTime ingresso, LocalDateTime uscita, float prezzo, int idParcheggio, String idAuto, int orePassate) {
        this.id = id;
        this.ingresso = ingresso;
        this.uscita = uscita;
        this.prezzo = prezzo;
        this.idParcheggio = idParcheggio;
        this.idAuto = idAuto;
        this.orePassate = orePassate;
    }
    
    
    public int getId() {
        return id;
    }

    public LocalDateTime getIngresso() {
        return ingresso;
    }

    public LocalDateTime getUscita() {
        return uscita;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public int getIdParcheggio() {
        return idParcheggio;
    }

    public String getIdAuto() {
        return idAuto;
    }

    public void setUscita(LocalDateTime uscita) {
        this.uscita = uscita;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getOrePassate() {
        return orePassate;
    }

    public void setOrePassate(int orePassate) {
        this.orePassate = orePassate;
    }
    
}
