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
    private String file;

    /**
     * Constructs a new Content object with the specified user, photo, and description.
     *
     * @param file the file path or URL of the photo
     */
    public Multimedia(String name , String file, String description) {
        super(name,description);
        this.file = file;
        this.signaled =false;
    }

    public Multimedia() {
        super(null,"");
    }

    /**
     * Gets the file in a string format of the photo associated with the content.
     *
     * @return the file in a string format
     */
    public String getFile() {
        return this.file;
    }

    public boolean getSignaled() {
        return signaled;
    }

    public void setSignaled(boolean signaled) {
        this.signaled = signaled;
    }


}
