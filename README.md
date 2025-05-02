🛍️ Syed's Java Store (CLI)

📋 What This Project Does
This is a simple command-line store made with Java. You can:
-See a list of products
-Add products to a cart
-View your cart
-Remove items
-Pay and get a receipt

🖼️ Screenshots

Main Menu

![image](https://github.com/user-attachments/assets/0adbba67-ec62-491b-846e-37eeea1f40fe)


Product List

![image](https://github.com/user-attachments/assets/0f6911f0-1243-481d-b8e7-0b430f2c69b1)


Adding to Cart

![image](https://github.com/user-attachments/assets/1e68aeb4-8fdd-459f-b91d-7ad2d69573ec)


Cart View

![image](https://github.com/user-attachments/assets/2ef225aa-0717-4c02-986b-011aca9aba26)


Checkout

![image](https://github.com/user-attachments/assets/1364a4ca-446d-4838-ba9f-2f5d188c1c8b)


🧠 Interesting Code

double total = getTotal(cart);
        receipt.append("\nTotal: $").append(String.format("%.2f", total)).append("\n");
        receipt.append("Paid: $").append(String.format("%.2f", totalPaid)).append("\n");
        receipt.append("Change: $").append(String.format("%.2f", totalChange)).append("\n");
        receipt.append("================================\n");
        receipt.append("Thanks for shopping!\n");
