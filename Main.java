import java.util.*;
import java.io.*;

class Stock {
    private String symbol;
    private double price;

    public Stock(String symbol, double price) {
        this.symbol = symbol;
        this.price = price;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }
}

class Portfolio {
    private HashMap<String, Integer> holdings = new HashMap<>();
    private double cash = 100000;

    public double getCash() {
        return cash;
    }

    public HashMap<String, Integer> getHoldings() {
        return holdings;
    }

    public void buyStock(Stock stock, int quantity) {
        double total = stock.getPrice() * quantity;

        if (total > cash) {
            System.out.println("Not enough balance.");
            return;
        }

        cash -= total;
        holdings.put(stock.getSymbol(),
                holdings.getOrDefault(stock.getSymbol(), 0) + quantity);

        System.out.println("Stock Purchased Successfully.");
    }

    public void sellStock(Stock stock, int quantity) {

        int owned = holdings.getOrDefault(stock.getSymbol(), 0);

        if (quantity > owned) {
            System.out.println("You don't own enough shares.");
            return;
        }

        holdings.put(stock.getSymbol(), owned - quantity);

        cash += stock.getPrice() * quantity;

        System.out.println("Stock Sold Successfully.");
    }

    public void showPortfolio(Map<String, Stock> market) {

        System.out.println("\n========== PORTFOLIO ==========");

        double totalValue = cash;

        System.out.println("Available Cash : ₹" + cash);

        for (String symbol : holdings.keySet()) {

            int qty = holdings.get(symbol);

            if (qty > 0) {

                double value = qty * market.get(symbol).getPrice();

                totalValue += value;

                System.out.println(symbol +
                        " | Quantity : " + qty +
                        " | Value : ₹" + value);
            }
        }

        System.out.println("------------------------------");
        System.out.println("Total Portfolio Value : ₹" + totalValue);
    }
}

class User {

    private String name;
    private Portfolio portfolio;

    public User(String name) {
        this.name = name;
        portfolio = new Portfolio();
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }
}

class Market {

    private HashMap<String, Stock> stocks = new HashMap<>();

    public Market() {

        stocks.put("TCS", new Stock("TCS", 3500));
        stocks.put("INFY", new Stock("INFY", 1500));
        stocks.put("RELIANCE", new Stock("RELIANCE", 2800));
        stocks.put("HDFC", new Stock("HDFC", 1700));
        stocks.put("WIPRO", new Stock("WIPRO", 500));
    }

    public void displayMarket() {

        System.out.println("\n========== MARKET DATA ==========");

        for (Stock stock : stocks.values()) {

            System.out.println(stock.getSymbol() + " : ₹" + stock.getPrice());

        }
    }

    public Stock getStock(String symbol) {
        return stocks.get(symbol);
    }

    public Map<String, Stock> getStocks() {
        return stocks;
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Market market = new Market();

        User user = new User("Investor");

        while (true) {

            System.out.println("\n=================================");
            System.out.println("     STOCK TRADING PLATFORM");
            System.out.println("=================================");
            System.out.println("1. View Market Data");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save Portfolio");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    market.displayMarket();

                    break;

                case 2:

                    market.displayMarket();

                    System.out.print("Enter Stock Symbol: ");

                    String buySymbol = sc.next().toUpperCase();

                    Stock buyStock = market.getStock(buySymbol);

                    if (buyStock == null) {

                        System.out.println("Invalid Stock Symbol.");

                        break;
                    }

                    System.out.print("Enter Quantity: ");

                    int buyQty = sc.nextInt();

                    user.getPortfolio().buyStock(buyStock, buyQty);

                    break;

                case 3:

                    market.displayMarket();

                    System.out.print("Enter Stock Symbol: ");

                    String sellSymbol = sc.next().toUpperCase();

                    Stock sellStock = market.getStock(sellSymbol);

                    if (sellStock == null) {

                        System.out.println("Invalid Stock Symbol.");

                        break;
                    }

                    System.out.print("Enter Quantity: ");

                    int sellQty = sc.nextInt();

                    user.getPortfolio().sellStock(sellStock, sellQty);

                    break;

                case 4:

                    user.getPortfolio().showPortfolio(market.getStocks());

                    break;

                case 5:

                    savePortfolio(user);

                    break;

                case 6:

                    System.out.println("Thank You for Using Stock Trading Platform!");

                    sc.close();

                    return;

                default:

                    System.out.println("Invalid Choice.");
            }
        }
    }

    public static void savePortfolio(User user) {

        try {

            FileWriter fw = new FileWriter("portfolio.txt");

            fw.write("Cash Balance : ₹" + user.getPortfolio().getCash() + "\n");

            fw.write("Stock Holdings\n");

            for (String stock : user.getPortfolio().getHoldings().keySet()) {

                fw.write(stock + " : "
                        + user.getPortfolio().getHoldings().get(stock) + "\n");

            }

            fw.close();

            System.out.println("Portfolio saved successfully in portfolio.txt");

        } catch (IOException e) {

            System.out.println("Error saving portfolio.");

        }
    }
}