package Lim;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class UserBooking {

    JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnBack;
    private String userName;

    /**
     * Launch the application.
     */

    public UserBooking(String userName) {
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

        JLabel lblNewLabel = new JLabel("Your Bookings");
        lblNewLabel.setBounds(309, 20, 200, 20);
        frame.getContentPane().add(lblNewLabel);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(129, 50, 495, 170);
        frame.getContentPane().add(scrollPane);

        table = new JTable();
        updateTable();
        scrollPane.setViewportView(table);

        btnBack = new JButton("Back");
        btnBack.setBounds(309, 245, 85, 25);
        frame.getContentPane().add(btnBack);

        btnBack.addActionListener(e -> {
            frame.setVisible(false);
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        user window = new user();
                        window.setUserName(userName);
                        window.frame.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    private void cancelBooking(String bookingId) {
        // Implement the logic to cancel the booking here
        System.out.println("Booking cancelled: " + bookingId);
        JOptionPane.showMessageDialog(frame, "Booking ID " + bookingId + " cancelled successfully!");

        // Optionally, you can refresh the table to reflect the changes
        updateTable();
    }

    private void updateTable() {
        String urlString = "http://localhost/dad_lim/fetch_booking.php?username=" + userName;
        DefaultTableModel model = new DefaultTableModel(new String[]{"Booking ID", "Room ID", "Room Type", "Total Price"}, 0);

        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(content.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject booking = jsonArray.getJSONObject(i);
                String bookingId = booking.getString("id");
                String roomId = booking.getString("room_id");
                String roomType = booking.getString("room_type");
                String totalPrice = booking.getString("total_price");
                model.addRow(new Object[]{bookingId, roomId, roomType, totalPrice});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setModel(model);
    }
    

}
