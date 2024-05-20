/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.dao;

/**
 *
 * @author Nicola Natale
 */
public class QueryDictionary {
    //query auto
    public static final String loadAuto = "SELECT * FROM park.auto";
    public static final String insertAuto = "INSERT INTO `park`.`auto` (`targa`, `marca`, `modello`, `parcheggiata`) VALUES (?,?,?,?)";
    public static final String loadTarga = "SELECT * FROM `park`.`auto` WHERE `targa` = ?";
    public static final String updateStatoAuto = "UPDATE `park`.`auto` SET `parcheggiata` = ? WHERE `targa` = ?";
    public static final String updateMediaOre = "UPDATE `park`.`auto` SET `media_ore` = ? WHERE `targa` = ?";
    
    //query parcheggio
    public static final String insertParcheggio = "INSERT INTO `park`.`parcheggio` (`luogo`, `indirizzo`, `num_telefono`) VALUES (?,?,?)";
    public static final String loadParcheggio = "SELECT * FROM `park`.`parcheggio` WHERE `id` = ?";
    public static final String loadParcheggi = "SELECT * FROM `park`.`parcheggio`";
    public static final String updatePostiParcheggio = "UPDATE `park`.`parcheggio` SET `posti_disponibili` = ? WHERE `id` = ?";
    public static final String updateGuadagno = "UPDATE `park`.`parcheggio` SET `tot_guadagno` = ? WHERE `id` = ?";
    public static final String updatePostiTot = "UPDATE `park`.`parcheggio` SET `posti_max` = ? WHERE `id` = ?";
    
    //query ticket
    public static final String insertTicket = "INSERT INTO `park`.`ticket` (`ingresso`, `parcheggio`, `auto`) VALUES (?,?,?)";
    public static final String updateTicket = "UPDATE `park`.`ticket` SET `uscita`= ?, `prezzo`= ? , `ore_passate` = ? WHERE  `id`= ?;";
    public static final String loadTicket = "SELECT * FROM `park`.`ticket` WHERE `id` = ?";
    public static final String loadAllTickets = "SELECT * FROM park.ticket WHERE uscita IS NOT NULL;";
    public static final String loadTicketTarga = "SELECT * FROM park.ticket WHERE auto = ? AND uscita IS NOT NULL";
    
    //query vista auto parcheggiate
    public static final String loadAutoParcheggiate = "SELECT * FROM auto_parcheggiate";
    public static final String loadAutoParcheggiateId = "SELECT * FROM auto_parcheggiate WHERE id = ?";
    
    //query vista grafico
    public static final String loadData = "SELECT * FROM vista_grafico WHERE CAST(uscita as DATE) = ?";
    public static final String loadDataMese = "SELECT * FROM vista_grafico WHERE YEAR(uscita) = ? AND MONTH(uscita) = ?";
    
    //query login
    public static final String checkLogin = "SELECT * FROM user WHERE username = ? AND password = ? ";
    public static final String crypt = "SELECT user.password FROM user WHERE username = ? ";
    public static final String loadUser = "SELECT * FROM user WHERE username = ? ";
}
