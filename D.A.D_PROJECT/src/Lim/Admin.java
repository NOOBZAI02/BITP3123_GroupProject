package Lim;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Admin {

    JFrame frame;
    private JTextField textField;
    private int totalBookingsToday = 5; // Placeholder for total bookings. Replace with actual logic.

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Admin window = new Admin();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Admin() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 856, 539);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("YSL HOTEL BOOKING SYSTEM");
        lblNewLabel.setBounds(315, 70, 200, 38);
        frame.getContentPane().add(lblNewLabel);

        JButton btnManageBooking = new JButton("Manage Booking");
        btnManageBooking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            AdminBooking window = new AdminBooking("Mr. Lim");
                            window.frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        btnManageBooking.setBounds(293, 135, 189, 21);
        frame.getContentPane().add(btnManageBooking);

        JButton btnManageRoom = new JButton("Manage Rooms");
        btnManageRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            ManageRoom window = new ManageRoom();
                            window.frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        btnManageRoom.setBounds(293, 166, 189, 21);
        frame.getContentPane().add(btnManageRoom);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });
        btnExit.setBounds(321, 197, 118, 21);
        frame.getContentPane().add(btnExit);

        textField = new JTextField("Good Morning Mr. Lim");
        textField.setBounds(325, 102, 200, 19);
        textField.setEditable(false);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
    }

    public void showFrame() {
        this.frame.setVisible(true);
    }
}
