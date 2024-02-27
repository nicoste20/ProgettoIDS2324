package it.unicam.cs.ids.model.content;

import jakarta.persistence.*;

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
     */
    public Multimedia(String name , String description, String path) {
        super(name,description);
        this.path = path;
        this.signaled = false;
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
    public String getPath() {
        return this.path;
    }

    public void setPath(String path){
        this.path = path;
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

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

    public void addComment(int id){this.comments.add(id);}

    public List<Integer> getComments(){return this.comments;}

    public void deleteComment(int id){this.comments.remove(id);}

}
