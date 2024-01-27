package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.content.IContent;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.List;

/**
 * The  ContentController class manages the addition and validation of content,
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link IContent}, {@link BaseUser}, {@link IUserPlatform}, and {@link UserRole}.
 */
public class ContentController {
    /**
     * The list of approved content.
     */
    List<IContent> contentList;

    /**
     * The list of content pending approval.
     */
    List<IContent> pendingContentList;

    /**
     * Adds content to the appropriate list based on the user's role.
     *
     * @param content the content to be added
     */
    public void addContent(IContent content) {
        IUserPlatform user = content.getUser();
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))) {
            if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                addContentNoPending(content);
            } else {
                addContentPending(content);
            }
        }
    }

    /**
     * Adds content to the list of approved content.
     *
     * @param content the content to be added
     */
    private void addContentNoPending(IContent content) {
        this.contentList.add(content);
    }

    /**
     * Adds content to the list of content pending approval.
     *
     * @param content the content to be added
     */
    private void addContentPending(IContent content) {
        this.pendingContentList.add(content);
    }

    /**
     * Validates content based on the curator's choice (approve or reject).
     *
     * @param choice  {@code true} to approve the content, {@code false} to reject
     * @param content the content to be validated
     */
    private void validateContent(boolean choice, IContent content) {
        if (choice)
            this.contentList.add(content);
        else
            this.pendingContentList.remove(content);
    }
}
