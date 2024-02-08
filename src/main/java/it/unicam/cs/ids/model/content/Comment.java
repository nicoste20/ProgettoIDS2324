package it.unicam.cs.ids.model.content;

import it.unicam.cs.ids.model.user.IUserPlatform;

/**
 *  Represents a base implementation of the {@link Content} interface.
 * This class provides methods that concerns about Comments.
 */
public class Comment extends Content {
    private final Content content;

    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param text  The text of a comment
     * @param author The author of a comment.
     * @param content The content associated with a comment.
     * @param id the id of the comment
     */
    public Comment(String text, IUserPlatform author, int id, Content content) {
        super(author, text, id);
        this.content = content;
    }
    /**
     *it gets the content of the comment
     */
    public Content getContent() {
        return this.content;
    }

}
