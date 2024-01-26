
import java.util.*;
class Transaction {
    private String type;
    private double amount;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }
 public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}

class BankAccount {
    private double balance;
    private ArrayList<Transaction> transactionHistory;
    public BankAccount() {
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }
    public double getBalance() {
        return balance;
    }
    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add(new Transaction("Deposit", amount));
    }
    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            transactionHistory.add(new Transaction("Withdraw", amount));
            return true;
        } else {
            System.out.println("Insufficient balance!");
            return false;
        }
    }
    public void transfer(double amount, BankAccount destinationAccount) {
        if (withdraw(amount)) {
            destinationAccount.deposit(amount);
            transactionHistory.add(new Transaction("Transfer to " + destinationAccount.hashCode(), amount));
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Transfer failed. Insufficient balance!");
        }
    }

    public void printTransactionHistory() {
        System.out.println("Transaction History:");
        for (Transaction transaction : transactionHistory) {
            System.out.println(transaction.getType() + ": $" + transaction.getAmount());
        }
    }
}

class User {
    private String UserId;
    private String pin;
    private BankAccount bankAccount;

    public User(String UserId, String pin) {
        this.UserId = UserId;
        this.pin = pin;
        this.bankAccount = new BankAccount();
    }

    public String getUserId() {
        return UserId;
    }

    public String getPin() {
        return pin;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }
}

class ATMSystem {
    private Map<String, User> users;
    private User currentUser;

    public ATMSystem() {
        this.users = new HashMap<>();
        users.put("bhargav019", new User("bhargav019", "1994"));
        users.put("vijay099", new User("vijay099", "8298"));
    }

    public boolean authenticateUser(String UserId, String pin) {
        User user = users.get(UserId);
        if (user != null && user.getPin().equals(pin)) {
            currentUser = user;
            return true;
        } else {
            System.out.println("Invalid User id or pin. Please try again.");
            return false;
        }
    }

    public void performTransaction(int choice, double amount, String destinationUserId) {
        switch (choice) {
            case 1:
                currentUser.getBankAccount().printTransactionHistory();
                break;
            case 2:
                System.out.print("Enter withdrawal amount: $");
                if (currentUser.getBankAccount().withdraw(amount)) {
                    System.out.println("Withdrawal successful!");
                }
                break;
            case 3:
                System.out.print("Enter deposit amount: $");
                currentUser.getBankAccount().deposit(amount);
                System.out.println("Deposit successful!");
                break;
            case 4:
                System.out.print("Enter transfer amount: $");
                User destinationUser = users.get(destinationUserId);
                if (destinationUser != null) {
                    currentUser.getBankAccount().transfer(amount, destinationUser.getBankAccount());
                } else {
                    System.out.println("Destination user not found.");
                }
                break;
            case 5:
                System.out.println("Exiting ATM. Thank you!");
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}

class AtmInterface {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATMSystem atmSystem = new ATMSystem();

        System.out.print("Enter user id: ");
        String userId = scanner.nextLine();
        System.out.print("Enter pin: ");
        String pin = scanner.nextLine();

        if (atmSystem.authenticateUser(userId, pin)) {
            int choice;
            do {
                System.out.println("\nATM Menu:");
                System.out.println("1. Transaction History");
                System.out.println("2. Withdraw");
                System.out.println("3. Deposit");
                System.out.println("4. Transfer");
                System.out.println("5. Quit");
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();

                if (choice == 4) {
                    System.out.print("Enter destination user id: ");
                    String destinationUserId = scanner.next();
                    atmSystem.performTransaction(choice, 0, destinationUserId);
                } else {
                    System.out.print("Enter amount: $");
                    double amount = scanner.nextDouble();
                    atmSystem.performTransaction(choice, amount, null);
                }
            } while (choice != 5);
        }
    }
}