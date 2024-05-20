/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.entity;

import java.time.LocalDateTime;

/**
 *
 * @author Nicola
 */
public class VistaGrafico {
    private int idTicket;
    private String idAuto;
    private LocalDateTime uscita;
    private String luogoParcheggio;
    private int idParcheggio;
    private float guadagno;
    private float ore;

    public VistaGrafico(int idTicket, String idAuto, LocalDateTime uscita, String luogoParcheggio, int idParcheggio, float guadagno, float ore) {
        this.idTicket = idTicket;
        this.idAuto = idAuto;
        this.uscita = uscita;
        this.luogoParcheggio = luogoParcheggio;
        this.idParcheggio = idParcheggio;
        this.guadagno = guadagno;
        this.ore = ore;
    }

    public int getIdTicket() {
        return idTicket;
    }

    public String getIdAuto() {
        return idAuto;
    }

    public LocalDateTime getUscita() {
        return uscita;
    }

    public String getLuogoParcheggio() {
        return luogoParcheggio;
    }

    public int getIdParcheggio() {
        return idParcheggio;
    }

    public float getGuadagno() {
        return guadagno;
    }

    public float getOre() {
        return ore;
    }
            
            
}
