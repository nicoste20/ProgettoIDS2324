package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Represents a content item with user information, a photo, a description, and a list of comments.
 * Implements the  IContent interface.
 */
@Entity
public class Multimedia extends Content {
    @Column
    private boolean signaled;
    private String photo;

    /**
     * Constructs a new Content object with the specified user, photo, and description.
     *
     * @param photo       the file path or URL of the photo
     */
    public Multimedia(BaseUser author, String text , String photo, int id) {
        super(author,text, id);
        this.photo = photo;
        this.signaled =false;
    }

    public Multimedia() {
        super(null,null,-1);
    }

    /**
     * Gets the file in a string format of the photo associated with the content.
     *
     * @return the file in a string format
     */
    public String getFile() {
        return this.photo;
    }

    public boolean getSignaled() {
        return signaled;
    }

    public void setSignaled(boolean signaled) {
        this.signaled = signaled;
    }


}
