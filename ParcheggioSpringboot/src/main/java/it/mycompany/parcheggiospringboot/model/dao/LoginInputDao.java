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
import it.mycompany.parcheggiospringboot.model.entity.LoginInput;
import it.mycompany.parcheggiospringboot.security.Encryption;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author Nicola
 */
public class LoginInputDao {

    private Connection dbParkConnection;

    public LoginInputDao() {
        this.dbParkConnection = (Connection) ParcheggioModelConfiguration.getConfigurationValueFromKey(ApplicationCostants.KEY_DATABASE);
    }

    public LoginInput checkLogin(LoginInput login) throws ParcheggioModelException {
        LoginInput li = null;

        try {
            PreparedStatement psCrypt = this.dbParkConnection.prepareStatement(QueryDictionary.crypt);
            PreparedStatement ps = this.dbParkConnection.prepareStatement(QueryDictionary.checkLogin);

            psCrypt.setString(1, login.getUsername());
            ResultSet rsCrypt = psCrypt.executeQuery();

            String encrypted = "";
            while (rsCrypt.next()) {
                encrypted = rsCrypt.getNString("password");
            }
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean passDec = passwordEncoder.matches(login.getPassword(), encrypted);

            if (passDec) {
                ps.setString(1, login.getUsername());
                ps.setString(2, encrypted);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    String username = rs.getNString("username");
                    String password = rs.getNString("password");
                    boolean isAdmin = rs.getBoolean("is_admin");

                    li = new LoginInput(username, password, isAdmin);
                }
            }

        } catch (SQLException ex) {
            throw new ParcheggioModelException("LoginInputDao -> checkLogin -> " + ex.getMessage());
        }

        return li;
    }

    public LoginInput loadUsername(String username) throws ParcheggioModelException {
        LoginInput li = null;

        try {
            PreparedStatement psCrypt = this.dbParkConnection.prepareStatement(QueryDictionary.loadUser);

            psCrypt.setString(1, username);
            ResultSet rs = psCrypt.executeQuery();
            
            while (rs.next()) {
                String user = rs.getNString("username");
                String password = rs.getNString("password");
                boolean isAdmin = rs.getBoolean("is_admin");

                li = new LoginInput(user, password, isAdmin);
            }

        } catch (SQLException ex) {
            throw new ParcheggioModelException("LoginInputDao -> checkLogin -> " + ex.getMessage());
        }

        return li;
    }
}
