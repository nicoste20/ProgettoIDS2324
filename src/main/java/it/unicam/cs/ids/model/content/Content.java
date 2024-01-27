package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.comment.IComment;
import it.unicam.cs.ids.model.user.IUserPlatform;
/**
 * Represents a content item with user information, a photo, a description, and a list of comments.
 * Implements the  IContent interface.
 */
public class Content implements IContent{
    private IUserPlatform user;
    private String photo;
    private String description;
    private List<IComment> commentList;
    /**
     * Constructs a new Content object with the specified user, photo, and description.
     *
     * @param user        the user associated with the content
     * @param photo       the file path or URL of the photo
     * @param description the description of the content
     */
    public Content(IUserPlatform user, String photo, String description) {
        this.user = user;
        this.photo = photo;
        this.description = description;
    }
    /**
     * Gets the user associated with the content.
     *
     * @return the user associated with the content
     */
    @Override
    public IUserPlatform getUser() {
        return this.user;
    }

    /**
     * Gets the file in a string format of the photo associated with the content.
     *
     * @return the file in a string format
     */
    @Override
    public String getFile() {
        return this.photo;
    }

    /**
     * Adds a comment to the list of comments associated with the content.
     *
     * @param comment the comment to be added
     */
    @Override
    public void addComment(IComment comment) {
        commentList.add(comment);
    }

    /**
     * Gets the list of comments associated with the content.
     *
     * @return the list of comments
     */
    @Override
    public List<IComment> getComment() {
        return this.commentList;
    }

    /**
     * Gets the description of the content.
     *
     * @return the description of the content
     */
    @Override
    public String getDescription() {
        return this.description;
    }
}
