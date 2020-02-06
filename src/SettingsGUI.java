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
import java.awt.event.ActionEvent;

public class SettingsGUI extends JFrame {

    private JPanel contentPane;
    private JLabel lblBroadcastAddr;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SettingsGUI frame = new SettingsGUI();
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
    public SettingsGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 0,1250, 800);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("Block");
        label.setForeground(new Color(153, 0, 51));
        label.setFont(new Font("Tahoma", Font.PLAIN, 62));
        label.setBounds(622, 48, 200, 66);
        contentPane.add(label);

        JLabel label_1 = new JLabel("Medi");
        label_1.setForeground(Color.WHITE);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 60));
        label_1.setBounds(488, 54, 153, 54);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("");
        label_2.setIcon(new ImageIcon("src/Images/logo2.png"));
        label_2.setBounds(-42, -55, 338, 260);
        contentPane.add(label_2);

        lblBroadcastAddr = new JLabel("BROADCAST ADDR :");
        lblBroadcastAddr.setForeground(Color.WHITE);
        lblBroadcastAddr.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblBroadcastAddr.setBounds(291, 300, 196, 32);
        contentPane.add(lblBroadcastAddr);

        JLabel lblDifficulty = new JLabel("DIFFICULTY :");
        lblDifficulty.setForeground(Color.WHITE);
        lblDifficulty.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblDifficulty.setBounds(291, 362, 196, 32);
        contentPane.add(lblDifficulty);

        JLabel lblTransactionsBlock = new JLabel("TRANSACTIONS / BLOCK :");
        lblTransactionsBlock.setForeground(Color.WHITE);
        lblTransactionsBlock.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblTransactionsBlock.setBounds(291, 433, 222, 32);
        contentPane.add(lblTransactionsBlock);

        JLabel label_6 = new JLabel("BLOCKCHAIN-SETTINGS");
        label_6.setForeground(new Color(153, 0, 51));
        label_6.setFont(new Font("Tahoma", Font.PLAIN, 35));
        label_6.setBackground(new Color(153, 0, 51));
        label_6.setBounds(350, 160, 441, 59);
        contentPane.add(label_6);

        textField = new JTextField();
        textField.setColumns(10);
        textField.setBounds(571, 300, 154, 32);
        contentPane.add(textField);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(571, 369, 154, 32);
        contentPane.add(textField_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(571, 440, 154, 32);
        contentPane.add(textField_2);

        JButton button = new JButton("ENTER");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (textField.getText() != null)
                    MainRun.BROADCAST_ADDRESS = textField.getText();
                if (textField_1.getText() != null)
                    BlockChainGUI.m.b_chain.difficulty = Integer.parseInt(textField_1.getText());
                if (textField_2.getText() != null)
                    Blockchain.transactions_per_block = Integer.parseInt(textField_2.getText());


                contentPane.setVisible(true);
                dispose();
            }
        });
        button.setForeground(new Color(153, 0, 51));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.setBounds(488, 541, 116, 32);
        contentPane.add(button);

        JButton button_1 = new JButton("BACK");
        button_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        button_1.setForeground(new Color(153, 0, 51));
        button_1.setFocusable(false);
        button_1.setBorderPainted(false);
        button_1.setBackground(Color.WHITE);
        button_1.setBounds(888, 670, 139, 54);
        contentPane.add(button_1);
    }
}