/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.dao;

import it.mycompany.parcheggiospringboot.ApplicationCostants;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelConfiguration;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.entity.Auto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicola Natale
 */
public class AutoDao {
    private Connection dbParkConnection;

    public AutoDao() {
        this.dbParkConnection = (Connection) ParcheggioModelConfiguration.getConfigurationValueFromKey(ApplicationCostants.KEY_DATABASE);
    }
    
    private List<Auto> stampaAutoPrepared(PreparedStatement ps) throws ParcheggioModelException{
        List<Auto> auto = new ArrayList<>();
        try{
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String targa = rs.getNString("targa");
                String marca = rs.getNString("marca");
                String modello = rs.getNString("modello");
                boolean parcheggiata = rs.getBoolean("parcheggiata");
                float mediaOre = rs.getFloat("media_ore");
                auto.add(new Auto(id, targa, marca, modello, parcheggiata,mediaOre));
            }
                   
        } catch(SQLException ex){
            throw new ParcheggioModelException("AutoDao -> stampaAutoPrepared -> " + ex.getMessage());
        }
        return auto;
    }
    
    public List<Auto> stampaAuto() throws ParcheggioModelException{
        List<Auto> auto = new ArrayList<>();
        try {
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadAuto);
            auto = stampaAutoPrepared(ps);
        } catch (SQLException ex) {
            throw new ParcheggioModelException("AutoDao -> stampaAuto -> " + ex.getMessage());
        }
        return auto;
    }
    
    public void inserisciAuto(Auto a) throws ParcheggioModelException, AutoPresenteDbException {
        Trigger.checkFormatoTarga(a.getTarga());
        Trigger.validatorAuto(a);
                
        try{
            Trigger.checkAutoEsistenteDb(a);
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.insertAuto, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, a.getTarga());
            ps.setString(2, a.getMarca());
            ps.setString(3, a.getModello());
            ps.setBoolean(4, false);
            ps.executeUpdate();
            
            ResultSet rsId = ps.getGeneratedKeys();
            
            if(rsId.next()) {
                a.setId(rsId.getInt("id"));
            }
            
        } catch(SQLException ex){
            throw new ParcheggioModelException("AutoDao -> inserisciAuto -> " + ex.getMessage());
        } catch (AutoPresenteDbException ex) {
            throw new AutoPresenteDbException("Auto presente nel sistema");
        }
        
    }
    
    public Auto stampaAutoTarga(String targa) throws ParcheggioModelException{
        Auto auto = null;
        try {
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadTarga);
            ps.setString(1, targa);
            List<Auto> a = stampaAutoPrepared(ps);
            
            if(a.size() > 1)
                throw new ParcheggioModelException("Errore: sono presenti due auto con la stessa targa.");
            
            if(a.size() == 1)
                auto = a.get(0);
            
        } catch (SQLException ex) {
            throw new ParcheggioModelException("AutoDao -> stampaAutoTarga -> " + ex.getMessage());
        }
        return auto;
    }
    
}
