import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private Map<String, Account> accounts;
    private Scanner scanner;

    public ATM() {
        accounts = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Yeni Hesap Oluştur");
            System.out.println("2. Hesaba Giriş Yap");
            System.out.println("3. ATM'yi Kapat");
            System.out.print("Seçiminizi yapın: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Geçersiz bir seçim yaptınız. Lütfen tekrar deneyin.");
            }
        }

        scanner.close();
    }

    private void createAccount() {
        System.out.print("Yeni kullanıcı adı: ");
        String username = scanner.next();
        System.out.print("Yeni şifre: ");
        String password = scanner.next();

        if (accounts.containsKey(username)) {
            System.out.println("Bu kullanıcı adı zaten kullanılıyor. Lütfen farklı bir kullanıcı adı deneyin.");
        } else {
            Account account = new Account(username, password);
            accounts.put(username, account);
            System.out.println("Yeni hesap oluşturuldu. Giriş yapabilirsiniz.");
        }
    }

    private void login() {
        System.out.print("Kullanıcı adı: ");
        String username = scanner.next();
        System.out.print("Şifre: ");
        String password = scanner.next();

        Account account = accounts.get(username);
        if (account != null && account.getPassword().equals(password)) {
            System.out.println("Giriş başarılı. Hoş geldiniz, " + username + "!");
            performTransactions(account);
        } else {
            System.out.println("Kullanıcı adı veya şifre yanlış. Giriş başarısız.");
        }
    }

    private void performTransactions(Account account) {
        boolean exit = false;
        while (!exit) {
            System.out.println("1. Bakiye Sorgulama");
            System.out.println("2. Para Yatırma");
            System.out.println("3. Para Çekme");
            System.out.println("4. Hesaptan Çıkış Yap");
            System.out.print("Seçiminizi yapın: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Bakiyeniz: " + account.getBalance());
                    break;
                case 2:
                    System.out.print("Yatırmak istediğiniz miktarı girin: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Yatırma işlemi tamamlandı. Yeni bakiyeniz: " + account.getBalance());
                    break;
                case 3:
                    System.out.print("Çekmek istediğiniz miktarı girin: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Çekme işlemi tamamlandı. Yeni bakiyeniz: " + account.getBalance());
                    } else {
                        System.out.println("Yetersiz bakiye. Çekme işlemi başarısız.");
                    }
                    break;
                case 4:
                    exit = true;
                    break;
                default:
                    System.out.println("Geçersiz bir seçim yaptınız. Lütfen tekrar deneyin.");
            }
        }
    }

    public static void main(String[] args) {
        ATM atm = new ATM();
        atm.run();
    }
}

class Account {
    private String username;
    private String password;
    private double balance;

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            return true;
        }
        return false;
    }
}

