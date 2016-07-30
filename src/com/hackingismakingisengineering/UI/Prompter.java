package com.hackingismakingisengineering.UI;

import com.sun.javafx.binding.StringFormatter;

import java.util.Scanner;

/**
 * Created by helloworld on 24/07/2016.
 */
public class Prompter {

    private static Scanner scanner = new Scanner(System.in);

    public static void print(String sf){


        System.out.printf(sf.toString());

    }


    public static String printForString(String s) {
        print(s);
        return scanner.nextLine();
    }

    public static int printForInt(String s) {
        print(s);

        int intIn = scanner.nextInt()-1;
        scanner.nextLine(); // Clears the return carriage character in the buffer (bug)

        return intIn;
    }
}
