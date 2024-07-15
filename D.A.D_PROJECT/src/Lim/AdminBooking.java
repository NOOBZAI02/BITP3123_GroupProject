package Lim;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class AdminBooking {

    JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnCancelBooking;
    private JButton btnBack;
    private String userName;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminBooking window = new AdminBooking("Admin");
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
    public AdminBooking(String userName) {
        this.userName = userName;
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

        JLabel lblNewLabel = new JLabel("Admin Booking Management");
        lblNewLabel.setBounds(309, 20, 200, 20);
        frame.getContentPane().add(lblNewLabel);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(129, 50, 495, 170);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        updateTable();
        scrollPane.setViewportView(table);

        btnCancelBooking = new JButton("Cancel Booking");
        btnCancelBooking.setBounds(319, 240, 150, 25);
        frame.getContentPane().add(btnCancelBooking);

        btnBack = new JButton("Back");
        btnBack.setBounds(129, 240, 85, 25);
        frame.getContentPane().add(btnBack);

        btnCancelBooking.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String bookingId = (String) table.getValueAt(selectedRow, 0);

                // Show cancellation details
                int confirm = JOptionPane.showConfirmDialog(frame,
                    "Are you sure you want to cancel the booking with ID: " + bookingId + "?",
                    "Cancel Booking",
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    // Cancel the booking via PHP backend
                    cancelBooking(bookingId);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a booking to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnBack.addActionListener(e -> {
            frame.setVisible(false);
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        Admin window = new Admin();
                        window.showFrame();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private void cancelBooking(String bookingId) {
        try {
            URL url = new URL("http://localhost/dad_lim/cancel_booking.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Create JSON payload
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("cancel_booking_id", bookingId);

            // Write JSON input to the connection
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInput.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                JOptionPane.showMessageDialog(frame, "Booking ID " + bookingId + " cancelled successfully!");
                updateTable();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error cancelling booking: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        try {
            URL url = new URL("http://localhost/dad_lim/manage_booking.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            // Read the response
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // Parse JSON response
                JSONArray bookings = new JSONArray(response.toString());
                Object[][] data = new Object[bookings.length()][5];
                for (int i = 0; i < bookings.length(); i++) {
                    JSONObject booking = bookings.getJSONObject(i);
                    data[i][0] = booking.getString("id");
                    data[i][1] = booking.getString("room_id");
                    data[i][2] = booking.getString("room_type");
                    data[i][3] = booking.getString("total_price");
                    data[i][4] = booking.getString("username");

                }                         

                table.setModel(new DefaultTableModel(
                    data,
                    new String[] {
                        "Booking ID", "Room ID", "Room Type", "Total Price", "User Name"
                    }
                ) {
                    boolean[] columnEditables = new boolean[] {
                        false, false, false, false, false
                    };
                    public boolean isCellEditable(int row, int column) {
                        return columnEditables[column];
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error fetching booking data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
