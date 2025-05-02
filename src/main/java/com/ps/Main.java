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
            System.out.println("\nMain Menu:");
            System.out.println("[1] Display Products");
            System.out.println("[2] Add to Cart");
            System.out.println("[3] View Cart");
            System.out.println("[4] Remove from Cart");
            System.out.println("[5] Checkout");
            System.out.println("[0] Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nInventory:");
                    System.out.println("SKU | Name | Price | Department");
                    for (Product product : inventory) {
                        System.out.printf("%s | %s | $%.2f | %s\n",
                                product.getSku(), product.getName(),
                                product.getPrice(), product.getDepartment());
                    }
                    break;

                case 2:
                    System.out.print("Enter the SKU of the product to add: ");
                    String sku = scanner.nextLine();
                    Product selected = null;
                    for (Product p : inventory) {
                        if (p.getSku().equalsIgnoreCase(sku)) {
                            selected = new Product(p); // clone to avoid shared reference
                            break;
                        }
                    }
                    if (selected == null) {
                        System.out.println("Product not found.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();

                    selected.setCount(qty);
                    cart.add(selected);
                    System.out.println(selected.getName() + " x" + qty + " added to cart.");
                    break;

                case 3:
                    if (cart.isEmpty()) {
                        System.out.println("Your cart is empty.");
                    } else {
                        System.out.println("\nCart Items:");
                        for (Product product : cart) {
                            System.out.printf("%s | %s | $%.2f | Qty: %d\n",
                                    product.getSku(), product.getName(),
                                    product.getPrice(), product.getCount());
                        }
                        System.out.printf("Total: $%.2f\n", HelperMethods.getTotal(cart));
                    }
                    break;

                case 4:
                    if (cart.isEmpty()) {
                        System.out.println("Cart is empty.");
                        break;
                    }
                    System.out.print("Enter SKU of item to remove: ");
                    String removeSku = scanner.nextLine();
                    HelperMethods.removeFromCart(removeSku, cart);
                    break;

                case 5:
                    if (cart.isEmpty()) {
                        System.out.println("Nothing to checkout. Cart is empty.");
                        break;
                    }
                    double total = HelperMethods.getTotal(cart);
                    System.out.printf("Your total is $%.2f\n", total);
                    System.out.print("Enter payment amount: ");
                    double payment = scanner.nextDouble();
                    scanner.nextLine();
                    HelperMethods.verifyPayment(payment, total, cart);
                    break;

                case 0:
                    System.out.println("Thanks for visiting the Online Store!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }

        } while (choice != 0);
    }
}
