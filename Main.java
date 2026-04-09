import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static LinkedList<BankAccount> accounts = new LinkedList<>();

    static Stack<String> history = new Stack<>();

    static Queue<String> billQueue = new LinkedList<>();

    static Queue<BankAccount> accountRequests = new LinkedList<>();

    public static void main(String[] args) {

        BankAccount[] accountsArray = new BankAccount[3];
        accountsArray[0] = new BankAccount("Ali", 150000);
        accountsArray[1] = new BankAccount("Sara", 220000);
        accountsArray[2] = new BankAccount("Nurlan", 180000);

        System.out.println("=== TASK 6: ARRAY OF ACCOUNTS ===");
        for (BankAccount acc : accountsArray) {
            acc.display();
        }

        accounts.add(new BankAccount("Ayan", 100000));
        accounts.add(new BankAccount("Dana", 200000));

        while (true) {
            System.out.println("\n=== MINI BANKING MENU ===");
            System.out.println("1 - Enter Bank");
            System.out.println("2 - Enter ATM");
            System.out.println("3 - Admin Area");
            System.out.println("4 - Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    bankMenu();
                    break;
                case 2:
                    atmMenu();
                    break;
                case 3:
                    adminMenu();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void bankMenu() {
        while (true) {
            System.out.println("\n=== BANK MENU ===");
            System.out.println("1 - Submit account opening request");
            System.out.println("2 - Deposit money");
            System.out.println("3 - Withdraw money");
            System.out.println("4 - Add bill payment request");
            System.out.println("5 - Display all accounts");
            System.out.println("6 - Search account by name");
            System.out.println("7 - Show transaction history");
            System.out.println("8 - Show last transaction");
            System.out.println("9 - Undo last transaction");
            System.out.println("0 - Back");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    submitAccountRequest();
                    break;
                case 2:
                    depositMoney();
                    break;
                case 3:
                    withdrawMoney();
                    break;
                case 4:
                    addBillPayment();
                    break;
                case 5:
                    displayAccounts();
                    break;
                case 6:
                    searchAccount();
                    break;
                case 7:
                    showHistory();
                    break;
                case 8:
                    showLastTransaction();
                    break;
                case 9:
                    undoLastTransaction();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void atmMenu() {
        while (true) {
            System.out.println("\n=== ATM MENU ===");
            System.out.println("1 - Balance enquiry");
            System.out.println("2 - Withdraw money");
            System.out.println("0 - Back");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdrawMoney();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1 - View pending account requests");
            System.out.println("2 - Process next account request");
            System.out.println("3 - View bill payment queue");
            System.out.println("4 - Process next bill payment");
            System.out.println("0 - Back");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewRequests();
                    break;
                case 2:
                    processRequest();
                    break;
                case 3:
                    displayBillQueue();
                    break;
                case 4:
                    processBillPayment();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    static void displayAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts.");
            return;
        }

        System.out.println("=== Accounts List ===");
        int i = 1;
        for (BankAccount acc : accounts) {
            System.out.println(i + ".");
            acc.display();
            i++;
        }
    }

    static void searchAccount() {
        System.out.print("Enter name to search: ");
        String name = sc.nextLine();

        BankAccount acc = findAccount(name);

        if (acc == null) {
            System.out.println("Account not found.");
        } else {
            System.out.println("Account found:");
            acc.display();
        }
    }

    static BankAccount findAccount(String name) {
        for (BankAccount acc : accounts) {
            if (acc.name.equalsIgnoreCase(name)) {
                return acc;
            }
        }
        return null;
    }

    static void depositMoney() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        BankAccount acc = findAccount(name);

        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter deposit amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        acc.balance += amount;
        history.push("Deposit " + amount + " to " + acc.name);
        System.out.println("New balance: " + acc.balance);
    }

    static void withdrawMoney() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        BankAccount acc = findAccount(name);

        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.print("Enter withdraw amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        if (amount <= 0) {
            System.out.println("Invalid amount.");
            return;
        }

        if (acc.balance < amount) {
            System.out.println("Not enough money.");
            return;
        }

        acc.balance -= amount;
        history.push("Withdraw " + amount + " from " + acc.name);
        System.out.println("New balance: " + acc.balance);
    }

    static void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }

        System.out.println("=== Transaction History ===");
        for (String item : history) {
            System.out.println(item);
        }
    }

    static void showLastTransaction() {
        if (history.isEmpty()) {
            System.out.println("No transactions.");
            return;
        }

        System.out.println("Last transaction: " + history.peek());
    }

    static void undoLastTransaction() {
        if (history.isEmpty()) {
            System.out.println("Nothing to undo.");
            return;
        }

        System.out.println("Removed: " + history.pop());
    }

    static void addBillPayment() {
        System.out.print("Enter bill name: ");
        String bill = sc.nextLine();

        billQueue.add(bill);
        history.push("Bill payment added: " + bill);
        System.out.println("Added: " + bill);
    }

    static void displayBillQueue() {
        if (billQueue.isEmpty()) {
            System.out.println("Bill queue is empty.");
            return;
        }

        System.out.println("=== Bill Queue ===");
        for (String bill : billQueue) {
            System.out.println(bill);
        }
    }

    static void processBillPayment() {
        if (billQueue.isEmpty()) {
            System.out.println("No bills to process.");
            return;
        }

        String bill = billQueue.poll();
        System.out.println("Processing: " + bill);
    }

    static void submitAccountRequest() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter initial balance: ");
        double balance = sc.nextDouble();
        sc.nextLine();

        BankAccount request = new BankAccount(name, balance);
        accountRequests.add(request);

        System.out.println("Request submitted.");
    }

    static void viewRequests() {
        if (accountRequests.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        System.out.println("=== Pending Requests ===");
        for (BankAccount acc : accountRequests) {
            acc.display();
        }
    }

    static void processRequest() {
        if (accountRequests.isEmpty()) {
            System.out.println("No requests to process.");
            return;
        }

        BankAccount acc = accountRequests.poll();
        accounts.add(acc);

        System.out.println("Request processed. Account created:");
        acc.display();
    }

    static void checkBalance() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        BankAccount acc = findAccount(name);

        if (acc == null) {
            System.out.println("Account not found.");
            return;
        }

        System.out.println("Balance: " + acc.balance);
    }
}