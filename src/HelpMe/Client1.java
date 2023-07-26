/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package HelpMe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author pipe
 */
public class Client1 extends javax.swing.JFrame {

    /**
     * Creates new form Administrator
     */
    public Client1() {
        initComponents();
        SwingUtilities.invokeLater(this::start);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        Message = new javax.swing.JTextArea();
        SendButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        MessagesList = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Client 1");
        getContentPane().setLayout(null);

        Message.setColumns(20);
        Message.setRows(5);
        jScrollPane1.setViewportView(Message);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(10, 221, 280, 60);

        SendButton.setText("Send");
        getContentPane().add(SendButton);
        SendButton.setBounds(310, 220, 80, 60);

        MessagesList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(MessagesList);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(10, 20, 380, 190);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Client1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Client1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Client1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Client1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Client1 window = new Client1();

                window.setVisible(true);
                window.setSize(400, 320);
            }
        });
    }

    private void start() {
        try {
            Socket socket = new Socket("localhost", 8888);
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            writer.println("Client1");
            writer.flush();

            System.out.println("Client1 connected. Type 'exit' to quit.");

            if (SendButton != null) {
                SendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String message = Message.getText();
                        writer.println("Client1: " + message);
                        writer.flush();
                        Message.setText("");
                    }
                });
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            SwingWorker<Void, String> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() {
                    try {
                        String message;

                        while ((message = reader.readLine()) != null) {
                            if (message.contains("Client1") || message.contains("Administrator1")) {
                                publish(message);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                @Override
                protected void process(java.util.List<String> chunks) {
                    for (String message : chunks) {
                        messages = appendWord(messages, message);
                        MessagesList.setModel(new AbstractListModel<String>() {
                            public int getSize() {
                                return messages.length;
                            }

                            public String getElementAt(int i) {
                                return messages[i];
                            }
                        });
                    }
                }
            };

            worker.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] appendWord(String[] array, String word) {
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = word;
        return newArray;
    }

    private String[] messages = new String[0];

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea Message;
    private javax.swing.JList<String> MessagesList;
    private javax.swing.JButton SendButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
