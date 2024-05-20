/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.control;

import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.dao.LoginInputDao;
import it.mycompany.parcheggiospringboot.model.entity.LoginInput;
import it.mycompany.parcheggiospringboot.model.entity.LoginOutput;
import it.mycompany.parcheggiospringboot.security.JwtProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Nicola
 */
@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthRestController {
    private LoginInputDao loginInputDao;

    public AuthRestController() {
        this.loginInputDao = new LoginInputDao();
    }
    
    @PostMapping(path = "/login")
    public ResponseEntity<LoginOutput> login(@RequestBody LoginInput body){
        LoginInput li = null;
        
        try {
            li = this.loginInputDao.checkLogin(body);
            if(li == null)
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (ParcheggioModelException ex) {
            
        }
        
        Map claimMap = new HashMap<>();
        claimMap.put("user", li.getUsername());
        claimMap.put("authorities",li.getRole());
        
        
        LoginOutput tmp = new LoginOutput();
        String jwt = JwtProvider.createJwt(li.getUsername(),claimMap);
        tmp.setToken(jwt);
        tmp.setUsername(li.getUsername());
        tmp.setRole(li.getRole());
        
        return ResponseEntity.ok(tmp);
    }
}
