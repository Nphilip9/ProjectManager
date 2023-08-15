package com.nphilip.UI;

import com.nphilip.utils.Utils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
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

        createProject.setBounds(x - 80, 100, 150, 35);

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

        createProject.addActionListener(e -> {
            createProject(projectPathInput.getText() + "\\" + projectNameInput.getText(), mainFrame);
        });

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

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private static void createProject(String path, JFrame mainFrame) {
        System.out.println(path);
        boolean file = new File(path).mkdir();
        if(file) {
            JOptionPane.showMessageDialog(mainFrame, "Project created!");
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Project not created!");
        }
    }
}