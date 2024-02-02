package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.ArrayList;
import java.util.List;

/**
 * The  ContentController class manages the addition and validation of content,
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link Multimedia}, {@link BaseUser}, {@link IUserPlatform}, and {@link UserRole}.
 */
public class MultimediaController {

    /**
     * The list of content.
     */
    List<Multimedia> contentList;

    /**
     * Constructs a new {@code MultimediaController} with empty content lists.
     */
    public MultimediaController() {
        this.contentList = new ArrayList<Multimedia>();
    }

    /**
     * Adds content to the appropriate list based on the user's role.
     *
     * @param content the content to be added
     */
    public void addContent(Multimedia content) {
        IUserPlatform user = content.getAuthor();
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
    private void addContentNoPending(Multimedia content) {
        content.setValidation(true);
        this.contentList.add(content);
    }

    /**
     * Adds content to the list of content pending approval.
     *
     * @param content the content to be added
     */
    private void addContentPending(Multimedia content) {
        content.setValidation(false);
        this.contentList.add(content);
    }

    /**
     * Validates content based on the curator's choice (approve or reject).
     *
     * @param user    the curator making the decision
     * @param choice  {@code true} to approve the content, {@code false} to reject
     * @param content the content to be validated
     */
    public void validateContent(IUserPlatform user, boolean choice, Multimedia content) {
        if (user.getUserType().equals(UserRole.Curator)) {
            int index = contentList.indexOf(content);
            if(index !=1) {
                if (choice) {
                    this.contentList.get(index).setValidation(true);
                } else {
                    this.contentList.remove(content);
                }
            }
        }
    }

    /**
     * Gets the size of the approved content list.
     *
     * @return the size of the approved content list
     */
    public int getContentListSize() {
        return contentList.size();
    }

}
