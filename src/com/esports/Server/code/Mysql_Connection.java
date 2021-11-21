package com.esports.Server.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class will establish a connection with the MYSQL database
 * @author Lasindu Ruwin
 */
public class Mysql_Connection {
    
        private static final String UN = "root";
        private static final String PW = "";
        private static final String URL = "jdbc:mysql://localhost:3306/esportssurveydb"; 
    
        public static Connection dbconn()
    {
        Connection con ;
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con= DriverManager.getConnection(URL, UN, PW);
            System.out.println("Connection Successfully Established");
            return con;
        }
        catch(ClassNotFoundException e)
        {
            System.out.println("ClassNotFoundException \n"+e);
            return null;
        }
        catch(SQLException ee)
        {
            System.out.println("SQLException \n"+ee);
            return null;
        }
        catch(NullPointerException ex){
            System.out.println("Null pointer Error \n"+ex);
            return null;
        }
    }
    
}
