/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.security;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import it.mycompany.parcheggiospringboot.model.ParcheggioModelException;
import it.mycompany.parcheggiospringboot.model.dao.LoginInputDao;
import it.mycompany.parcheggiospringboot.model.entity.LoginInput;
import it.mycompany.parcheggiospringboot.model.entity.LoginInput.RoleType;
import java.io.IOException;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 *
 * @author Nicola
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        
        String header = request.getHeader(SecurityConfig.param);

        if (header != null && header.startsWith(SecurityConfig.prefix)) {
            try {

                DecodedJWT decoded = JwtProvider.verifyJwt(header.replace(SecurityConfig.prefix, ""));

                List<GrantedAuthority> role = new ArrayList<>();
                if (role.isEmpty()) {
                    role.add(getRole(header));
                }
                if (role.get(0).equals(RoleType.ADMIN)) {
                    role.add(RoleType.USER);
                }
                SecurityContextHolder.getContext()
                        .setAuthentication(new UsernamePasswordAuthenticationToken(decoded.getSubject(), null, role));
                chain.doFilter(request, response);
                
            } catch (TokenExpiredException ex) {
                response.setStatus(401);
            }
            

        } else if (!request.getRequestURI().endsWith("login") && !request.getMethod().equals("OPTIONS")
                && !request.getRequestURI().contains("/parcheggio/")) {
            response.setStatus(401);
        } else {
            chain.doFilter(request, response);
        }

    }

    private RoleType getRole(String jwt) {
        String[] splittedJWT = jwt.split("\\.");
        String payload = splittedJWT[1];
        Base64 b = new Base64(true);
        String body = new String(b.decode(payload));

        String[] splittedBody = body.split(",");
        String[] splitUsername = splittedBody[4].split(":");
        String username = splitUsername[1].replaceAll("[^\\w+]", "");

        System.out.println(username);
        LoginInput li = null;
        try {
            li = new LoginInputDao().loadUsername(username);
            System.out.println(li);
        } catch (ParcheggioModelException ex) {

        }
        return li.getRole();
    }
}
