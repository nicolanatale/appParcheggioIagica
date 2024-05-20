/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.dao;

import it.mycompany.parcheggiospringboot.ApplicationCostants;
import it.mycompany.parcheggiospringboot.control.ParcheggioService;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelConfiguration;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.entity.Auto;
import it.mycompany.parcheggiospringboot.model.entity.Parcheggio;
import it.mycompany.parcheggiospringboot.model.entity.Ticket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Nicola Natale
 */
public class TicketDao {
    private Connection dbParkConnection;

    public TicketDao() {
        this.dbParkConnection = (Connection) ParcheggioModelConfiguration.getConfigurationValueFromKey(ApplicationCostants.KEY_DATABASE);
    }
    
    public void generaTicket(Ticket t) throws ParcheggioModelException{
        
        Trigger.checkSeParcheggiata(t);
        Trigger.checkParcheggioVincoloMinimo(t);
        Trigger.checkAutoEsistenteDbTicket(t);
        
        try{
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.insertTicket);
            PreparedStatement pss = this.dbParkConnection.prepareStatement(QueryDictionary.updateStatoAuto);
            PreparedStatement psss = this.dbParkConnection.prepareStatement(QueryDictionary.updatePostiParcheggio);
            
            ps.setTimestamp(1,  Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, t.getIdParcheggio());
            ps.setString(3,t.getIdAuto());
            
            pss.setBoolean(1, true);
            pss.setString(2, t.getIdAuto());
            
            psss.setByte(1,ParcheggioService.decrementaPosti(t));
            psss.setInt(2,t.getIdParcheggio());
            
            this.dbParkConnection.setAutoCommit(false);
            
            ps.executeUpdate();
            pss.executeUpdate();
            psss.executeUpdate();
            
            this.dbParkConnection.commit();
            this.dbParkConnection.setAutoCommit(true);
            
        } catch(SQLException ex){
            throw new ParcheggioModelException("TicketDao -> generaTicket -> " + ex.getMessage());
        }
    }
    
    public void updateTicket(Ticket t) throws ParcheggioModelException{
        Trigger.checkNonParcheggiata(t);
        Trigger.checkTicket(t);
        HashMap<Float,Integer> calcolo = ParcheggioService.calcolaPrezzo(t.getIngresso(),t.getUscita());
        
        try {
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.updateTicket);
            PreparedStatement psParcheggiata = this.dbParkConnection.prepareStatement(QueryDictionary.updateStatoAuto);
            PreparedStatement psPosti = this.dbParkConnection.prepareStatement(QueryDictionary.updatePostiParcheggio);
            PreparedStatement psMediaOre = this.dbParkConnection.prepareStatement(QueryDictionary.updateMediaOre);
            PreparedStatement psGuadagno = this.dbParkConnection.prepareStatement(QueryDictionary.updateGuadagno);
            
            ps.setTimestamp(1, Timestamp.valueOf(t.getUscita()));
            ps.setFloat(2,t.getPrezzo());
            ps.setInt(3, t.getOrePassate());
            ps.setInt(4, t.getId());
            
            psParcheggiata.setBoolean(1, false);
            psParcheggiata.setString(2, t.getIdAuto());
            
            psPosti.setByte(1,ParcheggioService.incrementaPosti(t));
            psPosti.setInt(2,t.getIdParcheggio());
            
            
            psGuadagno.setFloat(1,ParcheggioService.aggiornaGuadagno(t));
            psGuadagno.setInt(2,t.getIdParcheggio());
            
            this.dbParkConnection.setAutoCommit(false);
            
            ps.executeUpdate();
            psParcheggiata.executeUpdate();
            psPosti.executeUpdate();
            psGuadagno.executeUpdate();
            
            this.dbParkConnection.commit();
            this.dbParkConnection.setAutoCommit(true);
            
            psMediaOre.setFloat(1, ParcheggioService.mediaOre(t.getIdAuto()));
            psMediaOre.setString(2, t.getIdAuto());
            
            psMediaOre.executeUpdate();
            
        } catch (SQLException ex) {
            throw new ParcheggioModelException("TicketDao -> updateTicket -> " + ex.getMessage());
        }
    }
    
    public Ticket loadTicketId(int idp) throws ParcheggioModelException{
        Ticket t = null;
        try{

            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadTicket);
            ps.setInt(1, idp);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int id = rs.getInt("id");
                LocalDateTime ingresso = rs.getTimestamp("ingresso").toLocalDateTime();
                int idParcheggio = rs.getInt("parcheggio");
                String idAuto = rs.getNString("auto");

                t = new Ticket(id,ingresso,idParcheggio,idAuto);

            }

        } catch(SQLException ex){
            throw new ParcheggioModelException("TicketDao -> loadTicketId -> " + ex.getMessage());
        }
        return t;
    }

    public List<Ticket> loadAllTickets() throws ParcheggioModelException{
        List<Ticket> ti = new ArrayList<>();
        try{
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadAllTickets);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int id = rs.getInt("id");
                LocalDateTime ingresso = rs.getTimestamp("ingresso").toLocalDateTime();
                LocalDateTime uscita = rs.getTimestamp("uscita").toLocalDateTime();
                int orePassate = rs.getInt("ore_passate");
                float prezzo = rs.getFloat("prezzo");
                int idParcheggio = rs.getInt("parcheggio");
                String idAuto = rs.getNString("auto");

                ti.add(new Ticket(id,ingresso,uscita,prezzo,idParcheggio,idAuto,orePassate));

            }
        } catch(SQLException ex){
            throw new ParcheggioModelException("TicketDao -> loadAllTickets -> " + ex.getMessage());
        }
        return ti;
    }
    
    public List<Ticket> loadTicketTarga(String targa) throws ParcheggioModelException{
        List<Ticket> ti = new ArrayList<>();
        try{

            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadTicketTarga);
            ps.setNString(1, targa);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                int id = rs.getInt("id");
                LocalDateTime ingresso = rs.getTimestamp("ingresso").toLocalDateTime();
                LocalDateTime uscita = rs.getTimestamp("uscita").toLocalDateTime();
                int orePassate = rs.getInt("ore_passate");
                float prezzo = rs.getFloat("prezzo");
                int idParcheggio = rs.getInt("parcheggio");
                String idAuto = rs.getNString("auto");

                ti.add(new Ticket(id,ingresso,uscita,prezzo,idParcheggio,idAuto,orePassate));

            }

        } catch(SQLException ex){
            throw new ParcheggioModelException("TicketDao -> loadTicketTarga -> " + ex.getMessage());
        }
        return ti;
    }
    
}
