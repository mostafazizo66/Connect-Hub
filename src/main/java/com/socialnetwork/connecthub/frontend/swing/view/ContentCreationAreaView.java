package com.socialnetwork.connecthub.frontend.swing.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.util.Date;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.interfaces.services.ContentService;
import com.socialnetwork.connecthub.backend.persistence.json.JsonPostRepository;
import com.socialnetwork.connecthub.frontend.swing.components.JLabel;
import com.socialnetwork.connecthub.frontend.swing.components.JButton;
import com.socialnetwork.connecthub.frontend.swing.constants.GUIConstants;
import com.socialnetwork.connecthub.frontend.swing.navigationhandler.NavigationHandlerFactory;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import com.socialnetwork.connecthub.shared.dto.GroupDTO;
import com.socialnetwork.connecthub.shared.dto.UserDTO;
import com.socialnetwork.connecthub.shared.exceptions.ContentCreationException;

public class ContentCreationAreaView extends JFrame {
    private JTextArea postTextArea;
    private JPanel imagePanel;
    private JButton addImageButton;
    private JButton submitButton;
    private JLabel titleLabel;
    private File selectedImageFile;
    private String placeholderText = "Write your text here...";
    private boolean isPost;
    private String type;
    private GroupDTO group;
    private ContentDTO contentToEdit;
    private UserDTO currentUser;
    private SocialNetworkAPI socialNetworkAPI;

    public ContentCreationAreaView(SocialNetworkAPI socialNetworkAPI, UserDTO currentUser, boolean isPost) {
        init(socialNetworkAPI, currentUser, isPost);
    }

    public ContentCreationAreaView(SocialNetworkAPI socialNetworkAPI, UserDTO currentUser, GroupDTO group) {
        groupPostInit(socialNetworkAPI, currentUser, group, null);
    }

    public ContentCreationAreaView(SocialNetworkAPI socialNetworkAPI, UserDTO currentUser, GroupDTO group, ContentDTO contentToEdit) {
        groupPostInit(socialNetworkAPI, currentUser, group, contentToEdit);
    }

    private void groupPostInit(SocialNetworkAPI socialNetworkAPI, UserDTO currentUser,GroupDTO group, ContentDTO contentToEdit) {
        this.group = group;
        this.socialNetworkAPI = socialNetworkAPI;
        this.currentUser = currentUser;
        this.contentToEdit = contentToEdit;
        // Set up the frame
        setTitle("Create a New Post");
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setBackground(new Color(255, 255, 255));
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        postTextArea = new JTextArea();
        postTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        postTextArea.setPreferredSize(new Dimension(600, 100));
        postTextArea.setWrapStyleWord(true);
        postTextArea.setLineWrap(true);
        postTextArea.setText(placeholderText);
        postTextArea.setForeground(Color.GRAY);

        if (contentToEdit != null && contentToEdit.getContent() != null && !contentToEdit.getContent().isEmpty()) {
            postTextArea.setText(contentToEdit.getContent());
            postTextArea.setForeground(Color.BLACK);
        }

        postTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (postTextArea.getText().equals(placeholderText)) {
                    postTextArea.setText("");
                    postTextArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (postTextArea.getText().isEmpty()) {
                    postTextArea.setText(placeholderText);
                    postTextArea.setForeground(Color.GRAY);
                }
            }
        });

        addImageButton = new JButton("Add Image", 12, 14);
        addImageButton.setPreferredSize(new Dimension(300, 40));
        submitButton = new JButton("Submit Post", 12, 14);
        submitButton.setPreferredSize(new Dimension(300, 40));
        titleLabel = new JLabel("Create a Post", 12, GUIConstants.blue, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        if (contentToEdit != null && contentToEdit.getImagePath() != null && !contentToEdit.getImagePath().isEmpty()) {
            selectedImageFile = new File(contentToEdit.getImagePath());
            updateImageLabels();
        }


        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImageFileChooser();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitPost(true);
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(postTextArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(addImageButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setPreferredSize(new Dimension(800, 1000));
        contentPanel.add(scrollPane);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(submitButton);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void init(SocialNetworkAPI socialNetworkAPI, UserDTO currentUser, boolean isPost) {
        this.socialNetworkAPI = socialNetworkAPI;
        this.currentUser = currentUser;
        this.isPost = isPost;
        this.type = (isPost? "Post" : "Story");
        // Set up the frame
        setTitle("Create a New " + type);
        setSize(1500, 800);
        setLocationRelativeTo(null);
        setBackground(new Color(255, 255, 255));
        setResizable(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        postTextArea = new JTextArea();
        postTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        postTextArea.setPreferredSize(new Dimension(600, 100));
        postTextArea.setWrapStyleWord(true);
        postTextArea.setLineWrap(true);
        postTextArea.setText(placeholderText);
        postTextArea.setForeground(Color.GRAY);
        if (contentToEdit != null && contentToEdit.getContent() != null && !contentToEdit.getContent().isEmpty()) {
            postTextArea.setText(contentToEdit.getContent());
            postTextArea.setForeground(Color.BLACK);
        }
        postTextArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (postTextArea.getText().equals(placeholderText)) {
                    postTextArea.setText("");
                    postTextArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (postTextArea.getText().isEmpty()) {
                    postTextArea.setText(placeholderText);
                    postTextArea.setForeground(Color.GRAY);
                }
            }
        });

        addImageButton = new JButton("Add Image", 12, 14);
        addImageButton.setPreferredSize(new Dimension(300, 40));
        submitButton = new JButton("Submit " + type, 12, 14);
        submitButton.setPreferredSize(new Dimension(300, 40));
        titleLabel = new JLabel("Create a " + type, 12, GUIConstants.blue, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        imagePanel = new JPanel();
        imagePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        if (contentToEdit != null && contentToEdit.getImagePath() != null && !contentToEdit.getImagePath().isEmpty()) {
            selectedImageFile = new File(contentToEdit.getImagePath());
            updateImageLabels();
        }


        addImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openImageFileChooser();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitPost(false);
            }
        });

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(postTextArea);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(addImageButton);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JScrollPane scrollPane = new JScrollPane(imagePanel);
        scrollPane.setPreferredSize(new Dimension(800, 1000));
        contentPanel.add(scrollPane);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        contentPanel.add(submitButton);

        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void openImageFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            updateImageLabels();
        }
    }

    private void updateImageLabels() {
        imagePanel.removeAll();

        if (selectedImageFile != null) {
            ImageIcon icon = new ImageIcon(selectedImageFile.getAbsolutePath());

            Image image = icon.getImage();
            int width = 800;
            int height = 1000;

            double aspectRatio = (double) image.getWidth(null) / image.getHeight(null);
            if (aspectRatio > 1) {
                height = (int) (width / aspectRatio);
            } else {
                width = (int) (height * aspectRatio);
            }


            Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            icon = new ImageIcon(resizedImage);


            javax.swing.JLabel imageLabel = new javax.swing.JLabel(icon);
            imageLabel.setPreferredSize(new Dimension(width, height));
            imagePanel.add(imageLabel);
        }

        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void submitPost(boolean isGroupPost) {
        String content = postTextArea.getText().trim();
        String imagePath = selectedImageFile != null ? selectedImageFile.getAbsolutePath() : null;
        ContentDTO contentDTO = new ContentDTO(currentUser.getUserId(), content, imagePath, new Date());


        if(isGroupPost) {
            try {

                if (contentToEdit == null) {
                    socialNetworkAPI.getGroupService().submitPost(group.getGroupId(), currentUser.getUserId(), contentDTO);

                } else {
                    contentToEdit.setContent(content);
                    contentToEdit.setImagePath(imagePath);
                    socialNetworkAPI.getGroupService().editPost(group.getGroupId(), currentUser.getUserId(), contentToEdit);
                }


                // Reset UI
                JOptionPane.showMessageDialog(this, "Post submitted successfully!");
                this.dispose();
                postTextArea.setText(placeholderText);
                postTextArea.setForeground(Color.GRAY);
                selectedImageFile = null;
                updateImageLabels();


            } catch (ContentCreationException ex) {
                new Alert(ex.getMessage(), ContentCreationAreaView.this);
            }
        }
        else
        {

            // Call the content service
            try {
                if (isPost)
                    socialNetworkAPI.getContentService().createPost(currentUser.getUserId(), contentDTO);
                else
                    socialNetworkAPI.getContentService().createStory(currentUser.getUserId(), contentDTO);


                // Reset UI
                JOptionPane.showMessageDialog(this, type + " submitted successfully!");
                this.dispose();
                postTextArea.setText(placeholderText);
                postTextArea.setForeground(Color.GRAY);
                selectedImageFile = null;
                updateImageLabels();

            } catch (ContentCreationException ex) {
                new Alert(ex.getMessage(), ContentCreationAreaView.this);
            }
        }
    }
}