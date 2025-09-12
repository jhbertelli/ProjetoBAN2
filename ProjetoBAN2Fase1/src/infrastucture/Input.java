package infrastucture;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Input {
    public static int getInt() {
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                return input.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Insira um número inteiro: ");
            }
        }
    }

    public static int getInt(String message) {
        System.out.print(message + " ");
        return getInt();
    }

    public static String getString() {
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    public static String getString(String message) {
        System.out.print(message + " ");
        return getString();
    }
}
