package it.mycompany.parcheggiospringboot;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class ApplicationParcheggio {

    private static final Logger LOGGER=LoggerFactory.getLogger(ApplicationParcheggio.class);

    public static void main(String[] args) {
        
        SpringApplication.run(it.mycompany.parcheggiospringboot.ApplicationParcheggio.class, args);
        
//        JdbcConnection.initJdbcConnectionProperties("libri", "root", "");       //inizializza i valori per la connessione al database (databasename, username, password)
    }

}
