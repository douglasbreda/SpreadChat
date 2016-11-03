/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Class;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import spread.*;
/**
 *
 * @author dougl
 */
public class Connect {
    
    private SpreadConnection oConnection = new SpreadConnection();
    private SpreadGroup oGroup = new SpreadGroup();
    private String sGroupName;

    //Return the group name
    public String getsGroupName() {
        return sGroupName;
    }
    
    //Create a connection to the server with the IpAdress passed by parameter
    public String ConnectToServer(String pIpAdress, String pUserName, String pGroup){
        
        String sRetorno = "";
                
        try {
            oConnection.connect(InetAddress.getByName(pIpAdress), 4803, pUserName, true, true);
            sRetorno = "Conexão realizada com sucesso";
            ConnectToGroup(pUserName, pGroup);
        } catch (UnknownHostException | SpreadException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            sRetorno = "Ocorreu um erro ao conectar: " + ex.toString();
        }
        
        return sRetorno;
    }
    
    //Disconnect from server
    public String DisconnectToServer(){
        String sRetorno = "";
        
        try {
            oConnection.disconnect();
            sRetorno = "Usuário desconectado";
        } catch (SpreadException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            sRetorno = "Ocorreu um erro ao desconectar" + ex.toString();
        }
        
        return sRetorno;
    }
    
    //Create and connect to a new group
    public String ConnectToGroup(String pUserName, String pGroupName){
        String sRetorno = "";
        try {
            oGroup.join(oConnection, pGroupName);
            sRetorno = pUserName + " entrou no grupo " + pGroupName;
            sGroupName = pGroupName;
        } catch (SpreadException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
            sRetorno = "Ocorreu um erro ao conectar ao grupo " + pGroupName + ": " + ex.toString();
        }
        
        return sRetorno;
    }
    
    //Send a messagem to a group
    public void SendMessage(byte[] pData){
        SpreadMessage oMessagem = new SpreadMessage();
        
        oMessagem.setData(pData);
        oMessagem.addGroup(sGroupName);
        oMessagem.setReliable();
        
        try {
            oConnection.multicast(oMessagem);
        } catch (SpreadException ex) {
            Logger.getLogger(Connect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //Add a listener to receive the messages
    public void AddListenerToConnection(ListenerMessages pListener){
        oConnection.add(pListener);
    }
    
    //Removes a listener from the connection
    public void RemoverListener(ListenerMessages pListener){
        oConnection.remove(pListener);
    }
}
