package com.socialnetwork.connecthub.backend.model;

import com.socialnetwork.connecthub.backend.service.java.JavaUserAccountService;
import com.socialnetwork.connecthub.shared.dto.ContentDTO;
import lombok.Data;

import java.util.Date;

@Data
public class GroupNotification extends Notification {
    Group group;
    ContentDTO contentDTO;

    GroupNotification(Group group) {
        this.group = group;
        this.message = "You were approved to join group " + group.getName();
        this.read = false;
        this.timestamp = new Date();
    }

    GroupNotification(Group group, ContentDTO contentDTO) {
        this.group = group;
        this.contentDTO = contentDTO;
        this.message = "New Post in " + group.getName() + " by " + JavaUserAccountService.getInstance().getUserById(contentDTO.getAuthorId()).getUsername();
        this.read = false;
        this.timestamp = new Date();
    }
}
