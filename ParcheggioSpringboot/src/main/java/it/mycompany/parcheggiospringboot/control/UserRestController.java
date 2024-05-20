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

@RestController
@RequestMapping("/log-parcheggio")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class UserRestController {

    @Autowired
    private ParcheggioController utente;

    @GetMapping(path = "/elenco-auto")
    public List<Auto> actionStampaAuto() {
        List<Auto> auto = new ArrayList<>();

        try {

            auto = this.utente.stampaAutoController();

        } catch (ParcheggioControlException ex) {

        }

        return auto;

    }

    @PostMapping(path = "/aggiorna-ticket")
    public Map<String, String> actionAggiornaTicket(@RequestBody(required = true) Ticket t) {
        String esitoAdd = "false";
        String message = "";
        try {

            this.utente.updateTicketController(t);
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

    @GetMapping(path = "/auto-parcheggiate")
    public List<AutoParcheggiate> actionloadAutoParcheggiate() {
        List<AutoParcheggiate> auto = new ArrayList<>();

        try {

            auto = this.utente.loadAutoParcheggiateController();

        } catch (ParcheggioControlException ex) {

        }

        return auto;

    }

    @PostMapping(path = "/verifica-ticket")
    public Ticket actionVerificaTicket(@RequestBody(required = true) Integer idTicket) {
        Ticket t = null;

        try {

            t = this.utente.calcolaUscitaEPrezzo(idTicket);

        } catch (ParcheggioControlException ex) {

        }

        return t;

    }

    @GetMapping(path = "/autopark-id")
    public AutoParcheggiate actionAutoparkId(@RequestParam(required = true) Integer id) {
        AutoParcheggiate ap = null;

        try {

            ap = this.utente.loadAutoParcheggiateIdController(id);

        } catch (ParcheggioControlException ex) {

        }

        return ap;

    }

    @GetMapping(path = "/elenco-tickets")
    public List<Ticket> actionLoadAllTickets() {
        List<Ticket> ticket = new ArrayList<>();

        try {

            ticket = this.utente.loadAllTicketsController();

        } catch (ParcheggioControlException ex) {

        }

        return ticket;

    }
    
}
