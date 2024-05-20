/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.control;

import it.mycompany.parcheggiospringboot.ApplicationCostants;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.dao.AutoDao;
import it.mycompany.parcheggiospringboot.model.dao.ParcheggioDao;
import it.mycompany.parcheggiospringboot.model.dao.TicketDao;
import it.mycompany.parcheggiospringboot.model.entity.Auto;
import it.mycompany.parcheggiospringboot.model.entity.LoginInput;
import it.mycompany.parcheggiospringboot.model.entity.Parcheggio;
import it.mycompany.parcheggiospringboot.model.entity.Ticket;
import it.mycompany.parcheggiospringboot.security.Encryption;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;


/**
 *
 * @author Nicola Natale
 */
public class ParcheggioService {
    
    public static byte decrementaPosti(Ticket t) throws ParcheggioModelException{
        Parcheggio p = new ParcheggioDao().loadParcheggioId(t.getIdParcheggio());
        byte aggiornamentoPosti = (byte) (p.getPostiDisponibili() - 1);
        return aggiornamentoPosti;
    }
    
    public static HashMap<Float,Integer> calcolaPrezzo(LocalDateTime dataIngresso, LocalDateTime dataUscita){
        HashMap<Float,Integer> p = new HashMap<>();
        float prezzo = 0f;
        
        Duration d = Duration.between(dataIngresso,dataUscita);
        int orePassate = (int) d.toHours();
        
        if(orePassate <= 1)
            prezzo = ApplicationCostants.QUOTA_ORARIA_INFERIORE_ORA;
        else prezzo = ApplicationCostants.QUOTA_ORARIA_INFERIORE_ORA + ApplicationCostants.QUOTA_ORARIA_SUPERIORE_ORA * orePassate;
        
        p.put(prezzo, orePassate);
        return p;
    }
    
    public static byte incrementaPosti(Ticket t) throws ParcheggioModelException {
        Parcheggio p = new ParcheggioDao().loadParcheggioId(t.getIdParcheggio());
        byte aggiornamentoPosti = (byte) (p.getPostiDisponibili() + 1);
        return aggiornamentoPosti;
    }
    
    public static float aggiornaGuadagno(Ticket t) throws ParcheggioModelException {
        Parcheggio p = new ParcheggioDao().loadParcheggioId(t.getIdParcheggio());
        float guadagno = p.getTotGuadagno() + t.getPrezzo();
        return guadagno;
    }
    
    public static float recuperaPrezzo(HashMap<Float,Integer> map) throws ParcheggioModelException{
        float prezzo = 0f;
        for(Map.Entry<Float, Integer> p : map.entrySet()){
            prezzo = p.getKey();
        }
        return prezzo;
    }
    
    public static int recuperaOre(HashMap<Float,Integer> map) throws ParcheggioModelException{
        int ore = 0;
        for(Map.Entry<Float, Integer> p : map.entrySet()){
            ore = p.getValue();
        }
        return ore;
    }
    
    public static float mediaOre(String targa){
        float media = 0f;
        float sommaOre = 0;
        try {
            List<Ticket> auto = new TicketDao().loadTicketTarga(targa);
            sommaOre = auto.size();
            for(Ticket t : auto){
                media += t.getOrePassate(); 
            }
        } catch (ParcheggioModelException ex) {
            
        }
        return media/sommaOre;
    }
    
}
