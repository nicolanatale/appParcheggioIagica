/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.dao;

import it.mycompany.parcheggiospringboot.ApplicationCostants;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelConfiguration;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.entity.AutoParcheggiate;
import it.mycompany.parcheggiospringboot.model.entity.VistaGrafico;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicola
 */
public class VistaGraficoDao {
    private Connection dbParkConnection;

    public VistaGraficoDao() {
        this.dbParkConnection = (Connection) ParcheggioModelConfiguration.getConfigurationValueFromKey(ApplicationCostants.KEY_DATABASE);
    }
    
    public List<VistaGrafico> loadData(LocalDate data) throws ParcheggioModelException{
        List<VistaGrafico> vistaGrafico = new ArrayList<>();
        try {
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadData);
            
            ps.setDate(1, Date.valueOf(data));
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String targa = rs.getNString("targa");
                LocalDateTime uscita = rs.getTimestamp("uscita").toLocalDateTime();
                String luogoParcheggio = rs.getNString("luogo_parcheggio");
                int idPark = rs.getInt("id_park");
                float totGuadagnato = rs.getFloat("tot_guadagno");
                float mediaOre = rs.getFloat("media_ore");
                vistaGrafico.add(new VistaGrafico(id,targa,uscita,luogoParcheggio,idPark,totGuadagnato,mediaOre));
            }
        } catch (SQLException ex) {
            throw new ParcheggioModelException("VistaGraficoDao -> loadData -> " + ex.getMessage());
        }
        return vistaGrafico;
    }
    
    public List<VistaGrafico> loadDataMese(LocalDate data) throws ParcheggioModelException{
        List<VistaGrafico> vistaGrafico = new ArrayList<>();
        try {
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.loadDataMese);
            
            
            ps.setInt(1, data.getYear());
            ps.setInt(2, data.getMonthValue());
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int id = rs.getInt("id");
                String targa = rs.getNString("targa");
                LocalDateTime uscita = rs.getTimestamp("uscita").toLocalDateTime();
                String luogoParcheggio = rs.getNString("luogo_parcheggio");
                int idPark = rs.getInt("id_park");
                float totGuadagnato = rs.getFloat("tot_guadagno");
                float mediaOre = rs.getFloat("media_ore");
                vistaGrafico.add(new VistaGrafico(id,targa,uscita,luogoParcheggio,idPark,totGuadagnato,mediaOre));
            }
        } catch (SQLException ex) {
            throw new ParcheggioModelException("VistaGraficoDao -> loadData -> " + ex.getMessage());
        }
        return vistaGrafico;
    } 
}
