import java.util.*;

class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountHolder) {
        this.accountHolder = accountHolder;
        this.accountNumber = UUID.randomUUID().toString();
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: Rs." + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactionHistory.add("Withdrew: Rs." + amount);
            return true;
        }
        System.out.println("Insufficient balance or invalid amount.");
        return false;
    }

    public boolean transfer(Account toAccount, double amount) {
        if (withdraw(amount)) {
            toAccount.deposit(amount);
            transactionHistory.add("Transferred: Rs." + amount + " to " + toAccount.getAccountHolder());
            toAccount.addTransaction("Received: Rs." + amount + " from " + accountHolder);
            return true;
        }
        return false;
    }

    public void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    public void displayTransactionHistory() {
        System.out.println("Transaction history for " + accountHolder + ":");
        for (String transaction : transactionHistory) {
            System.out.println(transaction);
        }
    }
}

class Bank {
    private Map<String, Account> accounts;

    public Bank() {
        accounts = new HashMap<>();
    }

    public void createAccount(String accountHolder) {
        Account newAccount = new Account(accountHolder);
        accounts.put(newAccount.getAccountNumber(), newAccount);
        System.out
                .println("Account created for " + accountHolder + ". Account Number: " + newAccount.getAccountNumber());
    }

    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }

    public void deposit(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            System.out.println("Deposited Rs." + amount + " to account " + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    public void withdraw(String accountNumber, double amount) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            System.out.println("Withdrew " + amount + " successfully!!");
        } else {
            System.out.println("Account not found.");
        }
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = getAccount(fromAccountNumber);
        Account toAccount = getAccount(toAccountNumber);
        if (fromAccount != null && toAccount != null) {
            if (fromAccount.transfer(toAccount, amount)) {
                System.out.println("Transferred Rs." + amount + " from " + fromAccount.getAccountHolder() + " to "
                        + toAccount.getAccountHolder());
            }
        } else {
            System.out.println("One or both accounts not found.");
        }
    }

    public void viewTransactionHistory(String accountNumber) {
        Account account = getAccount(accountNumber);
        if (account != null) {
            account.displayTransactionHistory();
        } else {
            System.out.println("Account not found.");
        }
    }
}

public class OnlineBankingSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        while (true) {
            System.out.println("Welcome to the Online Banking System");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. View Transaction History");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter account holder name: ");
                    String name = scanner.nextLine();
                    bank.createAccount(name);
                    break;

                case 2:
                    System.out.print("Enter account number: ");
                    String depositAccountNumber = scanner.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    bank.deposit(depositAccountNumber, depositAmount);
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    String withdrawAccountNumber = scanner.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    bank.withdraw(withdrawAccountNumber, withdrawAmount);
                    break;

                case 4:
                    System.out.print("Enter from account number: ");
                    String fromAccountNumber = scanner.nextLine();
                    System.out.print("Enter to account number: ");
                    String toAccountNumber = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    double transferAmount = scanner.nextDouble();
                    bank.transfer(fromAccountNumber, toAccountNumber, transferAmount);
                    break;

                case 5:
                    System.out.print("Enter account number to view transaction history: ");
                    String historyAccountNumber = scanner.nextLine();
                    bank.viewTransactionHistory(historyAccountNumber);
                    break;

                case 6:
                    System.out.println("Thank you for using the Online Banking System. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
