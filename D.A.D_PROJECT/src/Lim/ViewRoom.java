package Lim;

import java.awt.EventQueue;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class ViewRoom {

    JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnBookRoom;
    private JButton btnBack;
    private JComboBox<String> comboBox;
    private String userName;
    private HashMap<String, List<String>> roomBookings; // Room ID to list of dates booked

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ViewRoom window = new ViewRoom();
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
    public ViewRoom() {
        roomBookings = new HashMap<>();
        fetchRoomBookings(); // Fetch room bookings when the application starts
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

        JLabel lblNewLabel = new JLabel("YSL Hotel Room List");
        lblNewLabel.setBounds(309, 77, 130, 13);
        frame.getContentPane().add(lblNewLabel);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(129, 100, 495, 170);
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
                {"K003", "King", "RM210"},
            },
            new String[] {
                "ID", "Room Type", "Room Price /day"
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

        btnBookRoom = new JButton("Book Room");
        btnBookRoom.setBounds(319, 305, 120, 25);
        btnBookRoom.setEnabled(false); // Initially disabled
        frame.getContentPane().add(btnBookRoom);

        btnBack = new JButton("Back");
        btnBack.setBounds(129, 307, 85, 21);
        frame.getContentPane().add(btnBack);

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"Select days", "1 day", "2 days", "3 days"}));
        comboBox.setBounds(518, 307, 106, 21);
        frame.getContentPane().add(comboBox);

        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedIndex() > 0) { // Ensure a valid selection
                btnBookRoom.setEnabled(true);
            } else {
                btnBookRoom.setEnabled(false);
            }
        });

        btnBookRoom.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                String roomId = (String) table.getValueAt(selectedRow, 0);
                String roomType = (String) table.getValueAt(selectedRow, 1);
                String roomPriceStr = (String) table.getValueAt(selectedRow, 2);
                int roomPrice = Integer.parseInt(roomPriceStr.substring(2)); // Remove "RM" and parse integer
                int days = comboBox.getSelectedIndex(); // Days selected

                // Calculate total price
                int totalPrice = roomPrice * days;

                // Calculate check-in and check-out dates
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();
                String checkInDate = sdf.format(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, days);
                String checkOutDate = sdf.format(calendar.getTime());

                // Check if the room is already booked
                if (isRoomBooked(roomId, checkInDate, checkOutDate)) {
                    JOptionPane.showMessageDialog(frame,
                        "The room is already booked for the selected dates.",
                        "Booking Error",
                        JOptionPane.ERROR_MESSAGE);
                } else {
                    // Show booking details
                    JOptionPane.showMessageDialog(frame,
                        "Booking Room:\n" +
                        "ID: " + roomId + "\n" +
                        "Type: " + roomType + "\n" +
                        "Total Price: RM" + totalPrice + "\n" +
                        "Check-in Date: " + checkInDate + "\n" +
                        "Check-out Date: " + checkOutDate,
                        "Booking Confirmation",
                        JOptionPane.INFORMATION_MESSAGE);

                    // Send booking details to PHP backend
                    bookRoom(roomId, roomType, totalPrice, checkInDate, checkOutDate);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a room to book.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

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

    private void fetchRoomBookings() {
        try {
            URL url = new URL("http://localhost/dad_lim/check_bookings.php");
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

                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject booking = jsonArray.getJSONObject(i);
                    String roomId = booking.getString("room_id");
                    String checkInDate = booking.getString("check_in_date");
                    String checkOutDate = booking.getString("check_out_date");

                    // Add bookings to the roomBookings map
                    roomBookings.computeIfAbsent(roomId, k -> new ArrayList<>()).add(checkInDate + ":" + checkOutDate);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isRoomBooked(String roomId, String checkInDate, String checkOutDate) {
        List<String> bookings = roomBookings.get(roomId);
        if (bookings != null) {
            for (String booking : bookings) {
                String[] dates = booking.split(":");
                String bookedCheckIn = dates[0];
                String bookedCheckOut = dates[1];

                // Check if the requested dates overlap with any existing bookings
                if ((checkInDate.compareTo(bookedCheckIn) >= 0 && checkInDate.compareTo(bookedCheckOut) <= 0) ||
                    (checkOutDate.compareTo(bookedCheckIn) >= 0 && checkOutDate.compareTo(bookedCheckOut) <= 0) ||
                    (checkInDate.compareTo(bookedCheckIn) <= 0 && checkOutDate.compareTo(bookedCheckOut) >= 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void bookRoom(String roomId, String roomType, int totalPrice, String checkInDate, String checkOutDate) {
        try {
            URL url = new URL("http://localhost/dad_lim/insert_booking.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);

            // Create JSON payload
            JSONObject jsonInput = new JSONObject();
            jsonInput.put("room_id", roomId);
            jsonInput.put("room_type", roomType);
            jsonInput.put("total_price", totalPrice);
            jsonInput.put("check_in_date", checkInDate);
            jsonInput.put("check_out_date", checkOutDate);
            jsonInput.put("username", userName);

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
                JOptionPane.showMessageDialog(frame, "Room booked successfully! Returning to the YSL Hotel Page.", "Success", JOptionPane.INFORMATION_MESSAGE);
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
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error booking room: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
