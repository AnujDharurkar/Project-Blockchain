import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class MedicalObjectGUI extends JFrame {

    private JPanel contentPane;
    private JTextField Organ_Drug;
    private JTextField Quantity;
    private JTextField Funds;
    private JTextField BloodType;
    private JTextField nameF;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MedicalObjectGUI frame = new MedicalObjectGUI();
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
    public MedicalObjectGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 0,1250, 800);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblOrgandrug = new JLabel("ORGAN/DRUG :");
        lblOrgandrug.setForeground(Color.WHITE);
        lblOrgandrug.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblOrgandrug.setBounds(275, 245, 196, 32);
        contentPane.add(lblOrgandrug);

        JLabel lblQuantity = new JLabel("QUANTITY :");
        lblQuantity.setForeground(Color.WHITE);
        lblQuantity.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblQuantity.setBounds(275, 308, 196, 32);
        contentPane.add(lblQuantity);

        JLabel lblFundsToBe = new JLabel("FUNDS TO BE TRANSFER : ");
        lblFundsToBe.setForeground(Color.WHITE);
        lblFundsToBe.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblFundsToBe.setBounds(275, 371, 243, 32);
        contentPane.add(lblFundsToBe);

        JLabel lblBloodtype = new JLabel("BLOOD TYPE :");
        lblBloodtype.setForeground(Color.WHITE);
        lblBloodtype.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblBloodtype.setBounds(275, 439, 196, 32);
        contentPane.add(lblBloodtype);

        JLabel lblMedicalObject = new JLabel("MEDICAL OBJECT");
        lblMedicalObject.setForeground(new Color(153, 0, 51));
        lblMedicalObject.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lblMedicalObject.setBackground(new Color(153, 0, 51));
        lblMedicalObject.setBounds(427, 160, 348, 59);
        contentPane.add(lblMedicalObject);

        Organ_Drug = new JTextField();
        Organ_Drug.setColumns(10);
        Organ_Drug.setBounds(577, 252, 154, 32);
        contentPane.add(Organ_Drug);

        Quantity = new JTextField();
        Quantity.setColumns(10);
        Quantity.setBounds(577, 315, 154, 32);
        contentPane.add(Quantity);

        Funds = new JTextField();
        Funds.setColumns(10);
        Funds.setBounds(577, 378, 154, 32);
        contentPane.add(Funds);

        BloodType = new JTextField();
        BloodType.setColumns(10);
        BloodType.setBounds(577, 446, 154, 32);
        contentPane.add(BloodType);

        JButton button = new JButton("ENTER");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BlockChainGUI BlockChainGUIobj = new BlockChainGUI();
                contentPane.setVisible(true);
                dispose();

                StaticVars.OD = Organ_Drug.getText();
                StaticVars.quantity = Integer.parseInt(Quantity.getText());
                StaticVars.ftt = Float.valueOf(Funds.getText());
                StaticVars.bg2 = BloodType.getText();
                StaticVars.name2 = nameF.getText();
                if (BlockChainGUI.m.b_chain.getFunds() < StaticVars.ftt) {
                    StaticVars.ftt = 0;
                    NotificationGUI.model.addElement("Insufficient funds! None will be sent.");
                }
                MedicalObject obj = new MedicalObject(StaticVars.OD, StaticVars.name2, StaticVars.quantity, StaticVars.ftt);

                try {
                    obj.setBloodType(StaticVars.bg2);
                    obj.store();
                } catch (SQLException f) {
                    f.printStackTrace();
                }
                Transaction tr = new Transaction(String.valueOf((float) (Math.random() * 1000000)), BlockChainGUI.m.b_chain.ID, StaticVars.tr_to_address);
                tr.setObject(obj);
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
        button.setForeground(new Color(153, 0, 51));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.setBounds(450, 549, 116, 32);
        contentPane.add(button);

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

        JLabel label_2 = new JLabel("");
        label_2.setIcon(new ImageIcon("src/Images/logo2.png"));
        label_2.setBounds(-42, -55, 338, 260);
        contentPane.add(label_2);

        JLabel lblName = new JLabel("NAME :");
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblName.setBounds(275, 494, 196, 32);
        contentPane.add(lblName);

        nameF = new JTextField();
        nameF.setColumns(10);
        nameF.setBounds(577, 496, 154, 32);
        contentPane.add(nameF);
        setVisible(true);
    }

}