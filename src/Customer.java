import java.io.Serializable;

public class Customer implements Serializable{
    private String name;
    private double balance;
    private String expiryDate;
    private String cvv;
    private int accountID;
    Customer(String name, double balance, String cvv, String expiryDate, int accountID){
        this.name = name;
        this.balance = balance;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.accountID = accountID;
    }
    public String toString(){
        return "Name: " + name + ", Balance: " + balance + ",Cvv: " + cvv + ", ExpiryDate: " + expiryDate + ", AccountID: " + accountID;
    }
    public String getName() {
        return this.name;
    }
    public double getBalance() {
        return this.balance;
    }
    public String getExpiryDate() {
        return this.expiryDate;
    }
    public String getCvv() {
        return this.cvv;
    }
    public int getAccountId() {
        return accountID;
    }
}

