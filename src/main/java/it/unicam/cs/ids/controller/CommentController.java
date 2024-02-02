package it.unicam.cs.ids.controller;
import it.unicam.cs.ids.model.comment.Comment;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;

import java.util.List;
import java.util.Optional;
/**
 * The  Comment controller class manages the addition and validation of a general comment
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link BaseUser}, {@link UserRole}, {@link IUserPlatform}, {@link Comment}
 */
public class CommentController {
    private List<Comment> comments;

    /**
     * Adds a comment
     * @param comment the comment that we want to add
     * @param user the user that wants to add a comment
     */
    public void addPointComment(Comment comment , IUserPlatform user) {
        if (user.getUserType().equals(UserRole.Contributor) || user.getUserType().equals(UserRole.TouristAuthorized)) {
            if((user.getUserType().equals(UserRole.ContributorAuthorized) || user.getUserType().equals(UserRole.Curator)))
                this.addWithoutPending(comment);
            else
                this.addWithPending(comment);
        }
    }

    /**
     * Adds a comment with pending validation to the list.
     *
     * @param comment The comment to be added.
     */
    private void addWithPending(Comment comment) {
        comment.setValidation(false);
        this.comments.add(comment);
    }

    /**
     * Adds a comment without pending validation to the list.
     *
     * @param comment The comment to be added.
     */
    private void addWithoutPending(Comment comment) {
        comment.setValidation(true);
        this.comments.add(comment);
    }

    /**
     * Validates or removes a comment based on the user's choice.
     *
     * @param choice The user's choice for validation.
     * @param user   The user performing the validation.
     * @param comment  The Point to be validated or removed.
     */
    public void validateComment(boolean choice, IUserPlatform user, Comment comment) {
        int index = this.comments.indexOf(comment);
        Comment commentToValidate = this.comments.get(index);
        if (choice)
            commentToValidate.setValidation(true);
        else
            this.comments.remove(commentToValidate);
    }

 /*
     * Searches for a Point by its title in the list.
     *
     * @param description The description of a comment to be searched.
     * @return An Optional containing the found comment, or empty if not found.

    public Optional<Comment> searchPoint(String description) {
        for (Comment comment : comments) {
            if (comment.getDescription().equals(description))
                return Optional.of(comment);
        }
        return Optional.empty();
    }*/
}

