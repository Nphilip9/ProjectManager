package com.nphilip.UI;

import com.nphilip.manager.ItemListManager;
import com.nphilip.models.ProjectItem;
import com.nphilip.utils.Utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.File;

public class AddNewProject {

    private static final ItemListManager<ProjectItem> itemListManager = new ItemListManager<>();
    static JList<ProjectItem> itemJList = new JList<>(itemListManager.getListModel());

    public static void addNewProject() {
        JFrame mainFrame = new JFrame("Project Manager");
        mainFrame.setSize(990, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel projectNameInfoLabel = new JLabel("Project Name: ");
        projectNameInfoLabel.setBounds(10, 0, 100, 25);

        JTextField projectNameInput = new JTextField("unnamed", 17);
        projectNameInput.setBounds(115, 0, 180, 25);

        JLabel projectPathInfoLabel = new JLabel("Project Location: ");
        projectPathInfoLabel.setBounds(10, 40, 100, 25);

        JTextField projectPathInput = new JTextField(System.getProperty("user.home"));
        projectPathInput.setBounds(115, 40, 180, 25);

        JLabel finalProjectPathInfoLabel = new JLabel("Project will be created in: " + System.getProperty("user.home") + "\\" + projectNameInput.getText());
        finalProjectPathInfoLabel.setBounds(115, 65, 1000, 20);

        Font font = new Font("Arial", Font.PLAIN, 12);
        finalProjectPathInfoLabel.setFont(font);

        Color greyColor = Color.GRAY;
        finalProjectPathInfoLabel.setForeground(greyColor);

        JButton selectProjectLocation = new JButton("Select Path");
        selectProjectLocation.setBounds(300, 40, 100, 25);

        JButton createProject = new JButton("Create Project");

        int frameWidth = mainFrame.getWidth();
        int x = frameWidth / 2;

        createProject.setBounds(x - 80, 220, 150, 35);

        JScrollPane listScrollPane = new JScrollPane(itemJList);
        listScrollPane.setBounds(475, 0, 460, 200);
        itemListManager.addItem(new ProjectItem("Title", "Subtitle"));

        itemJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ProjectItem item) {
                    String title = item.getTitle();
                    String subtitle = item.getSubtitle();

                    // Use a different font with a smaller size for the subtitle
                    Font subtitleFont = new Font("Arial", Font.PLAIN, 12); // Adjust the size as needed
                    setFont(subtitleFont);

                    // Display the title and subtitle
                    setText("<html><b>" + title + "</b><br><font color='gray'>" + subtitle + "</font></html>");
                }
                return this;
            }
        });

        selectProjectLocation.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = chooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                projectPathInput.setText(selectedFile.getAbsolutePath());
            }
        });

        createProject.addActionListener(e -> createProject(projectNameInput.getText(), projectPathInput.getText(), mainFrame));

        projectNameInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                finalProjectPathInfoLabel.setText("Project will be created in: " + Utils.shortenPath(projectPathInput.getText() + "\\" + projectNameInput.getText(), 38));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                finalProjectPathInfoLabel.setText("Project will be created in: " + Utils.shortenPath(projectPathInput.getText() + "\\" + projectNameInput.getText(), 38));
            }

            @Override
            public void changedUpdate(DocumentEvent e) { /* Ignore */ }
        });

        projectPathInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                finalProjectPathInfoLabel.setText("Project will be created in: " + Utils.shortenPath(projectPathInput.getText() + "\\" + projectNameInput.getText(), 38));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                finalProjectPathInfoLabel.setText("Project will be created in: " + Utils.shortenPath(projectPathInput.getText() + "\\" + projectNameInput.getText(), 38));
            }

            @Override
            public void changedUpdate(DocumentEvent e) { /* Ignore */ }
        });

        mainPanel.add(projectNameInfoLabel);
        mainPanel.add(projectNameInput);
        mainPanel.add(projectPathInfoLabel);
        mainPanel.add(projectPathInput);
        mainPanel.add(finalProjectPathInfoLabel);
        mainPanel.add(selectProjectLocation);
        mainPanel.add(createProject);
        mainPanel.add(listScrollPane);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private static void createProject(String name, String location, JFrame mainFrame) {
        String path = location + "\\" + name;
        boolean file = new File(path).mkdir();
        if(file) {
            JOptionPane.showMessageDialog(mainFrame, "Project created!");
            itemListManager.addItem(new ProjectItem(name, path));
            itemJList.updateUI();
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Project not created!");
        }
    }
}