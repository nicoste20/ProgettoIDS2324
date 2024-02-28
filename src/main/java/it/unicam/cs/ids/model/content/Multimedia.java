package it.unicam.cs.ids.model.content;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a multimedia content item with user information, a photo, a description, and a signaling status.
 * This class extends the Content class and inherits its properties.
 */
@Entity
public class Multimedia extends Content {
    @Column
    private boolean signaled;
    private String path;
    private Integer pointId;

    @ElementCollection
    @CollectionTable(name = "multimedia_comments", joinColumns = @JoinColumn(name = "multimedia_id"))
    @Column(name = "comment_id")
    private List<Integer> comments;

    /**
     * Constructs a new Multimedia object with the specified name, file path or URL, and description.
     *
     * @param name        The name of the multimedia content.
     * @param description The description of the multimedia content.
     * @param path        The file path or URL of the multimedia content.
     */
    public Multimedia(String name, String description, String path) {
        super(name, description);
        this.path = path;
        this.signaled = false;
        this.comments = new ArrayList<>();
    }

    /**
     * Default constructor.
     */
    public Multimedia() {
        super(null, "");
    }

    /**
     * Gets the file path of the photo associated with the content.
     *
     * @return The file path or URL.
     */
    public String getPath() {
        return this.path;
    }

    /**
     * Sets the file path or URL of the multimedia content.
     *
     * @param path The file path or URL to set.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Checks if the multimedia content has been signaled.
     *
     * @return True if the multimedia content has been signaled, false otherwise.
     */
    public boolean getSignaled() {
        return signaled;
    }

    /**
     * Sets the signaling status of the multimedia content.
     *
     * @param signaled The signaling status to be set.
     */
    public void setSignaled(boolean signaled) {
        this.signaled = signaled;
    }

    /**
     * Retrieves the ID of the point associated with the multimedia content.
     *
     * @return The ID of the associated point.
     */
    public Integer getPointId() {
        return pointId;
    }

    /**
     * Sets the ID of the point associated with the multimedia content.
     *
     * @param pointId The ID of the point to set.
     */
    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    /**
     * Adds a comment to the multimedia content.
     *
     * @param id The ID of the comment to be added.
     */
    public void addComment(int id) {
        this.comments.add(id);
    }

    /**
     * Retrieves the list of comments associated with the multimedia content.
     *
     * @return The list of comments associated with the multimedia content.
     */
    public List<Integer> getComments() {
        return this.comments;
    }

    /**
     * Deletes a comment from the multimedia content.
     *
     * @param id The ID of the comment to be deleted.
     */
    public void deleteComment(int id) {
        this.comments.remove(id);
    }

}
