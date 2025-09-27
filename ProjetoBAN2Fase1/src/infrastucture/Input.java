package infrastucture;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Date;

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

    public static double getDouble() {
        while (true) {
            try {
                Scanner input = new Scanner(System.in);
                if (input.hasNextDouble()) {
                    return input.nextDouble();
                } else {
                    throw new InputMismatchException();
                }
            } catch (InputMismatchException e) {
                System.out.print("Entrada inválida. Insira um número decimal (ex: 10.50): ");
            }
        }
    }

    public static double getDouble(String message) {
        System.out.print(message + " ");
        return getDouble();
    }

    public static Date getDate(String message) {
        System.out.print(message + " ");
        return getDate();
    }

    public static Date getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false); // não permite datas inválidas, ex.: 30 de fevereiro

        while (true) {
            String dateString = getString();
            try {
                var parsedDate = format.parse(dateString);
                return new Date(parsedDate.getTime()); // converte para Date da biblioteca java.sql
            } catch (ParseException e) {
                System.out.print("Formato de data inválido ou data inexistente. Insira uma data no formato YYYY-MM-DD: ");
            }
        }
    }
}
