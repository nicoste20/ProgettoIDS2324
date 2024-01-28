package it.unicam.cs.ids.model;

import it.unicam.cs.ids.model.comment.IComment;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import java.util.List;

/**
 * Represents a content item with user information, a photo, a description, and a list of comments.
 * Implements the  IContent interface.
 */
public class Multimedia extends Content {
    private String photo;
    private List<IComment> commentList;
    /**
     * Constructs a new Content object with the specified user, photo, and description.
     *
     * @param user        the user associated with the content
     * @param photo       the file path or URL of the photo
     * @param description the description of the content
     */
    public Multimedia(BaseUser author, String text , IUserPlatform user, String photo, String description , int id) {
        super(author,text, id);
        this.photo = photo;
    }

    /**
     * Gets the file in a string format of the photo associated with the content.
     *
     * @return the file in a string format
     */
    public String getFile() {
        return this.photo;
    }

    /**
     * Adds a comment to the list of comments associated with the content.
     *
     * @param comment the comment to be added
     */
    public void addComment(IComment comment) {
        commentList.add(comment);
    }

    /**
     * Gets the list of comments associated with the content.
     *
     * @return the list of comments
     */
    public List<IComment> getComment() {
        return this.commentList;
    }

}
