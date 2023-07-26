/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package HelpMe;

import javax.swing.*;

/**
 * @author pipe
 */
public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Administrator adminWindow = new Administrator();
            Client1 client1Window = new Client1();
            Client2 client2Window = new Client2();

            adminWindow.setVisible(true);
            adminWindow.setSize(860, 320);

            client1Window.setVisible(true);
            client1Window.setSize(400, 320);

            client2Window.setVisible(true);
            client2Window.setSize(400, 320);
        });

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                new ChatServer().start();

                return null;
            }
        };

        worker.execute();
    }
}
