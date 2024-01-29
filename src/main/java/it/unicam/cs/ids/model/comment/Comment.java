package it.unicam.cs.ids.model.comment;

import it.unicam.cs.ids.model.Multimedia;
import it.unicam.cs.ids.model.user.IUserPlatform;

/**
 *  Represents a base implementation of the {@link IComment} interface.
 * This class provides methods that concerns about Comments.
 */
public class Comment implements IComment{
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
     */
    public Comment(String text, IUserPlatform author, Multimedia content, boolean commentValidation) {
        this.text = text;
        this.author = author;
        this.content = content;
        this.commentValidation = commentValidation;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public String getText() {
        return this.text;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public IUserPlatform getAuthor() {
        return this.author;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Multimedia getContent() {
        return this.content;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean commentValidation() {
        return this.commentValidation;
    }
}
