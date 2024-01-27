package it.unicam.cs.ids.model.comment;

import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.model.user.UserBuilder;

/**
 *  Represents a base implementation of the {@link IComment} interface.
 * This class provides methods that concerns about Comments.
 */
public class Comment implements IComment{
    private String text;
    private UserBuilder author;
    private Content content;
    private boolean commentValidation;

    /**
     * Constructs a new Contest object with the specified parameters.
     *
     * @param text  The text of a comment
     * @param author The author of a comment.
     * @param content The content associated with a comment.
     * @param commentValidation If the comment needs validation.
     */
    public Comment(String text, UserBuilder author, Content content, boolean commentValidation) {
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
    public UserBuilder getAuthor() {
        return this.author;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Content getContent() {
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
