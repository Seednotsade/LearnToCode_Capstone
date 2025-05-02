package com.ps;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class HelperMethods {

    static double totalPaid;
    static double totalChange;

    public static ArrayList<Product> getInventory() {
        ArrayList<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("products.csv/products.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String sku = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    String department = parts[3];
                    products.add(new Product(sku, name, price, department));
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading inventory.");
        }
        return products;
    }

    public static double getTotal(ArrayList<Product> cart) {
        double total = 0;
        for (Product product : cart) {
            total += product.getPrice() * product.getCount();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public static void removeFromCart(String sku, ArrayList<Product> cart) {
        Scanner scanner = new Scanner(System.in);
        boolean found = false;

        for (int i = 0; i < cart.size(); i++) {
            Product product = cart.get(i);
            if (product.getSku().equalsIgnoreCase(sku)) {
                if (product.getCount() > 1) {
                    System.out.print("Enter quantity to remove (out of " + product.getCount() + "): ");
                    int qtyToRemove = scanner.nextInt();
                    scanner.nextLine();
                    if (qtyToRemove >= product.getCount()) {
                        cart.remove(i);
                        System.out.println(product.getName() + " removed entirely from cart.");
                    } else {
                        product.setCount(product.getCount() - qtyToRemove);
                        System.out.println("Removed " + qtyToRemove + " of " + product.getName());
                    }
                } else {
                    cart.remove(i);
                    System.out.println(product.getName() + " removed from cart.");
                }
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Item not found in cart.");
        }
    }

    public static int verifyPayment(double userPayment, double total, ArrayList<Product> cart) {
        Scanner scanner = new Scanner(System.in);
        double change;

        while (userPayment < total) {
            double remaining = Math.round((total - userPayment) * 100.0) / 100.0;
            System.out.println("You're short by $" + remaining);
            System.out.println("[1] Add more money");
            System.out.println("[2] Cancel checkout");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 1) {
                System.out.print("Enter additional amount: ");
                double additional = scanner.nextDouble();
                scanner.nextLine();
                userPayment += additional;
            } else {
                System.out.println("Checkout cancelled.");
                return -1;
            }
        }

        change = userPayment - total;
        totalPaid = userPayment;
        totalChange = change;

        System.out.println(printReceipt(cart));
        cart.clear();
        return 1;
    }

    public static StringBuilder printReceipt(ArrayList<Product> cart) {
        StringBuilder receipt = new StringBuilder();
        String currDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String fileDate = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        receipt.append("=========== RECEIPT ===========\n");
        receipt.append("Date: ").append(currDate).append("\n\n");

        int i = 1;
        for (Product product : cart) {
            receipt.append("[").append(i++).append("] ")
                    .append(product.getName())
                    .append(" - $").append(product.getPrice())
                    .append(" x").append(product.getCount())
                    .append("\n");
        }

        double total = getTotal(cart);
        receipt.append("\nTotal: $").append(String.format("%.2f", total)).append("\n");
        receipt.append("Paid: $").append(String.format("%.2f", totalPaid)).append("\n");
        receipt.append("Change: $").append(String.format("%.2f", totalChange)).append("\n");
        receipt.append("================================\n");
        receipt.append("Thanks for shopping!\n");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Receipts/" + fileDate + ".txt"))) {
            writer.write(receipt.toString());
        } catch (Exception e) {
            System.out.println("Could not write receipt to file.");
        }

        return receipt;
    }
}
