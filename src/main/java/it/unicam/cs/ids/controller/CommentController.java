package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.CommentNotExistExcpetion;
import it.unicam.cs.ids.Exception.UserNotExistException;
import it.unicam.cs.ids.controller.Repository.CommentRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Comment;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The  Comment controller class manages the addition and validation of a general comment
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link BaseUser}, {@link UserRole}, {@link IUserPlatform}, {@link Comment}
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
public class CommentController {

    private final CommentRepository comments;

    private final UserRepository users;

    @Autowired
    public CommentController(CommentRepository comments, UserRepository users){
        this.comments = comments;
        this.users = users;
    }

    /**
     * Adds a comment
     * @param comment the comment that we want to add
     * @param userId the user that wants to add a comment
     */
    @PostMapping("/add/comment{userId}")
    public void addComment(@RequestBody Comment comment , @PathParam("userId") int userId) {
            BaseUser user = this.users.findById(userId).orElseThrow(UserNotExistException::new);
            comment.setAuthorId(userId);
            if (user.getUserType().equals(UserRole.Contributor) || user.getUserType().equals(UserRole.TouristAuthorized)) {
                    this.addWithPending(comment);
            }else {
                if((user.getUserType().equals(UserRole.ContributorAuthorized) || user.getUserType().equals(UserRole.Curator)))
                    this.addWithoutPending(comment);
            }
    }

    /**
     * Adds a comment with pending validation to the list.
     *
     * @param comment The comment to be added.
     */
    private void addWithPending(Comment comment) {
        comment.setValidation(false);
        this.comments.save(comment);
    }

    /**
     * Adds a comment without pending validation to the list.
     *
     * @param comment The comment to be added.
     */
    private void addWithoutPending(Comment comment) {
        comment.setValidation(true);
        this.comments.save(comment);
    }

    /**
     * Validates or removes a comment based on the user's choice.
     *
     * @param choice The user's choice for validation.
     * @param userId   The user performing the validation.
     * @param commentId  The Point to be validated or removed.
     */
    @PutMapping("/validate/comment{userId}{commentId}")
    public void validateComment(@RequestBody boolean choice,@PathParam("userId") int userId, @PathParam("commentId") int commentId) {
        BaseUser curator = this.users.findById(userId).orElseThrow(UserNotExistException::new);
        if(curator.getUserType().equals(UserRole.Curator)) {
            if (choice) {
                Comment comment = comments.findById(commentId).orElseThrow(CommentNotExistExcpetion::new);
                comment.setValidation(true);
                comments.save(comment);
            }else
                this.comments.deleteById(commentId);
        }
    }

    @GetMapping(value = "/get/comments")
    public ResponseEntity<Object> getComment(){
        return new ResponseEntity<>(comments.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/get/comments{contentId}")
    public ResponseEntity<Object> getComment(@PathParam("contentId") Integer contentId){
        List<Comment> commentsWithContentId = comments.findByContentId(contentId);
        return new ResponseEntity<>(commentsWithContentId, HttpStatus.OK);
    }

}

