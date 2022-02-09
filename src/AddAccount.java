
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
public class AddAccount extends JDialog {

    private JButton addCard;
    private JButton cancel;
    private JTextField cardNumber;
    private JLabel cardNumberLabel;
    private JTextField cvv;
    private JLabel cvvLabel;
    private JTextField deposit;
    private JLabel depositLabel;
    private JTextField expiryDate;
    private JLabel expiryDateLabel;
    private JTextField ownerName;
    private JLabel ownerNameLabel;
    private int userID = 0;
    private Customer customer;

    public AddAccount(JFrame parent, boolean modal, int userID){
        super(parent, modal);
        this.setLocationRelativeTo(parent);
        this.setTitle("Add Account");
        initComponents();
        this.userID = userID;
        customer = null;
    }
    
    private void initComponents() {
    
        ownerName = new JTextField();
        cardNumber = new JTextField();
        cvv = new JTextField();
        expiryDate = new JTextField();
        addCard = new JButton();
        ownerNameLabel = new JLabel();
        depositLabel = new JLabel();
        cardNumberLabel = new JLabel();
        cvvLabel = new JLabel();
        expiryDateLabel = new JLabel();
        deposit = new JTextField();
        cancel = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ownerName.setFont(new Font("Tahoma", 0, 13)); 

        cardNumber.setFont(new Font("Tahoma", 0, 13)); 
       
        cvv.setFont(new Font("Tahoma", 0, 13)); 

        expiryDate.setFont(new Font("Tahoma", 0, 13)); 
     
        addCard.setFont(new Font("Tahoma", 0, 13)); 

        addCard.setText("Add Card");
        addCard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addCardActionPerformed(evt);
            }
        });
        ownerNameLabel.setFont(new Font("Tahoma", 0, 13)); 
        ownerNameLabel.setText("Insert the owner's name");

        depositLabel.setFont(new Font("Tahoma", 0, 13)); 
        depositLabel.setText("Initial deposit");

        cardNumberLabel.setFont(new Font("Tahoma", 0, 13)); 
        cardNumberLabel.setText("Insert the card number");

        cvvLabel.setFont(new Font("Tahoma", 0, 13)); 
        cvvLabel.setText("Insert the CVV of the card");

        expiryDateLabel.setFont(new Font("Tahoma", 0, 13)); 
        expiryDateLabel.setText("Insert the expiry date");

        deposit.setFont(new Font("Tahoma", 0, 13)); 
    
        cancel.setFont(new Font("Tahoma", 0, 13)); 
        cancel.setText("Cancel");
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cancelActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(addCard, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(cardNumberLabel)
                        .addComponent(ownerNameLabel)
                        .addComponent(cvvLabel)
                        .addComponent(expiryDateLabel)
                        .addComponent(depositLabel)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(expiryDate, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                        .addComponent(ownerName, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                        .addComponent(cardNumber)
                        .addComponent(cvv, GroupLayout.Alignment.TRAILING)
                        .addComponent(deposit, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(cancel, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(depositLabel)
                    .addComponent(deposit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(ownerName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(ownerNameLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cardNumber, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cardNumberLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(cvv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(cvvLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(expiryDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(expiryDateLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addCard)
                    .addComponent(cancel))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        pack();
    }                          

    private void addCardActionPerformed(ActionEvent evt){ 
        double balance = 0;
        StringBuilder warnings = new StringBuilder(); 
        if (ownerName.getText().isEmpty()){
            warnings.append("There has been no owner name inserted.\n");
        }
        if (deposit.getText().isEmpty()){
            warnings.append("Initial balance has not been provided.\n");
        }
        else{
            try {
                balance = round(Double.parseDouble(deposit.getText()), 2);
                if (balance < 0){
                    warnings.append("The balance inserted is negative.\n");
                }
            } catch (NumberFormatException ex) {
                warnings.append("Initial deposit must be a number.");
            }
        }
        if (cardNumber.getText().isEmpty()){
            warnings.append("No card number has been provided.\n");
        }
        else if(cardNumber.getText().length() < 16 || cardNumber.getText().length() > 16){
            warnings.append("The card number should be 16 digits long.\n");
        }
        if (cvv.getText().isEmpty()){
            warnings.append("The CVV has no been provided.\n");
        }
        else if (cvv.getText().length() > 3 || cvv.getText().length() < 3){
            warnings.append("The CVV should be 3 digits long.\n");
        }
        if (expiryDate.getText().isEmpty()){
            warnings.append("There has been no expiry date inserted.\n");
        }
        else if(expiryDate.getText().charAt(2) != '/'){
            warnings.append("Wrong date format, format should be MM/YY.\n");
        }
        if(warnings.length() > 0){
            JOptionPane.showMessageDialog(this, warnings.toString(), "Input warnings", JOptionPane.WARNING_MESSAGE);
        }
        else{
            DbService db = new DbService();
            int accountID = db.addAccount(ownerName.getText(), balance, cvv.getText(), expiryDate.getText(), this.userID);
            customer = new Customer(ownerName.getText(), balance, cvv.getText(), expiryDate.getText(), accountID);
            this.dispose();
        }
    }

    private void cancelActionPerformed(ActionEvent evt) {                                       
        this.dispose();
    }                                      
           
    Customer getCustomer(){
        return customer;
    }
    
    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
