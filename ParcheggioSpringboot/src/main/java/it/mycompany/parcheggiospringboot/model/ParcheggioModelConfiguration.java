package it.mycompany.parcheggiospringboot.model;



import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nicola Natale
 */
public 
class ParcheggioModelConfiguration {

    private static Map<String, Object> dbUsersKeyValue;
    
    static {
        dbUsersKeyValue = new HashMap<String, Object>();
        dbUsersKeyValue.put("dbPark", JdbcConnection.getJdbcConnection("park", "root", ""));
    }

    public static Object getConfigurationValueFromKey(String key) {
        return dbUsersKeyValue.get(key);
    }
    
}
