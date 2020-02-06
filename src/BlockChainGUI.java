import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class BlockChainGUI {

    private JFrame frame;
    private JButton btnStartMining;
    final static MainRun m = new MainRun();
    CreateTransactionGUI CreateTransactionobj;


    /**
     * Launch the application.
     * @throws NoSuchAlgorithmException
     * @throws SocketException
     */
    public static void main(String[] args) throws SocketException, NoSuchAlgorithmException {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    m.setup();
                } catch (SocketException | NoSuchAlgorithmException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    BlockChainGUI window = new BlockChainGUI();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Here");
            }
        });
    }

    /**
     * Create the application.
     */
    public BlockChainGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(64, 64, 64));
        frame.getContentPane().setLayout(null);
        frame.setBounds(150, 0,1250, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //NotificationGUI frame1 = new NotificationGUI();
        //frame1.setVisible(true);

        JButton btnNewButton = new JButton("Create Transaction");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateTransactionobj = new CreateTransactionGUI ();
                frame.setVisible(true);
                frame.dispose();
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnNewButton.setFocusable(false);
        btnNewButton.setBorderPainted(false);
        btnNewButton.setForeground(Color.WHITE);
        btnNewButton.setBackground(new Color(153, 0, 51));
        btnNewButton.setBounds(783, 249, 258, 45);
        frame.getContentPane().add(btnNewButton);

        btnStartMining = new JButton("Start Mining");
        btnStartMining.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Thread miningThread = new Thread(m.b_chain);
                MainRun.stopThread_mining = false;
                miningThread.start();
            }
        });
        btnStartMining.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnStartMining.setForeground(Color.WHITE);
        btnStartMining.setFocusable(false);
        btnStartMining.setBorderPainted(false);
        btnStartMining.setBackground(new Color(153, 0, 51));
        btnStartMining.setBounds(783, 353, 258, 45);
        frame.getContentPane().add(btnStartMining);

        JButton btnStopMining = new JButton("Stop Mining");
        btnStopMining.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainRun.stopThread_mining = true;
            }
        });
        btnStopMining.setFont(new Font("Tahoma", Font.PLAIN, 18));
        btnStopMining.setForeground(Color.WHITE);
        btnStopMining.setFocusable(false);
        btnStopMining.setBorderPainted(false);
        btnStopMining.setBackground(new Color(153, 0, 51));
        btnStopMining.setBounds(783, 456, 258, 45);
        frame.getContentPane().add(btnStopMining);

        JButton button = new JButton("Exit");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    m.exit();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                System.exit(JFrame.EXIT_ON_CLOSE);
            }
        });
        button.setForeground(new Color(153, 0, 51));
        button.setFocusable(false);
        button.setBorderPainted(false);
        button.setBackground(Color.WHITE);
        button.setBounds(1054, 684, 116, 45);
        frame.getContentPane().add(button);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("src/Images/logo2.png"));
        lblNewLabel.setBounds(131, 137, 1461, 480);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblMedi = new JLabel("Medi");
        lblMedi.setFont(new Font("Tahoma", Font.PLAIN, 40));
        lblMedi.setForeground(Color.WHITE);
        lblMedi.setBounds(332, 455, 153, 54);
        frame.getContentPane().add(lblMedi);

        JLabel lblBlock = new JLabel("Block");
        lblBlock.setFont(new Font("Tahoma", Font.PLAIN, 42));
        lblBlock.setForeground(new Color(153, 0, 51));
        lblBlock.setBounds(416, 455, 153, 54);
        frame.getContentPane().add(lblBlock);

        JButton btnConsole = new JButton("Console");
        btnConsole.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NotificationGUI NotificationGUIobj = new NotificationGUI();
                NotificationGUIobj.setVisible(true);
            }
        });
        btnConsole.setForeground(new Color(153, 0, 51));
        btnConsole.setFocusable(false);
        btnConsole.setBorderPainted(false);
        btnConsole.setBackground(Color.WHITE);
        btnConsole.setBounds(23, 684, 116, 45);
        frame.getContentPane().add(btnConsole);

        JLabel label = new JLabel("ID: " + m.b_chain.ID);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Tahoma", Font.PLAIN, 18));
        label.setBounds(373, 507, 196, 32);
        frame.getContentPane().add(label);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnHelp = new JMenu("HELP");
        menuBar.add(mnHelp);

        JMenu mnSettings = new JMenu("SETTINGS");
        mnSettings.setForeground(Color.DARK_GRAY);
        menuBar.add(mnSettings);

        JMenuItem mntmBlockchainsettings = new JMenuItem("BlockChainSettings");
        mntmBlockchainsettings.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SettingsGUI SettingsGUIobj = new SettingsGUI();
                SettingsGUIobj.setVisible(true);

            }
        });
        mnSettings.add(mntmBlockchainsettings);


    }
}