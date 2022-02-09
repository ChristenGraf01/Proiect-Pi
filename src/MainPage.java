
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Font;
import javax.swing.table.*;

public class MainPage extends JFrame implements ActionListener{

    JButton addAccount = new JButton();
    JPanel contentPanel = new JPanel();
    JButton recurrentPayments = new JButton();
    JMenu file = new JMenu();
    JMenu edit = new JMenu();
    JMenuBar menu = new JMenuBar();
    JButton removeAccount = new JButton();
    JScrollPane scroll = new JScrollPane();
    JTable cTable = new JTable();
    int userID = 0;
    DbService database = new DbService();
    private final DefaultTableModel model;

    public MainPage(int userID){
        initComponents();
        setTitle("Easy Banking");
        model = (DefaultTableModel) cTable.getModel();
        setLocationRelativeTo(null);
        this.setVisible(true);
        this.userID = userID;
        reloadTable();
    }                        
    private void initComponents() {

        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        recurrentPayments.setFont(new Font("Tahoma", 0, 13)); 
        recurrentPayments.setText(" Recurrent Payments ");
        recurrentPayments.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                recurrentPaymentsActionPerformed(evt);
            }
        });


        removeAccount.setFont(new Font("Tahoma", 0, 13)); 
        removeAccount.setText("Remove account");
        removeAccount.setEnabled(false);
        removeAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                removeAccountActionPerformed(evt);
            }
        });


        addAccount.setFont(new Font("Tahoma", 0, 13)); 
        addAccount.setText("Add account");
        addAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addAccountActionPerformed(evt);
            }
        });

        cTable.setFont(new Font("Tahoma", 0, 13)); 
        cTable.setAutoCreateRowSorter(true);
        cTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Owner", "Balance", "Expiry Date", "Account Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        cTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        cTable.getTableHeader().setReorderingAllowed(false);
        cTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cTableMouseClicked(evt);
            }
        });
        
     
        scroll.setViewportView(cTable);

        if (cTable.getColumnModel().getColumnCount() > 0) {
            cTable.getColumnModel().getColumn(0).setResizable(false);
            cTable.getColumnModel().getColumn(1).setResizable(false);
            cTable.getColumnModel().getColumn(2).setResizable(false);
            cTable.getColumnModel().getColumn(3).setResizable(false);
        }

        file.setText("File");
        menu.add(file);

        edit.setText("Edit");
        menu.add(edit);
        
        setJMenuBar(menu);
        
        javax.swing.GroupLayout contentPanelLayout = new javax.swing.GroupLayout(contentPanel);
        contentPanel.setLayout(contentPanelLayout);
        contentPanelLayout.setHorizontalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scroll, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(contentPanelLayout.createSequentialGroup()
                        .addComponent(addAccount)
                        .addGap(18, 18, 18)
                        .addComponent(removeAccount)
                        .addGap(18, 18, 18)
                        .addComponent(recurrentPayments)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        contentPanelLayout.setVerticalGroup(
            contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(contentPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(contentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addAccount)
                    .addComponent(removeAccount)
                    .addComponent(recurrentPayments))
                .addGap(18, 18, 18)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
    private void removeAccountActionPerformed(ActionEvent e){
        int result = JOptionPane.showConfirmDialog(this, "Are you sure?", "Select an Option" , JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            int selectedRow = cTable.getSelectedRow();
            if (selectedRow >= 0) {
                Customer customer = getSelectedCustomer(selectedRow);
                if (customer != null) {
                    database.DeleteAccount(customer.getAccountId());
                    removeCustomerFromTable(selectedRow);
                }
            }
        }
    }
    private void addAccountActionPerformed(ActionEvent e){
        AddAccount add = new AddAccount(this, true, this.userID);
        add.setVisible(true);
        if (add.getCustomer() != null){
            addToTable(add.getCustomer());
        }
    }
    private void recurrentPaymentsActionPerformed(ActionEvent e){
        RecurrentPayments payment = new RecurrentPayments(this, true, userID);
        payment.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == recurrentPayments){
            recurrentPaymentsActionPerformed(e);
        }
        if(e.getSource() == removeAccount){
            removeAccountActionPerformed(e); 
        }
        if(e.getSource() == addAccount){
            addAccountActionPerformed(e);
        }
    }
    
    private void cTableMouseClicked(java.awt.event.MouseEvent evt) {
        setAccountButtonsActive(true);
        
    }
    private void setAccountButtonsActive(boolean active) {
        removeAccount.setEnabled(active);
    }

    private Customer getSelectedCustomer(int selectedRow) {
        Customer customer = null;
        if (selectedRow >= 0) {
            int accountNumber = (int) cTable.getValueAt(selectedRow, 3);
            customer = database.getAccount(this.userID, accountNumber);
        }
        return customer;
    }
    private void removeCustomerFromTable(int row) {
        model.removeRow(row);
    }
    private void addToTable(Customer customer){
        model.addRow(new Object[]{});
        reloadCustomerRowData(model.getRowCount() - 1, customer.getAccountId());
    }
    
    private void reloadCustomerRowData(int selectedRow, int accountId) {
        Customer customer = database.getAccount(this.userID, accountId);
        if(customer != null){
            model.setValueAt(customer.getName(), selectedRow, 0);
            model.setValueAt(String.format("%.2f", customer.getBalance()), selectedRow, 1);
            model.setValueAt(customer.getExpiryDate(), selectedRow, 2);
            model.setValueAt(customer.getAccountId(), selectedRow, 3);
        }
    }
    private void reloadTable() {
        DefaultTableModel model = (DefaultTableModel) cTable.getModel();
        // Deletes the rows from the highest index downwards, since deleting 
        // from index 0 would shift all remaining rows to a lower index
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i); 
        }
            for (Customer c : database.getAllAccounts(this.userID)) {
            addToTable(c);
        }
    }
    

}
