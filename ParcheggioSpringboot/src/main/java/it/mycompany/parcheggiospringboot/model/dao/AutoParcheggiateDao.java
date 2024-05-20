/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.dao;

import it.mycompany.parcheggiospringboot.ApplicationCostants;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelConfiguration;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.entity.AutoParcheggiate;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicola Natale
 */
public class AutoParcheggiateDao {
    private Connection dbParkConnection;

    public AutoParcheggiateDao() {
        this.dbParkConnection = (Connection) ParcheggioModelConfiguration.getConfigurationValueFromKey(ApplicationCostants.KEY_DATABASE);
    }
    
    public List<AutoParcheggiate> loadAutoParcheggiate() throws ParcheggioModelException{
        List<AutoParcheggiate> auto = new ArrayList<>();
        try{
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadAutoParcheggiate);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String targa = rs.getNString("targa");
                String marca = rs.getNString("marca");
                String modello = rs.getNString("modello");
                LocalDateTime ingresso = rs.getTimestamp("ingresso").toLocalDateTime();
                String luogoParcheggio = rs.getNString("luogo_parcheggio");
                int idPark = rs.getInt("id_park");
                auto.add(new AutoParcheggiate(id,ingresso,targa,marca,modello,luogoParcheggio,idPark));
            }
                   
        } catch(SQLException ex){
            throw new ParcheggioModelException("AutoParcheggiateDao -> loadAutoParcheggiate -> " + ex.getMessage());
        }
        return auto;
    }
    
    public AutoParcheggiate loadAutoParcheggiateId(int idp) throws ParcheggioModelException{
        AutoParcheggiate auto = null;
        try{
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadAutoParcheggiateId);
            ps.setInt(1, idp);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String targa = rs.getNString("targa");
                String marca = rs.getNString("marca");
                String modello = rs.getNString("modello");
                LocalDateTime ingresso = rs.getTimestamp("ingresso").toLocalDateTime();
                String luogoParcheggio = rs.getNString("luogo_parcheggio");
                int idPark = rs.getInt("id_park");
                auto = new AutoParcheggiate(id,ingresso,targa,marca,modello,luogoParcheggio,idPark);
            }
                   
        } catch(SQLException ex){
            throw new ParcheggioModelException("AutoParcheggiateDao -> loadAutoParcheggiate -> " + ex.getMessage());
        }
        return auto;
    }
    
}
