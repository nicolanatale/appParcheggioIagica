/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.model.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Nicola
 */
public class LoginInput {
    private String username;
    private String password;
    private boolean isAdmin;
    private RoleType role;

    public enum RoleType implements GrantedAuthority {
        ADMIN(Names.ADMIN),
        USER(Names.USER);

        private final String authority;

        RoleType(String authority) {
            this.authority = authority;
        }

        @Override
        public String getAuthority() {
            return this.authority;
        }

        public class Names{
            public static final String ADMIN = "ROLE_ADMIN";
            public static final String USER = "ROLE_USER";
        }
        

    }

    public LoginInput(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        if(this.isAdmin) {
            this.role = RoleType.ADMIN;
        } else {
            this.role = RoleType.USER;
        }
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public RoleType getRole() {
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "LoginInput{" + "username=" + username + ", password=" + password + ", isAdmin=" + isAdmin + ", role=" + role + '}';
    }

    
}
