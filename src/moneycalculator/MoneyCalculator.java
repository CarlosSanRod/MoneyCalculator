package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class MoneyCalculator {
    double amount;
    double exchangerate;
    
    public static void main(String[] args) throws IOException {
        MoneyCalculator moneycalculator = new MoneyCalculator();
        moneycalculator.control();
    }

    private static double getExchangeRate(String from, String to) throws IOException {
        URL url = 
            new URL("https://free.currconv.com/api/v7/convert?q=" +
                    from + "_" + to + "&compact=ultra&apiKey=2e1689f00753f3e13de2");
        URLConnection connection = url.openConnection();
        try (BufferedReader reader = 
                new BufferedReader(
                        new InputStreamReader(connection.getInputStream()))) {
            String line = reader.readLine();
            String line1 = line.substring(line.indexOf(to)+5, line.indexOf("}"));
            return Double.parseDouble(line1);
        }
    }
    
    private void control() throws IOException {
        input();
        process();
        output();
    }

    private void input() {
        System.out.println("Introduce una cantidad de dólares: ");
        Scanner scanner = new Scanner(System.in);
        amount = scanner.nextDouble();
    }

    private void process() throws IOException {
        exchangerate = getExchangeRate("USD","EUR");
    }

    private void output() {
        System.out.println(amount + " $ = " + amount*exchangerate + " €");
    }
}