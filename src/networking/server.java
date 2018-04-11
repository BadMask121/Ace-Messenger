/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class server {
 
    public server(int port ) throws IOException{
        System.out.println("Waiting for Connection..");
    ServerSocket serverSoc = new ServerSocket(port);
    while(true){
    Socket client = serverSoc.accept();
    System.out.println("Connection Started from "+client.getInetAddress());
        try {
         
    ChatHandler Ch = new ChatHandler(client);
    Ch.start();   
    System.out.println("gone to Handler");
        } catch (Exception e) {
        e.printStackTrace();
        }
    }
    }
    
    public static void main(String[] args){
        
        try {
            server server = new server(8377);
            server.finalize();
        } catch (IOException ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Throwable ex) {
            Logger.getLogger(server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
