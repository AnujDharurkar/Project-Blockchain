import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class FundsGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textFieldWalletBalance;
    private JTextField textField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    FundsGUI frame = new FundsGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public FundsGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 0,1250, 800);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("Medi");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.PLAIN, 60));
        label.setBounds(488, 54, 153, 54);
        contentPane.add(label);

        JLabel label_1 = new JLabel("Block");
        label_1.setForeground(new Color(153, 0, 51));
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 62));
        label_1.setBounds(622, 48, 200, 66);
        contentPane.add(label_1);

        JLabel logofunds = new JLabel("");
        logofunds.setIcon(new ImageIcon("C:\\Users\\ashus\\git\\Project-Blockchain2\\Images\\logo element.png"));
        logofunds.setBounds(-42, -55, 338, 260);
        contentPane.add(logofunds);

        JLabel lblFunds = new JLabel("FUNDS");
        lblFunds.setForeground(new Color(204, 0, 51));
        lblFunds.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lblFunds.setBackground(new Color(153, 0, 51));
        lblFunds.setBounds(529, 201, 348, 59);
        contentPane.add(lblFunds);

        JLabel lblYourWalletBalance = new JLabel("YOUR WALLET BALANCE:");
        lblYourWalletBalance.setForeground(Color.WHITE);
        lblYourWalletBalance.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblYourWalletBalance.setBounds(353, 350, 288, 32);
        contentPane.add(lblYourWalletBalance);

        textFieldWalletBalance = new JTextField();
        textFieldWalletBalance.setBounds(650, 357, 116, 22);
        contentPane.add(textFieldWalletBalance);
        textFieldWalletBalance.setColumns(10);

        JLabel lblEnterAmount = new JLabel("ENTER AMOUNT : ");
        lblEnterAmount.setForeground(Color.WHITE);
        lblEnterAmount.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblEnterAmount.setBounds(353, 413, 288, 32);
        contentPane.add(lblEnterAmount);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(650, 420, 116, 22);
        contentPane.add(textField);

        JButton button = new JButton("ENTER");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldWalletBalance.setText(String.valueOf(BlockChainGUI.m.b_chain.getFunds()));

                Transaction tr = new Transaction(String.valueOf((float) (Math.random() * 1000000)), BlockChainGUI.m.b_chain.ID, StaticVars.tr_to_address);
                StaticVars.amt = Float.parseFloat(textField.getText());

                if (BlockChainGUI.m.b_chain.getFunds() >= StaticVars.amt) {
                    tr.setAmount(StaticVars.amt);
                    BlockChainGUI.m.b_chain.validate_add_transaction(tr);
                    TransferData td = new TransferData(BlockChainGUI.m.b_chain.ID, tr);
                    SendData sd = new SendData(MainRun.BROADCAST_ADDRESS, 7777);
                    BlockChainGUI BlockChainGUIobj = new BlockChainGUI();
                    try {
                        sd.broadcastData(td);
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    contentPane.setVisible(true);
                    StaticVars.clrall();
                    dispose();
                }
                else{
                    System.out.println("You don't have enough funds!");

                }

            }
        });
        button.setForeground(new Color(153, 0, 51));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.setBounds(525, 504, 116, 32);
        contentPane.add(button);

        JButton btnBack = new JButton("BACK");
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BlockChainGUI BlockChainGUIobj = new BlockChainGUI();
                contentPane.setVisible(true);
                dispose();
            }
        });
        btnBack.setIcon(new ImageIcon("src/Images/logo2.png"));
        btnBack.setForeground(new Color(153, 0, 51));
        btnBack.setFocusable(false);
        btnBack.setBorderPainted(false);
        btnBack.setBackground(Color.WHITE);
        btnBack.setBounds(1068, 686, 139, 54);
        contentPane.add(btnBack);
        setVisible(true);
    }

}