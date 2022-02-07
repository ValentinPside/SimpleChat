/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ru.avalon.javapp.devj130.chat;

import java.util.Scanner;

/**
 *
 * @author Valentin
 */
public class Main {

    public static void main(String[] args) throws ChatException {
        Scanner in = new Scanner(System.in);
        String s;
        SimpleChat chat = new SimpleChat();
        while (true) {
            System.out.println("Select the operating mode: s - server mode, c - lient mode, e - exit");
            s = in.nextLine();
            if(s.equals("s")){
                chat.server();
                
            }
            else if(s.equals("c")){
                chat.client();
            }
            else if(s.equals("e")){
                System.exit(0);
            }
            else {
                System.out.println("The operating mode is not selected, try again.");
            }
        }
    }
}
