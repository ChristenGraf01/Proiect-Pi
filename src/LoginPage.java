import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Font;
import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener {
    JLabel userLabel = new JLabel();
    JTextField userField = new JTextField();
    JLabel passLabel = new JLabel();
    JPasswordField passField = new JPasswordField();
    JCheckBox showPassword = new JCheckBox();
    JButton loginButton = new JButton();
    JButton registerButton = new JButton();
    JMenu edit = new JMenu();
    JMenu file = new JMenu();
    JMenuBar meniu = new JMenuBar();
    int userID = 0;
    LoginPage(){
        initComponents();
        initialize();
    }
    public void initialize() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setTitle("Login");
        this.setSize(420,420);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        userLabel.setFont(new Font("Tahoma", 0, 14)); 
        userLabel.setText("Username");

        passLabel.setFont(new Font("Tahoma", 0, 14)); 
        passLabel.setText("Password");

        loginButton.setFont(new Font("Tahoma", 0, 14)); 
        loginButton.setText("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt){
                loginButtonActionPerformed(evt);
            }
        });

        registerButton.setFont(new Font("Tahoma", 0, 14)); 
        registerButton.setText("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                registerButtonActionPerformed(evt);
            }
        });

        file.setText("File");
        meniu.add(file);

        edit.setText("Edit");
        meniu.add(edit);

        setJMenuBar(meniu);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loginButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(passLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(userLabel, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(passField, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(userField))))
                .addContainerGap(115, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                    .addComponent(userField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(passLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
                    .addComponent(passField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(loginButton)
                    .addComponent(registerButton))
                .addContainerGap(88, Short.MAX_VALUE))
        );
        this.add(userField);
        this.add(userLabel);
        this.add(passLabel);
        this.add(passField);
        this.add(showPassword);
        this.add(loginButton);
        this.add(registerButton);
        pack();
    }                      

    public void loginButtonActionPerformed(ActionEvent e) {
        DbService db = new DbService();
        String parola = String.valueOf(passField.getPassword());
        int userID = db.verifyUser(userField.getText(), parola);
        if (userID > 0){
            this.dispose();
            new MainPage(userID);
        }
       
    }
    public void registerButtonActionPerformed(ActionEvent e) {
        this.dispose();
        new RegisterPage();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == registerButton){
            registerButtonActionPerformed(e);
        }
        if(e.getSource() == loginButton){
            loginButtonActionPerformed(e);
        }
    }
}
