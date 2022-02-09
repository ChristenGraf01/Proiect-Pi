import javax.swing.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterPage extends JFrame implements ActionListener {
     JTextField emailField = new JTextField();
     JLabel emailLabel = new JLabel();
     JMenu fileMenu = new JMenu();
     JMenu editMenu = new JMenu();
     JMenuBar menuBar = new JMenuBar();
     JPasswordField passField = new JPasswordField();
     JLabel passLabel = new JLabel();
     JPasswordField passVerifField = new JPasswordField();
     JLabel passVerifLabel = new JLabel();
     JButton registerButton = new JButton();
     JTextField userField = new JTextField();
     JLabel userLabel = new JLabel();

    RegisterPage(){
        initComponents();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("Register");
        this.setSize(420, 420);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void initComponents() {

        userLabel.setFont(new Font("Tahoma", 0, 14));
        userLabel.setText("Insert a username");

        passLabel.setFont(new Font("Tahoma", 0, 14));
        passLabel.setText("Insert a password");

        passVerifLabel.setFont(new Font("Tahoma", 0, 14));
        passVerifLabel.setText("Insert the password again");

        emailLabel.setFont(new Font("Tahoma", 0, 14));
        emailLabel.setText("Enter your email address");

        registerButton.setFont(new Font("Tahoma", 0, 14));
        registerButton.setText("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");
        menuBar.add(fileMenu);

        editMenu.setText("Edit");
        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(userLabel)
                            .addComponent(passLabel)
                            .addComponent(emailLabel)
                            .addComponent(passVerifLabel))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(passField, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                            .addComponent(passVerifField, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                            .addComponent(emailField, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
                            .addComponent(userField, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(userLabel)
                    .addComponent(userField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel)
                    .addComponent(passField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passVerifLabel)
                    .addComponent(passVerifField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(emailLabel)
                    .addComponent(emailField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(registerButton)
                .addContainerGap(57, Short.MAX_VALUE))
        );
        this.setLayout(layout);
        this.add(userLabel);
        this.add(passLabel);
        this.add(passField);
        this.add(passVerifLabel);
        this.add(passVerifField);
        this.add(emailLabel);
        this.add(registerButton);
        pack();
    }    

    private void registerButtonActionPerformed(ActionEvent evt) {                                               
         StringBuilder warnings = new StringBuilder();
         String strDate = "";
         if (userField.getText().isEmpty()){
             warnings.append("No username has been provided\n");
         }
         if (passField.getPassword().length < 8) {
        	 warnings.append("Password must be longer than 7 characters.\n");
         }
         if (emailField.getText().isEmpty()) {
        	 warnings.append("Email must be filled in.\n");
         }
         if (warnings.length() > 0){
        	 JOptionPane.showMessageDialog(this, warnings.toString(), "Input warnings", JOptionPane.WARNING_MESSAGE);
         }
         else{
        	 DbService db = new DbService();
        	 db.addNewUser(userField.getText(), String.valueOf(passField.getPassword()), emailField.getText());
        	 this.dispose();
        	 new LoginPage();
         }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton) {
            registerButtonActionPerformed(e);
        } 
    }
}