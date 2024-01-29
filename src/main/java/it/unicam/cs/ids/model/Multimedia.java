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
    private final String photo;
    /**
     * Constructs a new Content object with the specified user, photo, and description.
     *
     * @param photo       the file path or URL of the photo
     */
    public Multimedia(BaseUser author, String text , String photo, int id) {
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

}
