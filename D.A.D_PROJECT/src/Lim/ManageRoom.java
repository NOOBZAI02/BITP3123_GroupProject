package Lim;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;

public class ManageRoom {

    JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
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

    /**
     * Create the application.
     */
    public ManageRoom() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 758, 537);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Manage Rooms");
        lblNewLabel.setBounds(309, 20, 200, 20);
        frame.getContentPane().add(lblNewLabel);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(129, 50, 495, 170);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {
                {"S001", "Single", "RM85"},
                {"S002", "Single", "RM85"},
                {"S003", "Single", "RM85"},
                {"D001", "Double", "RM155"},
                {"D002", "Double", "RM155"},
                {"D003", "Double", "RM155"},
                {"K001", "King", "RM210"},
                {"K002", "King", "RM210"},
                {"K003", "King", "RM210"}
            },
            new String[] {
                "Room ID", "Room Type", "Room Price"
            }
        ) {
            boolean[] columnEditables = new boolean[] {
                false, false, false
            };
            public boolean isCellEditable(int row, int column) {
                return columnEditables[column];
            }
        });

        scrollPane.setViewportView(table);

        JButton btnEditRoom = new JButton("Edit Room");
        btnEditRoom.setBounds(319, 240, 150, 25);
        frame.getContentPane().add(btnEditRoom);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(129, 240, 85, 25);
        frame.getContentPane().add(btnBack);

        btnEditRoom.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String roomId = (String) table.getValueAt(selectedRow, 0);
                String roomType = (String) table.getValueAt(selectedRow, 1);
                String roomPrice = (String) table.getValueAt(selectedRow, 2);

                String newRoomType = JOptionPane.showInputDialog(frame, "Enter new Room Type:", roomType);
                String newRoomPrice = JOptionPane.showInputDialog(frame, "Enter new Room Price:", roomPrice);

                // Update the room details in the table
                table.setValueAt(newRoomType, selectedRow, 1);
                table.setValueAt(newRoomPrice, selectedRow, 2);

                // Here, you can also update the room details in the database
                JOptionPane.showMessageDialog(frame, "Room details updated successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a room to edit.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> {
            frame.setVisible(false);
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
        });
    }
}
