/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.dao;

import it.mycompany.parcheggiospringboot.ApplicationCostants;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelConfiguration;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.entity.Parcheggio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicola Natale
 */
public class ParcheggioDao {
    private Connection dbParkConnection;

    public ParcheggioDao() {
        this.dbParkConnection = (Connection) ParcheggioModelConfiguration.getConfigurationValueFromKey(ApplicationCostants.KEY_DATABASE);
    }
    
    public void inserisciParcheggio(Parcheggio p) throws ParcheggioModelException{
        Trigger.checkParcheggioVincoloMassimo(p);
        Trigger.validatorParcheggio(p);
        
        try{
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.insertParcheggio);
            ps.setString(1, p.getLuogo());
            ps.setString(2, p.getIndirizzo());
            ps.setString(3,p.getNumTelefono());
            ps.executeUpdate();
        } catch(SQLException ex){
            throw new ParcheggioModelException("ParcheggioDao -> inserisciAuto -> " + ex.getMessage());
        }
    }
    
    private List<Parcheggio> loadParcheggioPrepared(PreparedStatement ps) throws ParcheggioModelException{
        List<Parcheggio> p = new ArrayList<>();
        try{
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String luogo = rs.getNString("luogo");
                String indirizzo = rs.getNString("indirizzo");
                String num_telefono = rs.getNString("num_telefono");
                byte postiDisponibili = rs.getByte("posti_disponibili");
                byte postiMax = rs.getByte("posti_max");
                float totGuadagno = rs.getFloat("tot_guadagno");
                p.add(new Parcheggio(id,luogo,indirizzo,num_telefono,postiDisponibili,postiMax,totGuadagno));
            }
                   
        } catch(SQLException ex){
            throw new ParcheggioModelException("ParcheggioDao -> loadParcheggioPrepared -> " + ex.getMessage());
        }
        return p;
    }
    
    public Parcheggio loadParcheggioId(int idp) throws ParcheggioModelException{
        Parcheggio p = null;
        try{
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadParcheggio);
            ps.setInt(1, idp);     
            List<Parcheggio> parcheggi = loadParcheggioPrepared(ps);
            
            if(parcheggi.size() > 1)
                throw new ParcheggioModelException("Errore: più di un parcheggio con lo stesso id.");
            
            if(parcheggi.size() == 1)
                p = parcheggi.get(0);
            
        } catch(SQLException ex){
            throw new ParcheggioModelException("ParcheggioDao -> loadParcheggioId -> " + ex.getMessage());
        }
        return p;
    }
    
    public List<Parcheggio> loadParcheggi() throws ParcheggioModelException{
        List<Parcheggio> parcheggi = new ArrayList<>();
        try{
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadParcheggi);     
            parcheggi = loadParcheggioPrepared(ps);
            
        } catch(SQLException ex){
            throw new ParcheggioModelException("ParcheggioDao -> loadParcheggi -> " + ex.getMessage());
        }
        return parcheggi;
    }
}
