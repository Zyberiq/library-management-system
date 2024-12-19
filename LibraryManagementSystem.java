import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

class Book {
    private int id;
    private String title, author, publisher;
    private int year;
    private boolean isAvailable;

    public Book(int id, String title, String author, String publisher, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.isAvailable = true;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getYear() { return year; }
    public boolean isAvailable() { return isAvailable; }
    public void borrowBook() { this.isAvailable = false; }
    public void returnBook() { this.isAvailable = true; }

    @Override
    public String toString() { return title + " by " + author + " (" + year + ")"; }
}

class Student {
    private int id;
    private String name, email, password, role;

    public Student(int id, String name, String email, String password, String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;  // Student, Teacher, Admin
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    @Override
    public String toString() { return name + " (" + email + ")"; }
}
public class LibraryManagementSystem {
    private JFrame frame;
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Student> students = new ArrayList<>();
    private Student loggedInStudent;

    // Declare bookTable at the class level
    private JTable bookTable;

    public LibraryManagementSystem() {
        // Sample users (Admin, Student, Teacher)
        students.add(new Student(1, "admin", "admin@example.com", "admin", "Admin"));
        students.add(new Student(2, "student", "student@example.com", "student", "Student"));
        students.add(new Student(3, "teacher", "teacher@example.com", "teacher", "Teacher"));

        // Adding 50+ books
        for (int i = 1; i <= 50; i++) {
            books.add(new Book(i, "Book Title " + i, "Author " + i, "Publisher " + i, 2000 + (i % 20)));
        }

        frame = new JFrame("Library Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.getContentPane().add(new SplashScreen());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }
    

    class SplashScreen extends JPanel {
        public SplashScreen() {
            setLayout(new BorderLayout());
            
            // Adding a smooth gradient background to the entire panel
            JPanel gradientPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g;
                    // Gradient from black to dark grey
                    GradientPaint gp = new GradientPaint(0, 0, Color.BLACK, getWidth(), getHeight(), new Color(50, 50, 50));
                    g2.setPaint(gp);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            gradientPanel.setLayout(new BorderLayout());
    
            // Title Panel (Centered text)
            JPanel titlePanel = new JPanel();
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
    
            // Project Name (Main title)
            JLabel projectNameLabel = new JLabel("Library Management System", JLabel.CENTER);
            projectNameLabel.setFont(new Font("Arial", Font.BOLD, 55));
            projectNameLabel.setForeground(Color.WHITE);  // White text for contrast on dark background
            projectNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            projectNameLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add space around the title
            titlePanel.add(projectNameLabel);
    
            // University Name in Arial, enhanced style
            JLabel universityLabel = new JLabel("Thal University Bhakkar", JLabel.CENTER);
            universityLabel.setFont(new Font("Arial", Font.PLAIN, 35));
            universityLabel.setForeground(new Color(255, 255, 255, 180)); // Slightly faded effect for elegance
            universityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            universityLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 30, 0)); // Space below university name
            titlePanel.add(universityLabel);
    
            // Adding the title panel to the gradient panel
            gradientPanel.add(titlePanel, BorderLayout.CENTER);
    
            // Button Panel (Centered)
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20)); // Centered button with vertical spacing
    
            // "Proceed to Login" Button with black background and custom look
            JButton proceedButton = new JButton("Proceed to Login") {
                @Override
                protected void paintComponent(Graphics g) {
                    if (getModel().isArmed()) {
                        g.setColor(new Color(80, 80, 80));  // Darker shade when clicked (pressed)
                    } else if (getModel().isRollover()) {
                        g.setColor(new Color(60, 60, 60));  // Slightly lighter on hover (hover effect)
                    } else {
                        g.setColor(Color.WHITE);  // Normal background color is black
                    }
                    g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);  // Rounded corners
                    super.paintComponent(g);  // Standard button painting
                }
            };
    
            proceedButton.setFont(new Font("Arial", Font.BOLD, 22));  // Bold Arial font for the text
            proceedButton.setForeground(Color.black);  // Button text color changed to white for contrast
            proceedButton.setPreferredSize(new Dimension(250, 60));  // Button size
            proceedButton.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Hand cursor on hover to indicate clickability
            proceedButton.setFocusPainted(false);  // Remove focus border for a cleaner look
            proceedButton.setBorder(BorderFactory.createEmptyBorder());  // Remove default border for a sleek look
    
            // Action when the button is clicked
            proceedButton.addActionListener(e -> goToLogin());
    
            buttonPanel.add(proceedButton);
    
            // Adding the button panel to the gradient panel
            gradientPanel.add(buttonPanel, BorderLayout.SOUTH);
    
            // Finally, add the gradient panel to the main panel
            add(gradientPanel);
        }
    
        // Method to transition to the Login Screen
        private void goToLogin() {
            // Assuming frame is initialized elsewhere (not shown here)
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new LoginPanel());
            frame.revalidate();
            frame.repaint();
        }
    }
        

    class LoginPanel extends JPanel {
        private JTextField usernameField;
        private JPasswordField passwordField;
    
        public LoginPanel() {
            setLayout(new GridBagLayout());
            setBackground(new Color(34, 40, 49));
    
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
    
            JLabel titleLabel = new JLabel("Library Management System", JLabel.CENTER);
            titleLabel.setFont(new Font("Verdana", Font.BOLD, 30));
            titleLabel.setForeground(Color.WHITE);
            gbc.gridwidth = 2;
            add(titleLabel, gbc);
    
            // Username Field
            addField("Username:", gbc, 1);
            usernameField = new JTextField(20);
            usernameField.setName("username");
            usernameField.setText("Enter your username");  // Placeholder text
            usernameField.setForeground(Color.GRAY);
            usernameField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (usernameField.getText().equals("Enter your username")) {
                        usernameField.setText("");
                        usernameField.setForeground(Color.BLACK);
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (usernameField.getText().isEmpty()) {
                        usernameField.setForeground(Color.GRAY);
                        usernameField.setText("Enter your username");
                    }
                }
            });
            add(usernameField, gbc);
    
            // Password Field
            addField("Password:", gbc, 2);
            passwordField = new JPasswordField(20);
            passwordField.setName("password");
            passwordField.setText("Enter your password");  // Placeholder text
            passwordField.setForeground(Color.GRAY);
            passwordField.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (new String(passwordField.getPassword()).equals("Enter your password")) {
                        passwordField.setText("");
                        passwordField.setForeground(Color.BLACK);
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (new String(passwordField.getPassword()).isEmpty()) {
                        passwordField.setForeground(Color.GRAY);
                        passwordField.setText("Enter your password");
                    }
                }
            });
            add(passwordField, gbc);
    
            // Buttons Section
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));
            buttonPanel.setOpaque(false);
    
            JButton studentLoginButton = new JButton("Login as Student");
            studentLoginButton.addActionListener(e -> handleLogin("Student"));
            buttonPanel.add(studentLoginButton);
    
            JButton teacherLoginButton = new JButton("Login as Teacher");
            teacherLoginButton.addActionListener(e -> handleLogin("Teacher"));
            buttonPanel.add(teacherLoginButton);
    
            JButton adminLoginButton = new JButton("Login as Admin");
            adminLoginButton.addActionListener(e -> handleLogin("Admin"));
            buttonPanel.add(adminLoginButton);
    
            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(e -> goToRegisterPanel());
            buttonPanel.add(registerButton);
    
            gbc.gridx = 0;
            gbc.gridy = 3;
            gbc.gridwidth = 2;
            add(buttonPanel, gbc);
        }
    
        private void addField(String label, GridBagConstraints gbc, int yPos) {
            gbc.gridx = 0;
            gbc.gridy = yPos;
            JLabel lbl = new JLabel(label);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.PLAIN, 18));
            add(lbl, gbc);
        }
    
        private void handleLogin(String role) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
    
            for (Student student : students) {
                if (student.getName().equals(username) && student.getPassword().equals(password) && student.getRole().equals(role)) {
                    loggedInStudent = student;
                    goToDashboard();
                    return;
                }
            }
    
            JOptionPane.showMessageDialog(frame, "Invalid username, password, or role", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    
        private void goToDashboard() {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new DashboardPanel());
            frame.revalidate();
            frame.repaint();
        }
    
        private void goToRegisterPanel() {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new RegisterPanel());
            frame.revalidate();
            frame.repaint();
        }
    }
    
    class RegisterPanel extends JPanel {
        private JTextField nameField, emailField, usernameField, passwordField;
        private JComboBox<String> roleComboBox;
    
        public RegisterPanel() {
            setLayout(new GridBagLayout());
            setBackground(new Color(34, 40, 49));
    
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
    
            JLabel titleLabel = new JLabel("Register as Student or Teacher", JLabel.CENTER);
            titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
            titleLabel.setForeground(Color.WHITE);
            gbc.gridwidth = 2;
            add(titleLabel, gbc);
    
            addField("Name:", gbc, 1);
            nameField = new JTextField(20);
            nameField.setName("name");
            nameField.setText("Enter your full name");  // Placeholder text
            nameField.setForeground(Color.GRAY);
            setPlaceholderBehavior(nameField);
            add(nameField, gbc);
    
            addField("Email:", gbc, 2);
            emailField = new JTextField(20);
            emailField.setName("email");
            emailField.setText("Enter your email address");  // Placeholder text
            emailField.setForeground(Color.GRAY);
            setPlaceholderBehavior(emailField);
            add(emailField, gbc);
    
            addField("Username:", gbc, 3);
            usernameField = new JTextField(20);
            usernameField.setName("username");
            usernameField.setText("Choose a username");  // Placeholder text
            usernameField.setForeground(Color.GRAY);
            setPlaceholderBehavior(usernameField);
            add(usernameField, gbc);
    
            addField("Password:", gbc, 4);
            passwordField = new JTextField(20);
            passwordField.setName("password");
            passwordField.setText("Enter a strong password");  // Placeholder text
            passwordField.setForeground(Color.GRAY);
            setPlaceholderBehavior(passwordField);
            add(passwordField, gbc);
    
            addField("Role:", gbc, 5);
            roleComboBox = new JComboBox<>(new String[]{"Student", "Teacher"});
            roleComboBox.setName("role");
            add(roleComboBox, gbc);
    
            JButton registerButton = new JButton("Register");
            registerButton.addActionListener(e -> handleRegister());
            gbc.gridx = 0;
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            add(registerButton, gbc);
        }
    
        private void setPlaceholderBehavior(JTextField field) {
            field.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (field.getText().startsWith("Enter") || field.getText().startsWith("Choose")) {
                        field.setText("");
                        field.setForeground(Color.BLACK);
                    }
                }
    
                public void focusLost(FocusEvent e) {
                    if (field.getText().isEmpty()) {
                        field.setForeground(Color.GRAY);
                        if (field == nameField) field.setText("Enter your full name");
                        else if (field == emailField) field.setText("Enter your email address");
                        else if (field == usernameField) field.setText("Choose a username");
                        else if (field == passwordField) field.setText("Enter a strong password");
                    }
                }
            });
        }
    
        private void addField(String label, GridBagConstraints gbc, int yPos) {
            gbc.gridx = 0;
            gbc.gridy = yPos;
            JLabel lbl = new JLabel(label);
            lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Arial", Font.PLAIN, 18));
            add(lbl, gbc);
        }
    
        private void handleRegister() {
            String name = nameField.getText();
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = passwordField.getText();
            String role = (String) roleComboBox.getSelectedItem();
    
            int id = students.size() + 1;
            students.add(new Student(id, name, email, password, role));
    
            JOptionPane.showMessageDialog(frame, "Registration Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            goToLogin();
        }
    
        private void goToLogin() {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new LoginPanel());
            frame.revalidate();
            frame.repaint();
        }
    }
    

    // Dashboard Panel
    class DashboardPanel extends JPanel {
        private JTabbedPane tabbedPane;

        public DashboardPanel() {
            setLayout(new BorderLayout());
            setBackground(new Color(34, 40, 49));

            tabbedPane = new JTabbedPane();

            if (loggedInStudent.getRole().equals("Admin")) {
                tabbedPane.addTab("Admin Dashboard", createAdminPanel());
            }

            tabbedPane.addTab("Books", createBookPanel());
            tabbedPane.addTab("Borrow Book", createBorrowPanel());
            tabbedPane.addTab("Return Book", createReturnBookPanel());

            if (loggedInStudent.getRole().equals("Admin")) {
                tabbedPane.addTab("Student Data", createStudentPanel());
            }

            JPanel logoutPanel = new JPanel();
            JButton logoutButton = new JButton("Logout");
            logoutButton.addActionListener(e -> logout());
            logoutPanel.add(logoutButton);

            add(tabbedPane, BorderLayout.CENTER);
            add(logoutPanel, BorderLayout.SOUTH);
        }

        private JPanel createAdminPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            JTextArea adminInfo = new JTextArea();
            adminInfo.setEditable(false);
            adminInfo.append("Admin can manage users and books here.\n");
            panel.add(new JScrollPane(adminInfo));
            return panel;
        }
        // Now use bookTable in the createBookPanel method
    private JPanel createBookPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> filterBooks(searchField.getText()));

        searchPanel.add(new JLabel("Search Books: "));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        panel.add(searchPanel, BorderLayout.NORTH);

        // Book Table with filtering functionality
        String[] columns = {"ID", "Title", "Author", "Publisher", "Year", "Available"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        // Initialize the bookTable here (no need to redeclare)
        bookTable = new JTable(model);
        bookTable.setFillsViewportHeight(true);

        // Populate the table with initial book data
        updateBookTable(model);

        // Adding a click listener to select a book
        bookTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = bookTable.getSelectedRow();
                if (selectedRow != -1) {
                    int bookId = (int) bookTable.getValueAt(selectedRow, 0);
                    Book selectedBook = getBookById(bookId);
                    if (selectedBook != null && selectedBook.isAvailable()) {
                        int confirmation = JOptionPane.showConfirmDialog(
                            frame,
                            "Do you want to borrow the book: " + selectedBook.getTitle() + "?",
                            "Confirm Borrow",
                            JOptionPane.YES_NO_OPTION
                        );
                        if (confirmation == JOptionPane.YES_OPTION) {
                            selectedBook.borrowBook();
                            JOptionPane.showMessageDialog(frame, "Book borrowed successfully!");
                            updateBookTable(model);  // Update table after borrowing
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Book is not available.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // JScrollPane for book table
        JScrollPane scrollPane = new JScrollPane(bookTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Method to filter books based on search term
    private void filterBooks(String searchTerm) {
        DefaultTableModel model = (DefaultTableModel) bookTable.getModel();
        model.setRowCount(0); // Clear current table data
        ArrayList<Book> filteredBooks = new ArrayList<>();

        // Filter books by title, author, or publisher
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTerm.toLowerCase()) ||
                book.getAuthor().toLowerCase().contains(searchTerm.toLowerCase()) ||
                book.getPublisher().toLowerCase().contains(searchTerm.toLowerCase())) {
                filteredBooks.add(book);
            }
        }

        // Update table with filtered books
        for (Book book : filteredBooks) {
            model.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYear(), book.isAvailable()});
        }
    }

    // Utility method to update the table with all books
    private void updateBookTable(DefaultTableModel model) {
        model.setRowCount(0);  // Clear existing rows
        for (Book book : books) {
            model.addRow(new Object[]{book.getId(), book.getTitle(), book.getAuthor(), book.getPublisher(), book.getYear(), book.isAvailable()});
        }
    }

    // Utility method to get a book by ID
    private Book getBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }
}

        private JPanel createBorrowPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextArea borrowArea = new JTextArea();
            borrowArea.setEditable(false);
            for (Book book : books) {
                if (book.isAvailable()) borrowArea.append(book.toString() + "\n");
            }
            JButton borrowButton = new JButton("Borrow Selected Book");
            borrowButton.setBackground(new Color(40, 167, 69));
            borrowButton.setForeground(Color.WHITE);
            borrowButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Book borrowed successfully!"));

            panel.add(new JScrollPane(borrowArea));
            panel.add(borrowButton);

            return panel;
        }

        private JPanel createReturnBookPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextArea returnArea = new JTextArea();
            returnArea.setEditable(false);
            for (Book book : books) {
                if (!book.isAvailable()) returnArea.append(book.toString() + "\n");
            }

            JButton returnButton = new JButton("Return Selected Book");
            returnButton.setBackground(new Color(220, 53, 69));
            returnButton.setForeground(Color.WHITE);
            returnButton.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Book returned successfully!"));

            panel.add(new JScrollPane(returnArea));
            panel.add(returnButton);

            return panel;
        }

        private JPanel createStudentPanel() {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

            JTextArea studentListArea = new JTextArea();
            studentListArea.setEditable(false);
            for (Student student : students) {
                studentListArea.append(student.toString() + "\n");
            }

            panel.add(new JScrollPane(studentListArea));
            return panel;
        }

        private void logout() {
            loggedInStudent = null;
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new SplashScreen());
            frame.revalidate();
            frame.repaint();
        }
    }

