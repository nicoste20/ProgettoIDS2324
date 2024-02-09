package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
     * @param text  The text of a comment
     * @param author The author of a comment.
     * @param content The content associated with a comment.
     * @param id the id of the comment
     */
    public Comment(String text, BaseUser author, int id, Content content) {
        super(author, text, id);
        this.content = content;
    }

    public Comment() {
        super(null,null,-1);
    }

    /**
     *it gets the content of the comment
     */
    public Content getContent() {
        return this.content;
    }

}
