/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.dao;

import it.mycompany.parcheggiospringboot.ApplicationCostants;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.entity.Auto;
import it.mycompany.parcheggiospringboot.model.entity.Parcheggio;
import it.mycompany.parcheggiospringboot.model.entity.Ticket;
import java.util.List;

/**
 *
 * @author Nicola Natale
 */
public class Trigger {

    public static void checkSeParcheggiata(Ticket t) throws ParcheggioModelException {

        Auto a = new AutoDao().stampaAutoTarga(t.getIdAuto());

        if (a == null) {

            throw new ParcheggioModelException("Errore: l'auto non esiste.");

        } else if (a.isParcheggiata()) {

            throw new ParcheggioModelException("Errore: l'auto è già parcheggiata.");

        }

    }

    public static void checkParcheggioVincoloMinimo(Ticket t) throws ParcheggioModelException {

        Parcheggio p = new ParcheggioDao().loadParcheggioId(t.getIdParcheggio());

        if (p == null) {

            throw new ParcheggioModelException("Errore: il parcheggio non esiste.");

        } else if (p.getPostiDisponibili() == 0) {

            throw new ParcheggioModelException("Errore: il parcheggio è pieno.");

        }

    }

    public static void checkParcheggioVincoloMassimo(Parcheggio p) throws ParcheggioModelException {

        if (p == null) {

            throw new ParcheggioModelException("Errore: il parcheggio non esiste.");

        } else if (p.getPostiDisponibili() > p.getPostiMax()) {

            throw new ParcheggioModelException("Errore: il parcheggio può contenere " + p.getPostiMax() + "auto.");
        }

    }

    public static void checkTicket(Ticket t) throws ParcheggioModelException {

        Ticket ti = new TicketDao().loadTicketId(t.getId());

        if (t == null) {

            throw new ParcheggioModelException("Errore: il ticket non esiste.");

        } else if (ti.getId() != t.getId()) {

            throw new ParcheggioModelException("Errore: si sta modificando un ticket differente.");

        }

    }

    public static void checkAutoEsistenteDb(Auto a) throws ParcheggioModelException, AutoPresenteDbException {

        List<Auto> autoL = new AutoDao().stampaAuto();
        
        int stato = 0;

        for (int i = 0; i < autoL.size(); i++) {

            if (a == null) {

                throw new ParcheggioModelException("Errore: l'auto non esiste.");

            } else if (a.getTarga().equalsIgnoreCase(autoL.get(i).getTarga())) {
                
                throw new AutoPresenteDbException("Auto presente nel sistema");
                
            } 

        }
        
    }

    public static void checkAutoEsistenteDbTicket(Ticket t) throws ParcheggioModelException {

        Auto a = new AutoDao().stampaAutoTarga(t.getIdAuto());
        List<Auto> autoL = new AutoDao().stampaAuto();

        for (int i = 0; i < autoL.size(); i++) {

            if (a == null) {

                throw new ParcheggioModelException("Errore: l'auto non esiste.");

            } else if (a.getTarga().equalsIgnoreCase(autoL.get(i).getTarga())
                    && !(a.getMarca().equalsIgnoreCase(autoL.get(i).getMarca())
                    && a.getModello().equalsIgnoreCase(autoL.get(i).getModello()))) {

                throw new ParcheggioModelException("Errore: stessa auto gia presente");

            }

        }

    }

    public static void checkNonParcheggiata(Ticket t) throws ParcheggioModelException {

        Auto a = new AutoDao().stampaAutoTarga(t.getIdAuto());

        if (a == null) {

            throw new ParcheggioModelException("Errore: l'auto non esiste.");

        } else if (!a.isParcheggiata()) {

            throw new ParcheggioModelException("Errore: l'auto è già parcheggiata.");

        }

    }
    
    public static void checkFormatoTarga(String s) throws ParcheggioModelException{
        if(s.length() >= 7 && !s.matches("[A-Z]{2}[0-9]{3}[A-Z]{2}"))
                    throw new ParcheggioModelException("Formato targa errato.");
    }
    
    public static void validatorAuto(Auto a) throws ParcheggioModelException{
        if(a.getMarca().length() > 50 && a.getModello().length() > 50)
                    throw new ParcheggioModelException("Formato marca o modello errato.");
    }
    
    public static void validatorParcheggio(Parcheggio p) throws ParcheggioModelException{
        if(p.getIndirizzo().length() > 50 && p.getLuogo().length() > 50 && p.getNumTelefono().length() > 10)
                    throw new ParcheggioModelException("Formato indirizzo, luogo o numero di telefono errato.");
    }
}
