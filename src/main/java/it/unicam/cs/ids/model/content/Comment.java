package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.*;

/**
 *  Represents a base implementation of the {@link Content} interface.
 * This class provides methods that concerns about Comments.
 */
@Entity
public class Comment{

    private boolean isValidate;

    private int contentId;

    private int authorId;

    private String comment;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param comment  The text of a comment
     * @param contentId The content associated with a comment.
     */
    public Comment(int contentId, String comment) {
        this.contentId = contentId;
        this.isValidate = false;
        this.comment = comment;
    }

    public Comment() {
        this.isValidate = false;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isValidate() {
        return isValidate;
    }

    public void setValidation(boolean validate) {
        isValidate = validate;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
