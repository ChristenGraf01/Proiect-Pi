import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import java.awt.Font;


public class RecurrentPayments extends JDialog implements ActionListener {
                     
     private JButton cancelButton;
     private com.toedter.calendar.JDateChooser dateReminder;
     private JLabel dateReminderLabel;
     private JButton okButton;
     private JTextField reminderTime;
     private JLabel reminderTimeLabel;
     private String stringDate;
     private int nrTimes;
     private int m_userID;

    public RecurrentPayments(JFrame parent, boolean modal, int userID) {
        super(parent, modal);
        stringDate = null;
        nrTimes = 0;
        m_userID = userID;
        initComponents();
    }
                          
    private void initComponents() {

        dateReminderLabel = new JLabel();
        dateReminder = new com.toedter.calendar.JDateChooser();
        cancelButton = new JButton();
        okButton = new JButton();
        reminderTimeLabel = new JLabel();
        reminderTime = new JTextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        dateReminderLabel.setFont(new Font("Tahoma", 0, 13)); 
        dateReminderLabel.setText("Select a date to be reminded on:");

        cancelButton.setText("Cancel");
        cancelButton.setFont(new Font("Tahoma", 0, 13));
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelButtonActionPerformed(e);
            }
        });

        okButton.setFont(new Font("Tahoma", 0 , 13));        
        okButton.setText("Ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okButtonActionPerformed(e);
            }
        });
      

        reminderTimeLabel.setFont(new Font("Tahoma", 0, 13)); 
        reminderTimeLabel.setText("How many times do you want to be reminded?");

        reminderTime.setFont(new Font("Tahoma", 0, 13)); 

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(reminderTimeLabel)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateReminderLabel, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(86, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateReminder, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(okButton, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
                            .addComponent(reminderTime))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dateReminderLabel, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dateReminder, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(reminderTimeLabel)
                .addGap(18, 18, 18)
                .addComponent(reminderTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
                .addGap(52, 52, 52))
        );

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {}
    
    private void okButtonActionPerformed(ActionEvent e) throws NullPointerException{
         Date date = null;
         StringBuilder warnings = new StringBuilder();
         String strDate = "";
         int times = 0;
         try{
             date = dateReminder.getDate();
             strDate = DateFormat.getDateInstance().format(date);
         } catch(NullPointerException err){
             warnings.append("No date has been selected\n");
         }
         if (reminderTime.getText().isEmpty()){
             warnings.append("No duration has been specified\n");
         }
         else{
             try{   
                 times = Integer.parseInt(reminderTime.getText());
             } catch(NumberFormatException err){
                 warnings.append("A number should be provided here\n");
             }
             if (times <= 0) {
                 warnings.append("The recurring payments must repeat at least 1 time.\n");
             }
             else{
                 this.nrTimes = times;
             }
         }
         this.stringDate = strDate;
      
         if (warnings.length() > 0){
             JOptionPane.showMessageDialog(this, warnings.toString(), "Input warnings", JOptionPane.WARNING_MESSAGE);
         }
         else{
        	 this.startThread();
        	 this.dispose();            
             }
    }
    private void startThread() 
    {
  
        SwingWorker sw1 = new SwingWorker() 
        {
  
            @Override
            protected String doInBackground() throws Exception 
            {           
                new MyApp(m_userID);
                return "Astazi merg la mare";
            }
  
            @Override
            protected void process(List chunks)
            {}
  
            @Override
            protected void done() 
            {
            
                System.out.println("Inside done function");         
            }
        };
          
        sw1.execute(); 
    }
  
    private void cancelButtonActionPerformed(ActionEvent e) {
        this.dispose();
    }

    public String getDate(){
        return this.stringDate;
    }
    
    public int getNumber(){
        return this.nrTimes;
    }
}
