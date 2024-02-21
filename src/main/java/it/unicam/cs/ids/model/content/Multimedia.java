package it.unicam.cs.ids.model.content;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/**
 * Represents a multimedia content item with user information, a photo, a description, and a signaling status.
 * This class extends the Content class and inherits its properties.
 */
@Entity
public class Multimedia extends Content {
    @Column
    private boolean signaled;
    private String file;

    /**
     * Constructs a new Multimedia object with the specified name, file path or URL, and description.
     *
     * @param name        The name of the multimedia content.
     * @param file        The file path or URL of the photo associated with the content.
     * @param description The description of the multimedia content.
     */
    public Multimedia(String name , String file, String description) {
        super(name,description);
        this.file = file;
        this.signaled =false;
    }

    /**
     * Default constructor.
     */
    public Multimedia() {
        super(null,"");
    }

    /**
     * Gets the file path of the photo associated with the content.
     * @return the file path
     */
    public String getFile() {
        return this.file;
    }

    /**
     * Checks if the multimedia content has been signaled.
     * @return True if the multimedia content has been signaled, false otherwise.
     */
    public boolean getSignaled() {
        return signaled;
    }

    /**
     * Sets the signaling status of the multimedia content.
     * @param signaled signaled the signaling status to be set.
     */
    public void setSignaled(boolean signaled) {
        this.signaled = signaled;
    }


}
