package moneycalculator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class MoneyCalculator {

    public static void main(String[] args) throws Exception {
      MoneyCalculator moneyCalculator = new MoneyCalculator();
      moneyCalculator.execute();       
    }

    private double amount;
    private Map<String, Currency> currencies = new HashMap<>();
    private Currency currencyFrom;
    private Currency currencyTo;
    private double exchangeRate;	    
    
    public MoneyCalculator(){
        currencies.put("USD", new Currency("USD", "Dólar americano", "$"));
        currencies.put("EUR", new Currency("EUR", "Euros", "€"));
        currencies.put("GBP", new Currency("GBP", "Libras Esterlinas", "£"));
    }
    
    private void execute() throws Exception{
        input();
        process();
        output();
    }
    
    private void process() throws Exception {
        exchangeRate = getExchangeRate(currencyFrom.getCode(), currencyTo.getCode());
    }
    
    private void output() {
        System.out.println(amount + " " + currencyFrom.getSymbol()
                            + " equivalen a " + amount*exchangeRate + " "
                            + currencyTo.getSymbol());
    }

    private void input(){
        System.out.println("Introduzca cantidad");
        Scanner scanner = new Scanner(System.in);
        amount = Double.parseDouble(scanner.next());
        
        System.out.println("Introduzca divisa origen");
        currencyFrom = currencies.get(scanner.next().toUpperCase());

        System.out.println("Introduzca divisa destino");
        currencyTo = currencies.get(scanner.next().toUpperCase());
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
}