package com.ps;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        ArrayList<Product> inventory = HelperMethods.getInventory();
        ArrayList<Product> cart = new ArrayList<>();
        int choice;
        System.out.println("Welcome to the Online Store!");
        System.out.println("============================");

        do {
            showMainMenu();
            choice = getUserChoice();
            switch (choice) {
                case 1:
                    displayProducts(inventory);
                    break;
                case 2:
                    addToCart(inventory, cart);
                    break;
                case 3:
                    viewCart(cart);
                    break;
                case 4:
                    removeFromCart(cart);
                    break;
                case 5:
                    checkout(cart);
                    break;
                case 0:
                    System.out.println("Thanks for visiting the Online Store!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 0);
    }

    private static void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("[1] Display Products");
        System.out.println("[2] Add to Cart");
        System.out.println("[3] View Cart");
        System.out.println("[4] Remove from Cart");
        System.out.println("[5] Checkout");
        System.out.println("[0] Exit");
        System.out.print("Choose an option: ");
    }

    private static int getUserChoice() {
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private static void displayProducts(ArrayList<Product> inventory) {
        System.out.println("\nInventory:");
        System.out.println("SKU | Name | Price | Department\n--------------------------------------------------------------");
        for (Product product : inventory) {
            System.out.printf("%s | %s | $%.2f | %s%n",
                    product.getSku(), product.getName(),
                    product.getPrice(), product.getDepartment());
        }
    }

    private static void addToCart(ArrayList<Product> inventory, ArrayList<Product> cart) {
        if (scanner.hasNextLine()) scanner.nextLine();
        System.out.print("Enter the SKU of the product to add: ");
        String sku = scanner.nextLine().trim();
        Product selected = findProductBySku(inventory, sku);
        if (selected == null) {
            System.out.println("Product not found.");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty = scanner.nextInt();
        scanner.nextLine();
        Product cartItem = new Product(selected);
        cartItem.setCount(qty);
        cart.add(cartItem);
        System.out.println(cartItem.getName() + " x" + qty + " added to cart.");
    }

    private static Product findProductBySku(ArrayList<Product> inventory, String sku) {
        for (Product product : inventory) {
            if (product.getSku().equalsIgnoreCase(sku)) {
                return product;
            }
        }
        return null;
    }

    private static void viewCart(ArrayList<Product> cart) {
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("\nCart Items:");
            for (Product product : cart) {
                System.out.printf("%s | %s | $%.2f | Qty: %d%n",
                        product.getSku(), product.getName(),
                        product.getPrice(), product.getCount());
            }
            System.out.printf("Total: $%.2f%n", HelperMethods.getTotal(cart));
        }
    }

    private static void removeFromCart(ArrayList<Product> cart) {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.print("Enter SKU of item to remove: ");
        String removeSku = scanner.nextLine().trim();
        HelperMethods.removeFromCart(removeSku, cart);
    }

    private static void checkout(ArrayList<Product> cart) {
        if (cart.isEmpty()) {
            System.out.println("Nothing to checkout. Cart is empty.");
            return;
        }
        double total = HelperMethods.getTotal(cart);
        System.out.printf("Your total is $%.2f%n", total);
        System.out.print("Enter payment amount: ");
        double payment = scanner.nextDouble();
        scanner.nextLine();
        HelperMethods.verifyPayment(payment, total, cart);
    }
}
