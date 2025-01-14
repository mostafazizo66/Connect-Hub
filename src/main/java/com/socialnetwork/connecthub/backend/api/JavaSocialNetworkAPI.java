package com.socialnetwork.connecthub.backend.api;

import com.socialnetwork.connecthub.backend.interfaces.SocialNetworkAPI;
import com.socialnetwork.connecthub.backend.interfaces.services.*;
import com.socialnetwork.connecthub.backend.service.java.*;

public class JavaSocialNetworkAPI implements SocialNetworkAPI {
    private static JavaSocialNetworkAPI instance;

    private JavaSocialNetworkAPI() {
        // Private constructor to prevent instantiation
    }

    public static synchronized JavaSocialNetworkAPI getInstance() {
        if (instance == null) {
            instance = new JavaSocialNetworkAPI();
        }
        return instance;
    }


    @Override
    public ContentService getContentService() {
        return JavaContentService.getInstance();
    }

    @Override
    public FriendService getFriendService() {
        return JavaFriendService.getInstance();
    }

    @Override
    public NewsFeedService getNewsFeedService() {
        return JavaNewsFeedService.getInstance();
    }

    @Override
    public ProfileService getProfileService() {
        return JavaProfileService.getInstance();
    }

    @Override
    public UserAccountService getUserAccountService() {
        return JavaUserAccountService.getInstance();
    }

    @Override
    public GroupService getGroupService() {
        return JavaGroupService.getInstance();
    }
}
