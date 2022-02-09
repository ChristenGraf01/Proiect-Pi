
import java.sql.*;
//import java.util.ArrayList;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
public class DbService {
    private String url = "jdbc:mysql://localhost:3306/facultate";
    private String user = "admin";
    private String password = "Descarcare2.";
    private int m_userID = -1;
    private int accountID = -1;
    private Connection connect() 
    {
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        try
        {
            connection = DriverManager.getConnection(url, user, password);
        } catch(SQLException e)
        {
            connection = null;
        }
        return connection;
    }
    DbService(){};
    DbService(int userID){
        m_userID = userID;
    }
    int addNewUser(String username, String password, String email){
        int ok = -1;
        Connection connection = connect();
        if(connection != null){
            try{
                connection.setAutoCommit(false);
                String query = "insert into useri(username, password, email) values (?, ?, ?)";
                PreparedStatement addUser = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                addUser.setString(1, username);
                addUser.setString(2, password);
                addUser.setString(3, email);
                addUser.executeUpdate();
                ResultSet addUserRes = addUser.getGeneratedKeys();
                if(addUserRes.next()){
                    ok = 0;
                }
                if(ok != 0){
                    connection.rollback();
                }
                else{
                    connection.commit();
                }
                connection.close();
            }catch(SQLException e){
                System.err.println("An error has occured: " +e.getMessage());
            }
        }
        else{
            System.err.println("Couldn't establish connection to database.");
        }
        return ok;
    }

    int verifyUser(String username, String password){
        Connection connection = connect();
        if (username.length() > 0 && password.length() > 0){
            if(connection != null){
                try{
                    connection.setAutoCommit(false);
                    String query = "SELECT * from useri where username = ? and password = ?";
                    PreparedStatement verify = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                    verify.setString(1, username);
                    verify.setString(2, password);
                    ResultSet raspuns = verify.executeQuery();
                    if(raspuns.next()){
                        m_userID = raspuns.getInt(1);
                        
                    }
                    else{
                        JOptionPane optionPane = new JOptionPane("The user doesn't exist.", JOptionPane.ERROR_MESSAGE);    
                        JDialog dialog = optionPane.createDialog("Failure");
                        dialog.setAlwaysOnTop(true);
                        dialog.setVisible(true);
                    }
                }catch(SQLException e){
                    System.err.println("An error has occured: " +e.getMessage());
                }
            }
        }
        else{
            JOptionPane optionPane = new JOptionPane("No username or password have been provided.", JOptionPane.ERROR_MESSAGE);    
            JDialog dialog = optionPane.createDialog("Failure");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
        }
        return m_userID;
    }
    int addAccount(String name, double balance, String cvv, String exp, int userID){
        Connection connection = connect();
        if (connection != null){
            try{
                connection.setAutoCommit(false);
                String query = "insert into conturi(Nume, Balanta, Cvv, Exp) values (?, ?, ?, ?)";
                PreparedStatement addAccount = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                addAccount.setString(1, name);
                addAccount.setDouble(2, balance);
                addAccount.setString(3, cvv);
                addAccount.setString(4, exp);
                addAccount.executeUpdate();
                ResultSet addAccountRes = addAccount.getGeneratedKeys();
                if(addAccountRes.next()){
                    accountID = addAccountRes.getInt(1);
                }
                if (userID > 0 && accountID > 0){
                    String linkAccount = "insert into map(UserID, AccountID) values (?, ?)";
                    PreparedStatement ps = connection.prepareStatement(linkAccount, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, userID);
                    ps.setInt(2, accountID);
                    ps.executeUpdate();
                    connection.commit();
                }
                else{
                    connection.rollback();
                }
                connection.close();
            }catch(SQLException e){
                System.err.println("An error has occured: " +e.getMessage());
            }
        }
        return accountID;
    }

    Customer getAccount(int userid, int accountID) {
        Customer customer = null;
        Connection connection = connect();
        try {
            try (PreparedStatement findUser = connection.prepareStatement(
                    "SELECT * "
                    + "FROM Conturi a JOIN Map b on a.ID = b.AccountID "
                    + "JOIN Useri c on c.iduseri = b.UserID "
                    + "WHERE c.iduseri = ? and a.ID = ?")) {
                findUser.setInt(1, userid);
                findUser.setInt(2, accountID);
                ResultSet findUserResults = findUser.executeQuery();
                if (findUserResults.next()) {
                    String nume = findUserResults.getString("Nume");
                    double balanta = findUserResults.getDouble("Balanta");
                    String cvv = findUserResults.getString("Cvv");
                    String exp = findUserResults.getString("Exp");
                    customer = new Customer(nume, balanta, cvv, exp, accountID);
                }
            }
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return customer;
    }

    boolean DeleteAccount(int userId) {
        boolean success = false;
        Connection connection = connect();
        try {
            try (PreparedStatement deleteRecords = connection.prepareStatement(
                    "DELETE Conturi FROM Conturi "
                    + "JOIN Map on Conturi.ID = Map.AccountID "
                    + "JOIN Useri on Useri.iduseri = Map.UserID "
                    + "WHERE Conturi.ID = ?")) {
                deleteRecords.setInt(1, userId);
                deleteRecords.executeUpdate();
            }
            success = true;
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return success;
    }

    ArrayList<Customer> getAllAccounts(int userID) {
        ArrayList<Customer> accounts = new ArrayList<Customer>();
        Connection connection = connect();
        try {
            try (PreparedStatement findUser = connection.prepareStatement(
                    "SELECT * "
                    + "FROM Conturi a JOIN Map b on a.ID = b.AccountId "
                    + "JOIN Useri c on b.UserID = c.iduseri "
                    + "WHERE c.iduseri = ?")) {
                    findUser.setInt(1, userID);
                ResultSet findUserResults = findUser.executeQuery();
                while (findUserResults.next()) {
                    String name = findUserResults.getString("Nume");
                    String cvv = findUserResults.getString("Cvv");
                    String exp = findUserResults.getString("Exp");
                    Double balance = findUserResults.getDouble("Balanta");
                    int accountId = findUserResults.getInt("AccountId");
                    Customer customer = new Customer(name, balance, cvv, exp, accountId);
                    accounts.add(customer);
                }
            }
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return accounts;
    }
    String getEmailAddress() {
        String email = null;
        Connection connection = connect();
        try{
            String query = "SELECT * FROM useri WHERE useri.iduseri = ?";
            PreparedStatement getMail = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getMail.setInt(1, m_userID);
            ResultSet findMail = getMail.executeQuery();
            if (findMail.next()) {
                email = findMail.getString("email");
            }
        } catch (SQLException ex) {
            System.err.println("An error has occured." + ex.getMessage());
        }
        return email;
    }
}
