/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.mycompany.parcheggiospringboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 *
 * @author Nicola
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
    public static String secret;
    public static String param;
    public static String prefix;
    
    @Autowired
    public SecurityConfig(Environment env){
        SecurityConfig.secret = env.getProperty("security.secret");
        SecurityConfig.param = env.getProperty("security.param");
        SecurityConfig.prefix = env.getProperty("security.prefix");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.cors().and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilter(new AuthorizationFilter(authenticationManager()))
                .authorizeRequests().antMatchers("/auth/**","auth").permitAll()
                .antMatchers("/admin-parcheggio/*").hasRole("ADMIN")
                .antMatchers("/log-parcheggio/*").hasAnyRole("USER","ADMIN")
                .antMatchers("/parcheggio/*").permitAll()
                .anyRequest().authenticated();
    }
    
//   @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/"));
//        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "OPTION", "PUT", "DELETE"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
}
