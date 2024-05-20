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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nicola
 */

    

/**
 *
 * @author Nicola Natale
 */
@RestController
@RequestMapping("/parcheggio")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ClienteRestController {

    @Autowired
    private ParcheggioController utente;

    @SuppressWarnings("finally")
    @PostMapping(path = "/aggiungi-auto")
    public Map<String, String> actionInserisciAuto(@RequestBody(required = true) Auto a) {
        String esitoAdd = "false";
        String message = "";
        Map<String, String> esito = new HashMap<>();
        try {

            this.utente.inserisciAutoController(a);
            esitoAdd = "true";

        } catch (AutoPresenteDbException ex) { 
            esitoAdd = "true";
        } catch (ParcheggioControlException ex) {
           String[] splitMsg = ex.getMessage().split("->");
           message = splitMsg[2];
        } finally {
            
        esito.put("esito", esitoAdd);
        esito.put("message", message);
        return esito;
        }

    }

    @PostMapping(path = "/genera-ticket")
    public Map<String, String> actionGeneraTicket(@RequestBody(required = true) Ticket t) {
        String esitoAdd = "false";
        String message = "";

        try {

            this.utente.generaTicketController(t);
            esitoAdd = "true";

        } catch (ParcheggioControlException ex) {
            String[] splitMsg = ex.getMessage().split("->");
            message = splitMsg[2];
        }

        Map<String, String> esito = new HashMap<>();
        esito.put("esito", esitoAdd);
        esito.put("message", message);
        return esito;

    }

    @GetMapping(path = "/elenco-parcheggi")
    public List<Parcheggio> actionStampaParcheggi() {
        List<Parcheggio> parcheggi = new ArrayList<>();

        try {

            parcheggi = this.utente.loadParcheggiController();

        } catch (ParcheggioControlException ex) {

        }

        return parcheggi;

    }
    
    @GetMapping(path = "/elenco-auto-targa")
    public Auto actionStampaAutoTarga(@RequestParam(required = true) String targa) {
        Auto auto = null;

        try {

            auto = this.utente.stampaAutoTargaController(targa);

        } catch (ParcheggioControlException ex) {

        }

        return auto;

    }

}
