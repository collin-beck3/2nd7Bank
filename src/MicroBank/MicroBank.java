

// this file is java for jshell

// read the input data from the file
// each line looks like: 1/10/2026, deposit, 100.00
// open file
// read file line by line
// split line into date, type, amount
// if type is deposit, add amount to balance
// if type is withdrawal, subtract amount from balance
// once you run out of transactions, print final balance

// import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class MicroBank {
    // input file is "input.data"
    double balance = 0.0;

    class Transaction {
        String date;
        String type;
        double amount;

        public Transaction(String date, String type, double amount) {
            this.date = date;
            this.type = type;
            this.amount = amount;
        }

        public double getAmount() {
            return this.amount;
        }
    }

    public static void main(String[] args) { 
        System.out.println( "Welcome to MicroBank!");
        MicroBank mb = new MicroBank();
        ArrayList<Transaction> transactions = mb.readData("input.data");
        for (Transaction t : transactions) {
            if (t.type.equals("withdrawal")) {
                mb.balance -= t.amount;
            } else if (t.type.equals("deposit")) {
                mb.balance += t.amount;
            }
        }
        System.out.println(String.format("Final Balance: $%.2f", mb.balance));

        Scanner scanner = new Scanner(System.in); 
        boolean keepGoing = true; 
        while (keepGoing) { 
            System.out.println( "\nWhat would you like to do?"); 
            System.out.println("1. Make a deposit");
            System.out.println("2. Make a withdrawal");
            System.out.println("3. Check balance");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) { 
                case "1" -> {
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = Double.parseDouble(scanner.nextLine());
                    mb.balance += depositAmount;
                    System.out.println(String.format("Deposited $%.2f", depositAmount));
                }
                case "2" -> {
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = Double.parseDouble(scanner.nextLine());
                    if (withdrawalAmount > mb.balance) {
                        System.out.println("Insufficient funds");
                    } else {
                        mb.balance -= withdrawalAmount;
                        System.out.println(String.format("Withdrew $%.2f", withdrawalAmount));
                    }
                }
                case "3" -> System.out.println(String.format("Current Balance: $%.2f", mb.balance));
                case "4" -> {
                    keepGoing = false;
                    System.out.println("Thank you for Banking with Us! Goodbye!");
                }
                default -> System.out.println("Invalid choice, please try again.");
            }
        }
        scanner.close();

    }

    ArrayList<Transaction> readData(String filename) {
        ArrayList<Transaction> list = new ArrayList<>();
        // open a text file and read each line, putting the fields into a Transaction
        // object.
        try {
            Files.lines(Paths.get(filename))
                    .forEach(line -> {
                        String[] parts = line.split(", ");
                        if (parts.length == 3) {
                            list.add(new Transaction(parts[0], parts[1], Double.parseDouble(parts[2])));
                        }
                    });
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}

