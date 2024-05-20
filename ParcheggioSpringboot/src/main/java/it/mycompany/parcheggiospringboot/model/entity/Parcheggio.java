/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.entity;

/**
 *
 * @author Nicola Natale
 */
public class Parcheggio {
    private int id;
    private String luogo;
    private String indirizzo;
    private String numTelefono;
    private byte postiDisponibili;
    private byte postiMax;
    private float totGuadagno;

    public Parcheggio() {
        super();
    }

    public Parcheggio(int id, String luogo, String indirizzo, String numTelefono, byte postiDisponibili, byte postiMax, float totGuadagno) {
        this.id = id;
        this.luogo = luogo;
        this.indirizzo = indirizzo;
        this.numTelefono = numTelefono;
        this.postiDisponibili = postiDisponibili;
        this.postiMax = postiMax;
        this.totGuadagno = totGuadagno;
    }

    public int getId() {
        return id;
    }

    public String getLuogo() {
        return luogo;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getNumTelefono() {
        return numTelefono;
    }

    public byte getPostiDisponibili() {
        return postiDisponibili;
    }

    public byte getPostiMax() {
        return postiMax;
    }

    public void setPostiMax(byte postiMax) {
        this.postiMax = postiMax;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + this.id;
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
        final Parcheggio other = (Parcheggio) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public float getTotGuadagno() {
        return totGuadagno;
    }
    
    
}
