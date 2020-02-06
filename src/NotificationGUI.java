import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.ImageIcon;
import java.awt.Font;

public class NotificationGUI extends JFrame {

    static DefaultListModel<String> model;
    JPanel contentPane;
    private JTable table;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    NotificationGUI frame = new NotificationGUI();
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
    public NotificationGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(150, 0,1080, 500);
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);


        model = new DefaultListModel<String>();

        @SuppressWarnings("unchecked")
        JList list_1 = new JList(model);
        list_1.setBackground(Color.DARK_GRAY);
        list_1.setForeground(Color.WHITE);
        list_1.setBounds(412, 29, 638, 386);
        contentPane.add(list_1);

        JLabel label = new JLabel("");
        label.setIcon(new ImageIcon("src/Images/logol.png"));
        label.setBounds(-160, -40, 1261, 480);
        contentPane.add(label);

        JLabel label_1 = new JLabel("Medi");
        label_1.setForeground(Color.WHITE);
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 40));
        label_1.setBounds(78, 280, 153, 54);
        contentPane.add(label_1);

        JLabel label_2 = new JLabel("Block");
        label_2.setForeground(new Color(153, 0, 51));
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 42));
        label_2.setBounds(164, 273, 200, 66);
        contentPane.add(label_2);

        JLabel lblConsole = new JLabel("CONSOLE");
        lblConsole.setForeground(Color.LIGHT_GRAY);
        lblConsole.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lblConsole.setBackground(Color.WHITE);
        lblConsole.setBounds(84, 340, 245, 59);
        contentPane.add(lblConsole);

    }
}