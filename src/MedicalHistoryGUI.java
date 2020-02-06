import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class MedicalHistoryGUI extends JFrame {

    private JPanel contentPane;
    private JTextField textField_5;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MedicalHistoryGUI frame = new MedicalHistoryGUI();
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
    public MedicalHistoryGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 0,1250, 800);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setVisible(true);

        JLabel label = new JLabel("Name:");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        label.setBounds(347, 263, 196, 32);
        contentPane.add(label);

        JLabel lblAddress = new JLabel("Address:");
        lblAddress.setForeground(Color.WHITE);
        lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblAddress.setBounds(347, 323, 196, 32);
        contentPane.add(lblAddress);

        JLabel lblBloodgroup = new JLabel("BloodGroup:");
        lblBloodgroup.setForeground(Color.WHITE);
        lblBloodgroup.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblBloodgroup.setBounds(347, 379, 196, 32);
        contentPane.add(lblBloodgroup);

        JLabel lblAge = new JLabel("Age:");
        lblAge.setForeground(Color.WHITE);
        lblAge.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblAge.setBounds(347, 439, 196, 32);
        contentPane.add(lblAge);

        JLabel lblDiseases = new JLabel("Diseases:");
        lblDiseases.setForeground(Color.WHITE);
        lblDiseases.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblDiseases.setBounds(347, 510, 196, 32);
        contentPane.add(lblDiseases);

        JLabel lblMedicalHistory = new JLabel("MEDICAL HISTORY");
        lblMedicalHistory.setBackground(new Color(153, 0, 51));
        lblMedicalHistory.setForeground(new Color(153, 0, 51));
        lblMedicalHistory.setFont(new Font("Tahoma", Font.PLAIN, 35));
        lblMedicalHistory.setBounds(427, 160, 348, 59);
        contentPane.add(lblMedicalHistory);

        JTextField txtTextfieldname = new JTextField();
        txtTextfieldname.setColumns(10);
        txtTextfieldname.setBounds(621, 265, 154, 32);
        contentPane.add(txtTextfieldname);

        JTextField textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(621, 325, 154, 32);
        contentPane.add(textField_1);

        JTextField textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(621, 381, 154, 32);
        contentPane.add(textField_2);

        JTextField textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(621, 441, 154, 32);
        contentPane.add(textField_3);

        JTextField textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(621, 512, 154, 32);
        contentPane.add(textField_4);

        JButton btnEnter = new JButton("ENTER");
        btnEnter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BlockChainGUI BlockChainGUIobj = new BlockChainGUI();
                contentPane.setVisible(true);
                dispose();

                StaticVars.name = txtTextfieldname.getText();
                StaticVars.address = textField_1.getText();
                StaticVars.bg = textField_2.getText();
                StaticVars.age = Integer.parseInt(textField_3.getText());
                String _diseases = textField_4.getText();
                String[] _disa = _diseases.split("[,]");
                String _allergies = textField_5.getText();
                String[] alga = _allergies.split("[,]");

                MedicalHistory mh = new MedicalHistory(StaticVars.name, StaticVars.address, StaticVars.bg, StaticVars.age);

                ArrayList<String> dis= new ArrayList<>();
                ArrayList<String> all = new ArrayList<>();

                Collections.addAll(dis, _disa);
                Collections.addAll(all, alga);
                mh.setALLERGIES(all);
                mh.setDISEASES(dis);
                try {
                    mh.store();
                } catch (SQLException z) {
                    z.printStackTrace();
                }
                Transaction tr = new Transaction(String.valueOf((float) (Math.random() * 1000000)), BlockChainGUI.m.b_chain.ID, StaticVars.tr_to_address);
                tr.setHistory(mh);
                BlockChainGUI.m.b_chain.validate_add_transaction(tr);
                TransferData td = new TransferData(BlockChainGUI.m.b_chain.ID, tr);
                SendData sd = new SendData(MainRun.BROADCAST_ADDRESS, 7777);
                try {
                    sd.broadcastData(td);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                StaticVars.clrall();
            }
        });
        btnEnter.setForeground(new Color(153, 0, 51));
        btnEnter.setFocusable(false);
        btnEnter.setBorderPainted(false);
        btnEnter.setBackground(Color.WHITE);
        btnEnter.setBounds(488, 618, 116, 32);
        contentPane.add(btnEnter);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("src/Images/logo2.png"));
        lblNewLabel.setBounds(-42, -55, 338, 260);
        contentPane.add(lblNewLabel);

        JLabel label_3 = new JLabel("Medi");
        label_3.setForeground(Color.WHITE);
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 60));
        label_3.setBounds(488, 54, 153, 54);
        contentPane.add(label_3);

        JLabel label_1 = new JLabel("Block");
        label_1.setForeground(new Color(153, 0, 51));
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 62));
        label_1.setBounds(622, 48, 200, 66);
        contentPane.add(label_1);

        JLabel lblAllergies = new JLabel("Allergies:");
        lblAllergies.setForeground(Color.WHITE);
        lblAllergies.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblAllergies.setBounds(347, 565, 196, 32);
        contentPane.add(lblAllergies);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(621, 572, 154, 32);
        contentPane.add(textField_5);

    }

}