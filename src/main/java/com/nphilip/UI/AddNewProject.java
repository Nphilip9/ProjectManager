package com.nphilip.UI;

import com.nphilip.manager.ItemListManager;
import com.nphilip.manager.JSONDataManager;
import com.nphilip.manager.RequestAndResponseManager;
import com.nphilip.models.ProjectItem;
import com.nphilip.models.RequestType;
import com.nphilip.server.Server;
import com.nphilip.utils.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Arrays;

public class AddNewProject {

    private static final ItemListManager<ProjectItem> itemListManager = new ItemListManager<>();
    static JList<ProjectItem> itemJList = new JList<>(itemListManager.getListModel());

    private static boolean checkBoxIsSelected = false;

    public static void addNewProject() {
        if (new JSONDataManager().loadDataFromJsonFile() != null) {
            itemListManager.addAllItems(new JSONDataManager().loadDataFromJsonFile());
        }

        JFrame mainFrame = new JFrame("Project Manager");
        mainFrame.setSize(990, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        JLabel projectNameInfoLabel = new JLabel("Project Name: ");
        projectNameInfoLabel.setBounds(10, 10, 100, 25);

        JTextField projectNameInput = new JTextField("unnamed", 17);
        projectNameInput.setBounds(115, 10, 180, 25);

        JLabel projectPathInfoLabel = new JLabel("Project Location: ");
        projectPathInfoLabel.setBounds(10, 50, 100, 25);

        JTextField projectPathInput = new JTextField(System.getProperty("user.home"));
        projectPathInput.setBounds(115, 50, 180, 25);

        JLabel finalProjectPathInfoLabel = new JLabel("Project will be created in: " + System.getProperty("user.home") + "\\" + projectNameInput.getText());
        finalProjectPathInfoLabel.setBounds(115, 75, 1000, 20);

        Font font = new Font("Arial", Font.PLAIN, 12);
        finalProjectPathInfoLabel.setFont(font);

        Color greyColor = Color.GRAY;
        finalProjectPathInfoLabel.setForeground(greyColor);

        JButton selectProjectLocation = new JButton("Select Path");
        selectProjectLocation.setBounds(300, 50, 100, 25);

        JLabel deleteHintLabel = new JLabel("Select item and press \"entf\" to delete it");
        deleteHintLabel.setBounds(475, 208, 250, 20); // Adjust the bounds as needed

        deleteHintLabel.setForeground(Color.GRAY);

        Font hintFont = new Font("Arial", Font.PLAIN, 12); // Adjust the size as needed
        deleteHintLabel.setFont(hintFont);

        JCheckBox addGitRepoCheckBox = new JCheckBox("Add Git Repo");
        addGitRepoCheckBox.setBounds(7, 90, 150, 20);

        JButton createProject = new JButton("Create Project");

        int frameWidth = mainFrame.getWidth();
        int x = frameWidth / 2;

        createProject.setBounds(x - 80, 230, 150, 35);

        JScrollPane listScrollPane = new JScrollPane(itemJList);
        listScrollPane.setBounds(475, 10, 460, 200);

        itemJList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    ProjectItem selectedProject = itemJList.getSelectedValue();
                    if (selectedProject != null) {
                        itemListManager.removeItem(selectedProject);
                        itemJList.updateUI();
                        new RequestAndResponseManager().handleRequest(RequestType.ITEM_DELETION, selectedProject);
                    }
                }
            }
        });

        addGitRepoCheckBox.addActionListener(e -> {
            checkBoxIsSelected = addGitRepoCheckBox.isSelected();
        });

        itemJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JPanel itemPanel = new JPanel(new BorderLayout());

                Border separatorBorder = BorderFactory.createMatteBorder(0, 0, 5, 0, Color.WHITE); // Smaller and gray
                itemPanel.setBorder(separatorBorder);

                JLabel titleLabel = new JLabel();
                titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
                titleLabel.setForeground(Color.BLACK);

                if (value instanceof ProjectItem item) {
                    String title = item.getTitle();
                    String subtitle = item.getSubtitle();
                    titleLabel.setText(title);

                    Font subtitleFont = new Font("Arial", Font.PLAIN, 12);
                    JLabel subtitleLabel = new JLabel(subtitle);
                    subtitleLabel.setFont(subtitleFont);
                    subtitleLabel.setForeground(Color.GRAY);

                    itemPanel.add(titleLabel, BorderLayout.NORTH);
                    itemPanel.add(subtitleLabel, BorderLayout.SOUTH);
                }

                if (isSelected) {
                    itemPanel.setBackground(Color.LIGHT_GRAY);
                } else {
                    itemPanel.setBackground(Color.WHITE);
                }

                return itemPanel;
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
        mainPanel.add(addGitRepoCheckBox);
        mainPanel.add(createProject);
        mainPanel.add(listScrollPane);
        mainPanel.add(deleteHintLabel);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    private static void createProject(String name, String location, JFrame mainFrame) {
        String path = location + "\\" + name;
        boolean file = new File(path).mkdir();
        if(file) {
            itemListManager.addItem(new ProjectItem(name, path, Utils.getCurrentDate()), checkBoxIsSelected);
            JOptionPane.showMessageDialog(mainFrame, "Project created!");
            itemJList.updateUI();
            new RequestAndResponseManager().handleRequest(RequestType.NEW_ITEM_CREATION, new ProjectItem(name, path, Utils.getCurrentDate()));
        } else {
            JOptionPane.showMessageDialog(mainFrame, "Project not created!");
        }
    }
}