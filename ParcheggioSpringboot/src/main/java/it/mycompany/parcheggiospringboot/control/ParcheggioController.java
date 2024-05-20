/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.control;

import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.dao.AutoDao;
import it.mycompany.parcheggiospringboot.model.dao.AutoParcheggiateDao;
import it.mycompany.parcheggiospringboot.model.dao.AutoPresenteDbException;
import it.mycompany.parcheggiospringboot.model.dao.LoginInputDao;
import it.mycompany.parcheggiospringboot.model.dao.ParcheggioDao;
import it.mycompany.parcheggiospringboot.model.dao.TicketDao;
import it.mycompany.parcheggiospringboot.model.dao.VistaGraficoDao;
import it.mycompany.parcheggiospringboot.model.entity.Auto;
import it.mycompany.parcheggiospringboot.model.entity.AutoParcheggiate;
import it.mycompany.parcheggiospringboot.model.entity.LoginInput;
import it.mycompany.parcheggiospringboot.model.entity.LoginOutput;
import it.mycompany.parcheggiospringboot.model.entity.Parcheggio;
import it.mycompany.parcheggiospringboot.model.entity.Ticket;
import it.mycompany.parcheggiospringboot.model.entity.VistaGrafico;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author Nicola Natale
 */
@Component

public class ParcheggioController {
    
    private AutoDao autoDao;
    private ParcheggioDao parcheggioDao;
    private TicketDao ticketDao;
    private AutoParcheggiateDao autoParcheggiateDao;
    private VistaGraficoDao vistaGraficoDao;

    public ParcheggioController() {
        this.autoDao = new AutoDao();
        this.parcheggioDao = new ParcheggioDao();
        this.ticketDao = new TicketDao();
        this.autoParcheggiateDao = new AutoParcheggiateDao();
        this.vistaGraficoDao = new VistaGraficoDao();
    }
    
    public List<Auto> stampaAutoController() throws ParcheggioControlException{
        List<Auto> auto = new ArrayList<>();
        try{
            auto = this.autoDao.stampaAuto();
        }catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("AutoController -> stampaAutoController -> " + ex.getMessage());
        }
        return auto;
    }
    
    public Auto stampaAutoTargaController(String targa) throws ParcheggioControlException{
        Auto auto = null;
        try{
            auto = this.autoDao.stampaAutoTarga(targa);
        }catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("AutoController -> stampaAutoController -> " + ex.getMessage());
        }
        return auto;
    }
    
    public void inserisciAutoController(Auto a) throws ParcheggioControlException, AutoPresenteDbException {
        try{
            this.autoDao.inserisciAuto(a);
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("AutoController -> inserisciAutoController -> " + ex.getMessage());
        } catch(AutoPresenteDbException ex){
            throw new AutoPresenteDbException("AutoController -> inserisciAutoController -> " + ex.getMessage());
        } 
        
    }
    
    public void inserisciParcheggioController(Parcheggio p) throws ParcheggioControlException{
        try{
            this.parcheggioDao.inserisciParcheggio(p);
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("ParcheggioController -> inserisciParcheggioController -> " + ex.getMessage());
        }
    }
    
    public void generaTicketController(Ticket t) throws ParcheggioControlException{
        try{
            this.ticketDao.generaTicket(t);
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("TicketController -> generaTicketController -> " + ex.getMessage());
        }
    }
    
    public void updateTicketController(Ticket t) throws ParcheggioControlException{
        try{
            this.ticketDao.updateTicket(t);
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("TicketController -> updateTicketController -> " + ex.getMessage());
        }
    }
    
    public List<Parcheggio> loadParcheggiController() throws ParcheggioControlException{
        List<Parcheggio> p = new ArrayList<>();
        try{
            p = this.parcheggioDao.loadParcheggi();
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("ParcheggioController -> loadParcheggiController -> " + ex.getMessage());
        }
        return p;
    }
    
    public List<AutoParcheggiate> loadAutoParcheggiateController() throws ParcheggioControlException{
        List<AutoParcheggiate> a = new ArrayList<>();
        try{
            a = this.autoParcheggiateDao.loadAutoParcheggiate();
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("AutoParcheggiateController -> loadAutoParcheggiateController -> " + ex.getMessage());
        }
        return a;
    }
    
    public Ticket calcolaUscitaEPrezzo(int id) throws ParcheggioControlException{
       Ticket ti = null;
        try {
            ti = this.ticketDao.loadTicketId(id);
            ti.setUscita(LocalDateTime.now());
            HashMap<Float,Integer> map = ParcheggioService.calcolaPrezzo(ti.getIngresso(), ti.getUscita());
            ti.setPrezzo(ParcheggioService.recuperaPrezzo(map));
            ti.setOrePassate(ParcheggioService.recuperaOre(map));
            
        } catch (ParcheggioModelException ex) {
            throw new ParcheggioControlException("TicketController -> updateTicketController -> " + ex.getMessage());
        }
  
        return ti;
    }
    
    public AutoParcheggiate loadAutoParcheggiateIdController(int id) throws ParcheggioControlException{
        AutoParcheggiate auto = null;
        try{
            auto = this.autoParcheggiateDao.loadAutoParcheggiateId(id);
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("AutoParcheggiateController -> loadAutoParcheggiateController -> " + ex.getMessage());
        }
        return auto;
    }
    
    public List<Ticket> loadAllTicketsController() throws ParcheggioControlException{
        List<Ticket> t = new ArrayList<>();
        try{
            t = this.ticketDao.loadAllTickets();
        } catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("AutoParcheggiateController -> loadAutoParcheggiateController -> " + ex.getMessage());
        }
        return t;
    }
    
    public List<VistaGrafico> stampaVistaGraficoController(LocalDate data) throws ParcheggioControlException{
        List<VistaGrafico> vistaGrafico = new ArrayList<>();
        try{
            vistaGrafico = this.vistaGraficoDao.loadData(data);
        }catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("VistaGraficoController -> loadDataController -> " + ex.getMessage());
        }
        return vistaGrafico;
    }
    
    public List<VistaGrafico> stampaVistaGraficoMeseController(LocalDate data) throws ParcheggioControlException{
        List<VistaGrafico> vistaGrafico = new ArrayList<>();
        try{
            vistaGrafico = this.vistaGraficoDao.loadDataMese(data);
        }catch(ParcheggioModelException ex){
            throw new ParcheggioControlException("VistaGraficoController -> loadDataController -> " + ex.getMessage());
        }
        return vistaGrafico;
    }
    
}
