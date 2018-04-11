package Main;


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.SwingUtilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author User
 */
public class Client extends javax.swing.JFrame {

private ObjectOutputStream output;
private ObjectInputStream input;
private String message ="";
private String serverIP;
private Socket connection;

public Client( String host) {
    super("Client Area");    
    initComponents();
        
    serverIP=host;
usertext.setEditable(false);
    setVisible(true);
    
    jScrollPane1.setHorizontalScrollBar(null);
    jScrollPane1.setVerticalScrollBar(jScrollPane1.createVerticalScrollBar());
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        usertext = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        display = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(41, 112, 142));

        usertext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usertextActionPerformed(evt);
            }
        });

        display.setBackground(new java.awt.Color(41, 112, 142));
        display.setColumns(20);
        display.setRows(5);
        jScrollPane1.setViewportView(display);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(usertext, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 306, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(usertext, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usertextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usertextActionPerformed
sendMessage(evt.getActionCommand());
usertext.setText("");
    }//GEN-LAST:event_usertextActionPerformed

    public void startRunning(){
    
        try {
            connectServer();
            setupStream();
            whileChatting();
        } catch (EOFException eofe) {
        showMessage("\n Client terminated connect");
        }catch(IOException ioException){
        ioException.printStackTrace();
        }
        finally{
        closeConnection();
        }
    }
    
    private void connectServer() throws IOException{
    showMessage("Attempting connection... \n");
    connection = new Socket(InetAddress.getByName(serverIP), 8377);
    showMessage("Connected to: "+connection.getInetAddress().getHostAddress() +"on port "+connection.getPort() );
    }

    private void setupStream() throws IOException{
    output= new ObjectOutputStream(connection.getOutputStream());
    output.flush();
    
    input = new ObjectInputStream(connection.getInputStream());
    showMessage("\n Streams setup complete \n");
    }
    
    private void whileChatting() throws IOException{
    ableToType(true);
    do{
    
        try {
            message = (String) input.readObject();
            showMessage("\n" +message);
        } catch (ClassNotFoundException e) {
        showMessage("\n ERROR: Invalid Message type");
        }
    }while(!message.equals("SERVER-EXIT"));
    }
                         
private  void closeConnection(){
showMessage("\n Closing connections...\n");
ableToType(false);

    try {
        output.close();
        input.close();
        connection.close();
    } catch (IOException ioException) {
    ioException.printStackTrace();
    }
}

    //send message to client
    private  void sendMessage(String message){
        try {
            output.writeObject("CLIENT - "+message);
            output.flush();
            showMessage("\n CLIENT - "+message);
        } catch (IOException ioe) {
        display.append("\n Error: Message cannot be sent");
        }
    }

   //updates message
private void showMessage(final String text){
  
    SwingUtilities.invokeLater( new Runnable() {
        @Override   
        public void run() {            
            display.append(text);
        }
    });
}

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea display;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField usertext;
    // End of variables declaration//GEN-END:variables

    private void ableToType(final boolean b) {
 SwingUtilities.invokeLater( new Runnable() {
        @Override
        public void run() {
            usertext.setEditable(b);
        }
     });
        
    }
}
