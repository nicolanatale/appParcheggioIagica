package it.mycompany.parcheggiospringboot.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Imlpementazione della connessione JDBC con uso del pattern singleton.
 * (per evitare di aprire torppe connessioni verso il database, un oeprazione che richiede un certo tempo ed uso di risorse)
 * 
 * @author Nicola Natale
 */
public class JdbcConnection {

    private Connection dbConnection = null;

    private static JdbcConnection jdbcConnectionSingletonInstance=null;

    private JdbcConnection() {} // costruttore
    
    public static Connection getJdbcConnection(String _databaseName, String _username, String _password) {

        if(jdbcConnectionSingletonInstance==null) {

            jdbcConnectionSingletonInstance = new JdbcConnection();
            
            String jdbcUrl = "jdbc:mariadb://localhost:3306/" + _databaseName;

            try {
                Class.forName("org.mariadb.jdbc.Driver");
                jdbcConnectionSingletonInstance.dbConnection
                        = DriverManager.getConnection(jdbcUrl,_username,_password);

            } catch (SQLException sqlEx) {
                jdbcConnectionSingletonInstance.dbConnection = null;
            } catch (ClassNotFoundException classExcpetion) {
                jdbcConnectionSingletonInstance.dbConnection = null;
            }
        }
        
        return jdbcConnectionSingletonInstance.dbConnection;
    }

    /** Test per verfifica implementazione connection jdbc con pattern singleton: 
     * le connessioni fanno riferimento allo stesso oggetto dbConnection di tipo Connection**/
    public static void main(String[] args) {
        Connection connection1 = JdbcConnection.getJdbcConnection("park", "root", "");
        
        System.out.println(connection1);
    }
    
}
