package ui;

import java.util.Scanner;

// Calls Menu to start program
public class Main {
    public static void main(String[] args) {
        // new MenuUI();
        System.out.println("Which interface would you like to use?\n1. Text\n2. GUI");
        int command;
        while (true) {
            command = intInput();
            if (command == 1) {
                new MenuText();
                break;
            } else if (command == 2) {
                new MenuUI();
                break;
            } else {
                System.out.println("Please enter 1 or 2");
            }
        }
    }

    // EFFECTS: checks to make sure user input is a positive integer
    public static int intInput() {
        String in;
        int inTry;
        Scanner input = new Scanner(System.in);
        while (true) {
            in = input.nextLine();
            try {
                inTry = Integer.parseInt(in);
                if (inTry > 0) {
                    return inTry;
                } else {
                    System.out.println("Please enter a positive number");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a positive number");
            }
        }
    }
}
