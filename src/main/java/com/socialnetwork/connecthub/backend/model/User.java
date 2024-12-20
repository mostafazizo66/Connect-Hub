package com.socialnetwork.connecthub.backend.model;

import lombok.Data;

import javax.management.Notification;
import java.util.Date;
import java.util.List;


@Data
public class User {
    private String userId;
    private String email;
    private String username;
    private String hashedPassword;
    private Date dateOfBirth;
    private boolean onlineStatus;
    private String profilePhotoPath;
    private String coverPhotoPath;
    private String bio;
    private List<String> friends;
    private List<String> posts;
    private List<String> stories;
    private List<String> groups;
    private List<com.socialnetwork.connecthub.backend.model.Notification> notifications;
}