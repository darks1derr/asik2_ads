public class BankAccount {

    static int nextId = 0;

    int accountNumber;
    String name;
    double balance;

    public BankAccount(String name, double balance) {
        this.accountNumber = nextId++;
        this.name = name;
        this.balance = balance;
    }

    public void display() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Name: " + name);
        System.out.println("Balance: " + balance);
        System.out.println(" ");
    }
}