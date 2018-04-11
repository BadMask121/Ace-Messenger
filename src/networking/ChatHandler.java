/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networking;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Vector;

/**
 *
 * @author User
 * 
 */

public class ChatHandler extends Thread {
  public Socket socket;
  protected  final DataInputStream input;
  protected  final DataOutputStream output;


  public ChatHandler(Socket socket) throws IOException {
this.socket = socket;
input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
output = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
output.flush();
  }
protected static Vector  handlers = new Vector ();
  
  @Override
  public void run(){
      try {
          if(socket.isConnected()){
           System.out.println("Connected");
          }
          handlers.addElement(this);
          while(true){
          //System.out.println(Arrays.toString(socket.getLocalAddress().getAddress())); 
              
                System.out.println("read input");
              String msg = input.readUTF();
             System.out.println(msg);
              broadcaster(msg);
      
          }
          
      } catch (Exception e) {
      
      e.printStackTrace();
      }finally{
      handlers.removeElement(this);
          try {
              socket.close();
          } catch (Exception e) {
          e.printStackTrace();
          }
      }
}
  
  protected static void broadcaster(String message){
  System.out.println("chathandgiver");
      synchronized(handlers){
      
System.out.println("chathandlerr");
      Enumeration e = handlers.elements();
      while(e.hasMoreElements()){
      ChatHandler c = (ChatHandler) e.nextElement();
          try {
              synchronized(c.output){
              c.output.writeUTF(message);
System.out.println("chathandler");
              }
              c.output.flush();
          } catch (IOException ex) {
          ex.printStackTrace();
          c.stop();
          }
      }
  }
  }
  
}
