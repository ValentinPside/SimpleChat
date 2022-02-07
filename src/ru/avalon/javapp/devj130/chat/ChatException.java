/*
 * Курс DEV-J130. Задание №4. Основы многопоточного программирования.
 */
package ru.avalon.javapp.devj130.chat;

/**
 * Класс, представляющий основное исключение для простого чата.
 *
 * @author (C)Y.D.Zakovryashin, 01.12.2020
 */
public class ChatException extends Exception {

    public ChatException() {
    }

    public ChatException(String string) {
        super(string);
    }

}
