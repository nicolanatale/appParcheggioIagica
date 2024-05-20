/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.control;

import it.mycompany.parcheggiospringboot.model.dao.AutoPresenteDbException;
import it.mycompany.parcheggiospringboot.model.entity.Auto;
import it.mycompany.parcheggiospringboot.model.entity.AutoParcheggiate;
import it.mycompany.parcheggiospringboot.model.entity.Parcheggio;
import it.mycompany.parcheggiospringboot.model.entity.Ticket;
import it.mycompany.parcheggiospringboot.model.entity.VistaGrafico;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nicola Natale
 */
@RestController
@RequestMapping("/admin-parcheggio")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AdminRestController {

    @Autowired
    private ParcheggioController utente;

    @PostMapping(path = "/aggiungi-parcheggio")
    public Map<String, String> actionInserisciParcheggio(@RequestBody(required = true) Parcheggio p) {
        String esitoAdd = "false";
        String message = "";
        try {

            this.utente.inserisciParcheggioController(p);
            esitoAdd = "true";

        } catch (ParcheggioControlException ex) {
            String[] splitMsg = ex.getMessage().split("->");
            message = splitMsg[2];
        }

        Map<String, String> esito = new HashMap<>();
        esito.put("esito", esitoAdd);
        esito.put("message",message);
        return esito;

    }

    @GetMapping(path = "/statistiche-giorno")
    public List<VistaGrafico> actionLoadData(@RequestParam (required = true) String data) {
        List<VistaGrafico> vistaGrafico = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LocalDate localDate = LocalDate.parse(data, formatter);
        
        try {
            
            vistaGrafico = this.utente.stampaVistaGraficoController(localDate);

        } catch (ParcheggioControlException ex) {
            
        }

        return vistaGrafico;

    }
    
    @GetMapping(path = "/statistiche-mese")
    public List<VistaGrafico> actionLoadDataMese(@RequestParam (required = true) String data) {
        List<VistaGrafico> vistaGrafico = new ArrayList<>();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");

        LocalDate localDate = LocalDate.parse(data, formatter);
        
        try {
            
            vistaGrafico = this.utente.stampaVistaGraficoMeseController(localDate);

        } catch (ParcheggioControlException ex) {
            
        }

        return vistaGrafico;

    }
    
}
