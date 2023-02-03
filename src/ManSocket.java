/*
 * Copyright 2014 Tecnocoli/Jeferson Coli
 * http://www.tecnocoli.com.br 
 * All rights reserved
 */


import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JTextArea;

/**
 * @Project teste-socket-server
 * @brief Classe TesteSocket
 * @author Jeferson Coli jcoli@tecnocoli.com.br
 * @date 05/11/2014
 */
public class ManSocket {

    private static final int PORTA = 7777;
    private JTextArea jTextAreaStatus;
    private ServerSocket serverSocket;
    static final String NewLine = System.getProperty("line.separator");
    private SocketThread sc;
    private Socket socket;

    public ManSocket(JTextArea jText) {
        jTextAreaStatus = jText;

        try {
            serverSocket = new ServerSocket(PORTA);
        } catch (IOException e) {
            eventOutput(e.getMessage());
        }

    }

    public void eventOutput(String eventDescription) {
        jTextAreaStatus.append(eventDescription + NewLine);
    }

    public void waitingSocket() {

        System.out.println("waitingSocket");
        eventOutput("Waiting Connection...");

        try {
            while (true) {

                eventOutput("Waiting...");
                System.out.println("Waiting...");

                TimeUnit.SECONDS.sleep(5);

                socket = serverSocket.accept();

                eventOutput("Nova conexão " + socket.getInetAddress().getHostAddress());

                System.out.println("Nova conexão " + socket.getInetAddress().getHostAddress());
                socket.setSoTimeout(30000);

                sc = new SocketThread(this, socket.getInputStream(), socket.getOutputStream(), socket);
                new Thread(sc).start();

            }

        } catch (SocketTimeoutException e) {
            System.out.println("TimeOut");
            
            sc.interrupt();
            
            try {
                socket.close();
            } catch (IOException w) {
                eventOutput("erro" + w.getMessage());
                System.out.println("erro " + w.getMessage());
            }
        } catch (IOException e) {
            eventOutput("erro" + e.getMessage());
            System.out.println("erro " + e.getMessage());
        } catch (InterruptedException e) {
            eventOutput("erro" + e.getMessage());
            System.out.println("erro " + e.getMessage());
        }
    }

}
