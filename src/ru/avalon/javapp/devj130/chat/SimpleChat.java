/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ru.avalon.javapp.devj130.chat;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author Valentin
 */
public class SimpleChat implements ISimpleChat{
    private Scanner scan;
    private String host;
    private int port;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Thread inputThread;
    ServerSocket ss;
    Socket socket;

    public SimpleChat(){

    }
    @Override
    public void client() throws ChatException {
        System.out.println("Client mod started!");
        System.out.println("Enter server host:");
        scan = new Scanner(System.in);
        host = scan.nextLine();
        System.out.println("Enter server port:");
        port = scan.nextInt();
        try{
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Now you can write messages\n"
                    + "If you want to close the chat write 'exit'");
        } catch (Exception e) {
            throw new ChatException();
        }
        inputThread("Server");
        outPut("Client");
    }

    @Override
    public void server() throws ChatException {
        try{
            ss = new ServerSocket(ISimpleChat.SERVER_PORT);
            System.out.println("Server mod started");
            socket = ss.accept();
            System.out.println("Client was connected");
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Now you can write messages\n"
                    + "If you want to close the chat write 'exit'");
        } catch (Exception e) {
            e.getMessage();
        }
        inputThread("Client");
        outPut("Server");
    }

    @Override
    public String getMessage() throws ChatException {
        String message = null;
        try {
            message = in.readObject().toString();
        } catch (IOException ex) {
            return "";
        } catch (ClassNotFoundException ex) {
            return "";
        }
        return message;
    }

    @Override
    public void sendMessage(String message) throws ChatException {
        try {
            out.writeObject(message);
        } catch (IOException e) {
            throw new ChatException(e.getMessage());
        }
    }

    @Override
    public void close() throws ChatException {
        inputThread.interrupt();
        System.out.println("Thread closed");
        try {
            out.close();
            in.close();
            System.out.println("Streams closed");
            socket.close();
            System.out.println("Socket closed");
            System.exit(0);

        } catch (IOException e) {
            e.getMessage();
        }

    }
    
    public void inputThread(String cOrS){
        inputThread = new Thread(() -> {
                String message = "";
                while (true){
                    try {
                        message = getMessage();
                    } catch (ChatException e) {
                        e.printStackTrace();
                    }
                    if(message.equals("exit")){
                        try {
                            close();
                        } catch (ChatException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!message.isBlank() && message != null) {
                        System.out.println(cOrS + ": " + message);
                    }
                }
            });
        inputThread.start();
    }
    
    public void outPut(String cOrS){
        while (true) {            
                String message = "";
                while (true){
                    try {
                        scan = new Scanner(System.in);
                        message = scan.nextLine();
                    if(message.equals("exit")){
                        sendMessage(message);
                        close();
                    }
                     if(!message.isBlank() && message != null){
                         sendMessage(message);
                         System.out.println(cOrS + ": " + message);
                     }
                    } catch (Exception e) {
                        e.getMessage();
                    }
                }
            }
    }
}