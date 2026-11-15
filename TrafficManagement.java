import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TrafficManagement extends Frame implements ActionListener {

    // Colors
    private final Color darkBlue = new Color(20, 35, 55);
    private final Color blue = new Color(40, 120, 180);
    private final Color lightBlue = new Color(225, 242, 250);
    private final Color green = new Color(45, 150, 100);
    private final Color red = new Color(200, 70, 70);
    private final Color orange = new Color(230, 160, 50);
    private final Color darkText = new Color(45, 55, 65);

    // GUI components
    private TextField reportIdText;
    private TextField nameText;
    private TextField phoneText;
    private TextField locationText;
    private TextField vehicleText;
    private TextField descriptionText;

    private Choice categoryChoice;

    private Checkbox complaint;
    private Checkbox incident;
    private CheckboxGroup reportTypeGroup;

    private Button submit;
    private Button read;
    private Button update;
    private Button delete;
    private Button clear;
    private Button exit;

    private TextArea result;
    private Label statusValue;

    // Constructor
    public TrafficManagement() {

        setTitle("Traffic Complaint and Incident Management System");
        setSize(850, 720);
        setLayout(null);
        setBackground(lightBlue);

        // Title
        Label title = new Label(
                "TRAFFIC COMPLAINT & INCIDENT MANAGEMENT"
        );

        title.setBounds(75, 35, 700, 40);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBackground(darkBlue);
        title.setAlignment(Label.CENTER);
        add(title);

        // Subtitle
        Label subtitle = new Label(
                "Report traffic problems and incidents easily"
        );

        subtitle.setBounds(75, 75, 700, 30);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitle.setForeground(Color.WHITE);
        subtitle.setBackground(darkBlue);
        subtitle.setAlignment(Label.CENTER);
        add(subtitle);

        // Report ID
        addFormLabel("Report ID", 120);
        reportIdText = addTextField(117);

        // Name
        addFormLabel("Name", 160);
        nameText = addTextField(157);

        // Phone
        addFormLabel("Contact Number", 200);
        phoneText = addTextField(197);

        // Location
        addFormLabel("Location", 240);
        locationText = addTextField(237);

        // Report type
        addFormLabel("Report Type", 280);

        reportTypeGroup = new CheckboxGroup();

        complaint = new Checkbox(
                "Complaint", reportTypeGroup, false
        );
        complaint.setBounds(240, 278, 110, 25);
        complaint.setBackground(lightBlue);
        add(complaint);

        incident = new Checkbox(
                "Incident", reportTypeGroup, false
        );
        incident.setBounds(370, 278, 100, 25);
        incident.setBackground(lightBlue);
        add(incident);

        // Category
        addFormLabel("Category", 320);

        categoryChoice = new Choice();

        categoryChoice.add("Illegal Parking");
        categoryChoice.add("Traffic Signal Violation");
        categoryChoice.add("Over Speeding");
        categoryChoice.add("Wrong Side Driving");
        categoryChoice.add("Road Accident");
        categoryChoice.add("Traffic Jam");
        categoryChoice.add("Road Blockage");
        categoryChoice.add("Other");

        categoryChoice.setBounds(240, 317, 360, 28);
        add(categoryChoice);

        // Vehicle
        addFormLabel("Vehicle Number", 360);
        vehicleText = addTextField(357);

        // Description
        addFormLabel("Description", 400);
        descriptionText = addTextField(397);

        // Status
        addFormLabel("Status", 440);

        statusValue = new Label("PENDING");
        statusValue.setBounds(240, 437, 140, 28);
        statusValue.setFont(new Font("Arial", Font.BOLD, 14));
        statusValue.setForeground(Color.WHITE);
        statusValue.setBackground(orange);
        statusValue.setAlignment(Label.CENTER);
        add(statusValue);

        // Buttons
        submit = addButton("CREATE", 90, 110, green);
        read = addButton("READ", 210, 100, blue);
        update = addButton("UPDATE", 320, 110, orange);
        delete = addButton("DELETE", 440, 100, red);
        clear = addButton("CLEAR", 550, 100, blue);
        exit = addButton("EXIT", 660, 100, red);

        // Result area
        result = new TextArea();
        result.setBounds(90, 545, 670, 120);
        result.setFont(new Font("Monospaced", Font.PLAIN, 12));
        result.setEditable(false);
        add(result);

        // Window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Create a label
    private void addFormLabel(String text, int y) {

        Label label = new Label(text);

        label.setBounds(90, y, 140, 25);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(darkText);

        add(label);
    }

    // Create a text field
    private TextField addTextField(int y) {

        TextField field = new TextField();

        field.setBounds(240, y, 360, 28);

        add(field);

        return field;
    }

    // Create a button
    private Button addButton(
            String text, int x, int width, Color color) {

        Button button = new Button(text);

        button.setBounds(x, 485, width, 40);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.addActionListener(this);

        add(button);

        return button;
    }

    // Get selected type
    private String getSelectedType() {

        if (complaint.getState()) {
            return "Complaint";
        }

        if (incident.getState()) {
            return "Incident";
        }

        return "";
    }

    // Validate form fields
    private boolean validateForm() {

        if (nameText.getText().trim().isEmpty()
                || phoneText.getText().trim().isEmpty()
                || locationText.getText().trim().isEmpty()
                || vehicleText.getText().trim().isEmpty()
                || descriptionText.getText().trim().isEmpty()) {

            result.setText("Please fill in all required fields.");
            return false;
        }

        if (getSelectedType().isEmpty()) {

            result.setText("Please select Complaint or Incident.");
            return false;
        }

        return true;
    }

    // Parse report ID
    private Integer getReportId() {

        String text = reportIdText.getText().trim();

        if (text.isEmpty()) {
            result.setText("Please enter a Report ID.");
            return null;
        }

        try {

            int id = Integer.parseInt(text);

            if (id <= 0) {
                result.setText("Report ID must be positive.");
                return null;
            }

            return id;

        } catch (NumberFormatException e) {

            result.setText("Invalid Report ID.");
            return null;
        }
    }

    // CREATE: Save report in MySQL
    private void createReport() {

        if (!validateForm()) {
            return;
        }

        String sql =
                "INSERT INTO reports "
                        + "(name, phone, location, type, category, "
                        + "vehicle, description, status) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nameText.getText().trim());
            ps.setString(2, phoneText.getText().trim());
            ps.setString(3, locationText.getText().trim());
            ps.setString(4, getSelectedType());
            ps.setString(5, categoryChoice.getSelectedItem());
            ps.setString(6, vehicleText.getText().trim());
            ps.setString(7, descriptionText.getText().trim());
            ps.setString(8, "PENDING");

            int rows = ps.executeUpdate();

            if (rows > 0) {

                try (ResultSet rs = ps.getGeneratedKeys()) {

                    if (rs.next()) {

                        int id = rs.getInt(1);

                        reportIdText.setText(String.valueOf(id));

                        statusValue.setText("PENDING");
                        statusValue.setBackground(orange);

                        result.setText(
                                "REPORT CREATED SUCCESSFULLY!\n\n"
                                        + "Report ID: " + id + "\n"
                                        + "Name: " + nameText.getText() + "\n"
                                        + "Location: " + locationText.getText() + "\n"
                                        + "Type: " + getSelectedType() + "\n"
                                        + "Category: " + categoryChoice.getSelectedItem()
                                        + "\nStatus: PENDING\n"
                                        + "Saved in MySQL database."
                        );
                    }
                }
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    // READ: Retrieve reports from MySQL
    private void readReports() {

        String idText = reportIdText.getText().trim();

        String sql;

        if (idText.isEmpty()) {
            sql = "SELECT * FROM reports ORDER BY id";
        } else {
            sql = "SELECT * FROM reports WHERE id = ?";
        }

        StringBuilder output = new StringBuilder();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            if (!idText.isEmpty()) {

                Integer id = getReportId();

                if (id == null) {
                    return;
                }

                ps.setInt(1, id);
            }

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    output.append("Report ID: ")
                            .append(rs.getInt("id")).append("\n");

                    output.append("Name: ")
                            .append(rs.getString("name")).append("\n");

                    output.append("Contact: ")
                            .append(rs.getString("phone")).append("\n");

                    output.append("Location: ")
                            .append(rs.getString("location")).append("\n");

                    output.append("Type: ")
                            .append(rs.getString("type")).append("\n");

                    output.append("Category: ")
                            .append(rs.getString("category")).append("\n");

                    output.append("Vehicle: ")
                            .append(rs.getString("vehicle")).append("\n");

                    output.append("Description: ")
                            .append(rs.getString("description")).append("\n");

                    output.append("Status: ")
                            .append(rs.getString("status")).append("\n");

                    output.append("Created: ")
                            .append(rs.getTimestamp("created_at"))
                            .append("\n");

                    output.append("--------------------------------\n");

                    if (!idText.isEmpty()) {
                        loadReport(rs);
                    }
                }
            }

            if (output.length() == 0) {
                result.setText("No matching reports found.");
            } else {
                result.setText(output.toString());
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    // Load database record into form
    private void loadReport(ResultSet rs) throws SQLException {

        reportIdText.setText(String.valueOf(rs.getInt("id")));
        nameText.setText(rs.getString("name"));
        phoneText.setText(rs.getString("phone"));
        locationText.setText(rs.getString("location"));
        vehicleText.setText(rs.getString("vehicle"));
        descriptionText.setText(rs.getString("description"));

        String category = rs.getString("category");

        for (int i = 0; i < categoryChoice.getItemCount(); i++) {
            if (categoryChoice.getItem(i).equals(category)) {
                categoryChoice.select(i);
                break;
            }
        }

        String type = rs.getString("type");

        if ("Complaint".equals(type)) {
            complaint.setState(true);
        } else {
            incident.setState(true);
        }

        String status = rs.getString("status");

        statusValue.setText(status);
        statusValue.setBackground(
                "PENDING".equalsIgnoreCase(status) ? orange : green
        );
    }

    // UPDATE: Modify report in MySQL
    private void updateReport() {

        Integer id = getReportId();

        if (id == null || !validateForm()) {
            return;
        }

        String sql =
                "UPDATE reports SET name=?, phone=?, location=?, "
                        + "type=?, category=?, vehicle=?, description=? "
                        + "WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nameText.getText().trim());
            ps.setString(2, phoneText.getText().trim());
            ps.setString(3, locationText.getText().trim());
            ps.setString(4, getSelectedType());
            ps.setString(5, categoryChoice.getSelectedItem());
            ps.setString(6, vehicleText.getText().trim());
            ps.setString(7, descriptionText.getText().trim());
            ps.setInt(8, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                result.setText(
                        "Report ID " + id + " updated successfully in MySQL."
                );

            } else {

                result.setText("Report ID " + id + " was not found.");
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    // DELETE: Remove report from MySQL
    private void deleteReport() {

        Integer id = getReportId();

        if (id == null) {
            return;
        }

        String sql = "DELETE FROM reports WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                clearFields();

                result.setText(
                        "Report ID " + id + " deleted from MySQL successfully."
                );

            } else {

                result.setText("Report ID " + id + " was not found.");
            }

        } catch (SQLException e) {
            showDatabaseError(e);
        }
    }

    // CLEAR
    private void clearFields() {

        reportIdText.setText("");
        nameText.setText("");
        phoneText.setText("");
        locationText.setText("");
        vehicleText.setText("");
        descriptionText.setText("");

        reportTypeGroup.setSelectedCheckbox(null);
        categoryChoice.select(0);

        statusValue.setText("PENDING");
        statusValue.setBackground(orange);
    }

    // Display SQL errors
    private void showDatabaseError(SQLException e) {

        e.printStackTrace();

        result.setText(
                "DATABASE ERROR:\n"
                        + e.getMessage()
                        + "\n\nCheck DBConnection.java, MySQL, "
                        + "table columns and JDBC driver."
        );
    }

    // Button events
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == submit) {
            createReport();
        } else if (e.getSource() == read) {
            readReports();
        } else if (e.getSource() == update) {
            updateReport();
        } else if (e.getSource() == delete) {
            deleteReport();
        } else if (e.getSource() == clear) {
            clearFields();
            result.setText("Form cleared successfully.");
        } else if (e.getSource() == exit) {
            dispose();
        }
    }

    // Main
    public static void main(String[] args) {

        EventQueue.invokeLater(TrafficManagement::new);
    }
}
