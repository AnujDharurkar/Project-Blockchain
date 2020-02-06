import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class CreateTransactionGUI extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateTransactionGUI frame = new CreateTransactionGUI();
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
    public CreateTransactionGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 0,1250, 800);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(64, 64, 64));
        contentPane.setLayout(null);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        setVisible(true);
        contentPane.setLayout(null);

        JTextField Transactionid = new JTextField();
        Transactionid.setBounds(498, 183, 244, 32);
        contentPane.add(Transactionid);
        Transactionid.setColumns(10);

        JLabel lblNewLabel = new JLabel("Enter Transaction ID :");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNewLabel.setForeground(Color.WHITE);
        lblNewLabel.setBounds(260, 181, 196, 32);
        contentPane.add(lblNewLabel);

        JButton btnMedicalHistory = new JButton("Medical History");
        btnMedicalHistory.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MedicalHistoryGUI MedicalHistoryObj = new MedicalHistoryGUI();
                contentPane.setVisible(true);
                dispose();
            }
        });

        btnMedicalHistory.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnMedicalHistory.setSelected(true);
        btnMedicalHistory.setForeground(Color.WHITE);
        btnMedicalHistory.setBorderPainted(false);
        btnMedicalHistory.setBackground(new Color(153, 0, 51));
        btnMedicalHistory.setBounds(260, 351, 172, 45);
        btnMedicalHistory.setVisible(false);
        contentPane.add(btnMedicalHistory);

        JButton btnMedicalObject = new JButton("Medical Object");
        btnMedicalObject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MedicalObjectGUI MedicalObjectGUIobj = new MedicalObjectGUI();
                contentPane.setVisible(true);
                dispose();

            }
        });
        btnMedicalObject.setSelected(true);
        btnMedicalObject.setForeground(Color.WHITE);
        btnMedicalObject.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnMedicalObject.setBorderPainted(false);
        btnMedicalObject.setBackground(new Color(153, 0, 51));
        btnMedicalObject.setBounds(550, 351, 172, 45);
        btnMedicalObject.setVisible(false);
        contentPane.add(btnMedicalObject);

        JButton btnFunds = new JButton("Funds");
        btnFunds.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FundsGUI FundsGUIobj = new FundsGUI();
                contentPane.setVisible(true);
                dispose();


            }
        });
        btnFunds.setSelected(true);
        btnFunds.setForeground(Color.WHITE);
        btnFunds.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnFunds.setBorderPainted(false);
        btnFunds.setBackground(new Color(153, 0, 51));
        btnFunds.setBounds(831, 351, 172, 45);
        btnFunds.setVisible(false);
        contentPane.add(btnFunds);

        JButton btnNewButton = new JButton("Enter");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StaticVars.tr_to_address = Transactionid.getText();
                btnMedicalHistory.setVisible(true);
                btnMedicalObject.setVisible(true);
                btnFunds.setVisible(true);
            }

        });
        btnNewButton.setBorderPainted(false);
        btnNewButton.setSelected(true);
        btnNewButton.setBackground(new Color(153, 0, 51));
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBounds(831, 183, 117, 32);
        contentPane.add(btnNewButton);

        JLabel label = new JLabel("Block");
        label.setForeground(new Color(153, 0, 51));
        label.setFont(new Font("Tahoma", Font.PLAIN, 62));
        label.setBounds(622, 48, 159, 66);
        contentPane.add(label);

        JLabel label_1 = new JLabel("Medi");
        label_1.setForeground(Color.WHITE);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 60));
        label_1.setBounds(488, 54, 153, 54);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("");
        label_2.setIcon(new ImageIcon("C:\\Users\\ashus\\git\\Project-Blockchain2\\Images\\logo element.png"));
        label_2.setBounds(-42, -55, 338, 260);
        contentPane.add(label_2);


    }

}