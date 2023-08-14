package com.nphilip.UI;

import javax.swing.*;
import java.io.File;

public class AddNewProject {
    public static void addNewProject() {
        JFrame mainFrame = new JFrame("Project Manager");
        mainFrame.setSize(500, 200);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel projectNameInfoLabel = new JLabel("Project Name: ");
        projectNameInfoLabel.setBounds(10, 0, 100, 25);

        JTextField projectNameInput = new JTextField(17);
        projectNameInput.setBounds(115, 0, 180, 25);

        JLabel projectPathInfoLabel = new JLabel("Project Location: ");
        projectPathInfoLabel.setBounds(10, 40, 100, 25);

        JTextField projectPathInput = new JTextField(17);
        projectPathInput.setBounds(115, 40, 180, 25);

        JButton selectProjectLocation = new JButton("Select Path");
        selectProjectLocation.setBounds(300, 40, 100, 25);

        selectProjectLocation.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = chooser.showOpenDialog(null);
            if (option == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                selectProjectLocation.setText(selectedFile.getAbsolutePath());
            }
        });

        mainPanel.add(projectNameInfoLabel);
        mainPanel.add(projectNameInput);
        mainPanel.add(projectPathInfoLabel);
        mainPanel.add(projectPathInput);
        mainPanel.add(selectProjectLocation);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }
}
