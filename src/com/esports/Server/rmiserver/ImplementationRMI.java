package com.esports.Server.rmiserver;

import com.esports.Server.code.QuickCharts;
import com.esports.Server.code.Mysql_Connection;
import java.awt.HeadlessException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 * This class contains the methods bodies in the ServiceInterface. 
 * @author Lasindu Ruwin
 */
public class ImplementationRMI extends UnicastRemoteObject implements ServiceInterface{
    
    protected ImplementationRMI() throws RemoteException 
    {
	super();
    }
    
    //Creating an instance from QuickCharts class
    QuickCharts chart = new QuickCharts();
    //Creating a connection with the mysql database server
    Connection conn = Mysql_Connection.dbconn();

    /**
     * Login function:
     * takes user name and password as a parameter and checks it with the database
     * and returns a boolean result
     * @param name user name as a String
     * @param Password Password of the user as a String
     * @return results Result as a Boolean 
     * @throws RemoteException 
     */
    @Override
    public Boolean login(String name, String Password) throws RemoteException {
        
        Boolean login = false;
        String sql = "SELECT * FROM `admin` WHERE `UserName`='"+name+"' AND `Password`='"+Password+"'";
        PreparedStatement ps;
        ResultSet rs ;
            try {
                
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();
                
                if(rs.next())
                {
                   login = true;
                }
                
            } catch (SQLException ex) {
                System.out.println("SQL Exception "+ex);
            }catch (HeadlessException e) {
                System.out.println("HeadlessException "+e);
            }
            return login;
    }

    /**
     * Method for getting input from 2 choice answers from radio buttons
     * takes 2 JRadioButton objects and a ButtonGroup object as parameters
     * @param a1 Radio Button 1
     * @param a2 Radio Button 2
     * @param group Button Group to get the selected radio button
     * @return Selected answer as a String
     * @throws RemoteException 
     */
    @Override
    public String YNAnswer(JRadioButton a1, JRadioButton a2, ButtonGroup group) throws RemoteException {
        String res = null;
        a1.setActionCommand("Yes");
        a2.setActionCommand("No");
        if(a1.isSelected() || a2.isSelected())
        {
            res = group.getSelection().getActionCommand();
            System.out.println("Answer is ---> "+res);
        }
        return res;
    }

    /**
     * Method for getting input from questions with 3 choice answers from radio buttons
     * @param a1 Radio Button 1
     * @param a2 Radio Button 2
     * @param a3 Radio Button 3
     * @param group Button Group to get the selected radio button
     * @param num Indicates the requested ActionCommand set
     * @return Returns the action command of the selected radio button
     * @throws RemoteException 
     */
    @Override
    public String HoursAnswer(JRadioButton a1, JRadioButton a2, JRadioButton a3 , ButtonGroup group , int num) throws RemoteException {
        if(num == 1)
        {
            a1.setActionCommand("<2hrs");
            a2.setActionCommand("3-5hrs");
            a3.setActionCommand("5hrs<");
        }
        else
        {
            a1.setActionCommand("Slow");
            a2.setActionCommand("Avg");
            a3.setActionCommand("Fast");
        }
        String res = null;
        if(a1.isSelected() || a2.isSelected() || a3.isSelected())
        {
            res = group.getSelection().getActionCommand();
            System.out.println("Answer is ---> "+res);
        }
        return res;
    }

    /**
     * Method for getting input from the game selection
     * @param a1  Radio button 1
     * @param a2 Radio button 2
     * @param a3 Radio button 3
     * @param a4 Radio button 4
     * @param a5 Radio button 5
     * @param a6 Radio button 6
     * @param group Button Group to get the selected radio button
     * @return Selected answers action command as a String
     * @throws RemoteException 
     */
    @Override
    public String GamesAnswer(JRadioButton a1, JRadioButton a2, JRadioButton a3, JRadioButton a4, JRadioButton a5, JRadioButton a6, ButtonGroup group) throws RemoteException {
        
        String res = null;
        a1.setActionCommand("Valorant");
        a2.setActionCommand("CSGO");
        a3.setActionCommand("COD4");
        a4.setActionCommand("RainbowSix");
        a5.setActionCommand("MKX");
        a6.setActionCommand("Dota");
        if(a1.isSelected() || a2.isSelected() || a3.isSelected() || a4.isSelected() || a5.isSelected() || a6.isSelected())
        {
            res = group.getSelection().getActionCommand();
            System.out.println("Answer is ---> "+res);
        }
        return res;
    }

    /**
     * This method will take an array and insert it into the MYSQL database 
     * @param array A String array containing all the answers provided by the user to the questions
     * @return Success Message 
     * @throws RemoteException 
     */
    @Override
    public String SubmitResults(String[] array) throws RemoteException {
        String res;
        String sql = "INSERT INTO `answertable`(`id`, `Answer1`, `Answer2`, `Answer3`, `Answer4`, `Answer5`, `Answer6`, `Answer7`) VALUES (null,?,?,?,?,?,?,?)";
        PreparedStatement ps;
                
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1, array[0]);
            ps.setString(2, array[1]);
            ps.setString(3, array[2]);
            ps.setString(4, array[3]);
            ps.setString(5, array[4]);
            ps.setString(6, array[5]);
            ps.setString(7, array[6]);
            ps.execute();
            res= "Results Submited Successfully ...!" ;
            
        } catch (SQLException ex) {
            System.out.println("Exception --> "+ex);
            res = " SQL Exception";
        }
        
        return res;
        
    }

    /**
     * 
     * Calls data from database and sends them to the quick chart methods via 2D String a array
     * After generating the link this method will return the short URL for the chart
     * @return URL of the chart
     * @throws RemoteException 
     */
    @Override
    public String printGameChart() throws RemoteException { 
        PreparedStatement ps;
        ResultSet rs;
        String results [][] = new String[6][2];
        int i =0;
        //sql querry for counting and printing the votes for teh games as a presentage
        String sql ="SELECT `Answer4`,ROUND(100*COUNT(`Answer4`)/(SELECT COUNT(*) FROM `answertable`),0)AS Precentage\n" +
                    "FROM `answertable`\n" +
                    "GROUP BY `Answer4`\n" +
                    "ORDER BY `Answer4`";
        String url = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                results[i][0] = rs.getString("Answer4");
                results[i][1] = rs.getString("Precentage");
                
                i++;
            }
           
        } catch (SQLException ex) {
            System.out.println("DataAnalysis class StreamerChart method\n  "+ex);
        }
        url = chart.getGameChart(results,"gamecchart");
        
        return url;
    }

    /**
     * Outputs a Doughnut Chart about Streamers 
     * @return URL of the chart
     * @throws RemoteException 
     */
    @Override
    public String printStreamerChart() throws RemoteException {        
        PreparedStatement ps;
        ResultSet rs;
        String results [][] = new String[2][2];
        int i =0;
        String sql ="SELECT `Answer3`, ROUND(100*COUNT(`Answer3`)/(SELECT COUNT(*) FROM `answertable`),0) AS Precentage \n" +
"                        FROM `answertable`\n" +
"                        GROUP BY `Answer3`\n" +
"                        ORDER BY `Answer3`";
        String url = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                results[i][0] = rs.getString("Answer3");
                results[i][1] = rs.getString("Precentage");
                i++;
            }
               
        } catch (SQLException ex) {
            System.out.println("DataAnalysis class StreamerChart method\n  "+ex);
        }
        url = chart.getGameChart(results,"Stremer");
                
        return url;
    }

    /**
     * Outputs a bar chart about average play time for specific video games
     * @return URL of the chart
     * @throws RemoteException 
     */
    @Override
    public String printTimeChart() throws RemoteException {
        PreparedStatement ps;
        ResultSet rs;
        String results [][] = new String[6][4];
        int i =0;
        // Calculates the number of selections for 5th question for each answer in 4th question 
        String sql ="SELECT `Answer4`,\n" +
                    "       SUM(CASE WHEN `Answer5` = '5hrs<' THEN 1 ELSE 0 END) as greaterthenFive,\n" +
                    "       SUM(CASE WHEN `Answer5` = '3-5hrs' THEN 1 ELSE 0 END) as ThreetoFive,\n" +
                    "       SUM(CASE WHEN `Answer5` = '<2hrs' THEN 1 ELSE 0 END) as LessthanTwo\n" +
                    "FROM `answertable`\n" +
                    "GROUP BY `Answer4`\n" +
                    "ORDER BY `Answer4` ASC;";
        String url = null;
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next())
            {
                results[i][0] = rs.getString("Answer4");
                results[i][1] = rs.getString("greaterthenFive");
                results[i][2] = rs.getString("ThreetoFive");
                results[i][3] = rs.getString("LessthanTwo");
                i++;
            }    
        } catch (SQLException ex) {
            System.out.println("DataAnalysis class StreamerChart method\n  "+ex);
        }
        url = chart.getGameChart(results,"Agv gametime");  
        return url;
     
    }


    
}
