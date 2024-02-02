package it.unicam.cs.ids.model.comment;

import it.unicam.cs.ids.model.Content;
import it.unicam.cs.ids.model.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;

/**
 *  Represents a base implementation of the {@link IComment} interface.
 * This class provides methods that concerns about Comments.
 */
public class Comment extends Content {
    private String text;
    private IUserPlatform author;
    private Multimedia content;
    private boolean commentValidation;

    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param text  The text of a comment
     * @param author The author of a comment.
     * @param content The content associated with a comment.
     * @param commentValidation If the comment needs validation.
     * @param id the id of the comment
     */
    public Comment(String text, BaseUser author, int id, Multimedia content, boolean commentValidation) {
        super(author, text, id);
        this.content = content;
        this.commentValidation = commentValidation;
    }
    /**
     *it gets the content of the comment
     */
    public Multimedia getContent() {
        return this.content;
    }
    /**
     * it gets the validation of the comment
     */
    public boolean getCommentValidation() {
        return this.commentValidation;
    }
}
