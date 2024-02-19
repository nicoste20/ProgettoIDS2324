package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.BaseUser;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;

/**
 *  Represents a base implementation of the {@link Content} interface.
 * This class provides methods that concerns about Comments.
 */
@Entity
public class Comment extends Content {
    @OneToOne(targetEntity = Content.class)
    private Content content;

    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param name  The text of a comment
     * @param content The content associated with a comment.
     */
    public Comment(String name,Content content, String description) {
        super(name,description);
        this.content = content;
    }

    public Comment() {
        super(null, null);
    }

    /**
     *it gets the content of the comment
     */
    public Content getContent() {
        return this.content;
    }

}
