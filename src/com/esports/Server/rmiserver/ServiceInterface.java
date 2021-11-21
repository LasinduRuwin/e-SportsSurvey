package com.esports.Server.rmiserver;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;

/**
 * This interface contains all the abstract methods 
 * @author Lasindu Ruwin
 */
public interface ServiceInterface extends Remote{
     
    public Boolean login(String name, String Password) throws RemoteException;
    
    public String YNAnswer(JRadioButton a1, JRadioButton a2 , ButtonGroup group) throws RemoteException;
    
    public String HoursAnswer(JRadioButton a1, JRadioButton a2 , JRadioButton a3, ButtonGroup group ,int num) throws RemoteException;
    
    public String GamesAnswer(JRadioButton a1, JRadioButton a2 , JRadioButton a3 ,JRadioButton a4, JRadioButton a5, JRadioButton a6, ButtonGroup group) throws RemoteException;
    
    public String SubmitResults (String array[]) throws RemoteException;
    
    public String printGameChart() throws RemoteException;
    
    public String printStreamerChart() throws RemoteException;
    
    public String printTimeChart() throws RemoteException;
   
}
