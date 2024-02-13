package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.MultimediaNotFoundException;
import it.unicam.cs.ids.Exception.UserNotCorrectException;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The  ContentController class manages the addition and validation of content,
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link Multimedia}, {@link BaseUser}, {@link IUserPlatform}, and {@link UserRole}.
 */
@RestController
public class MultimediaController {

    /**
     * The list of content.
     */
    private final MultimediaRepository contentList;

    private final UserRepository users;

    /**
     * Constructs a new {@code MultimediaController} with empty content lists.
     *
     * @param contentList the repository for multimedia content
     * @param users
     */
    @Autowired
    public MultimediaController(MultimediaRepository contentList, UserRepository users) {
        this.contentList = contentList;
        this.users = users;
    }
    /**
     * Adds content to the appropriate list based on the user's role.
     *
     * @param content the content to be added
     * @return a ResponseEntity representing the status of the operation
     * @throws UserNotCorrectException if the user's role is not correct
     */
    @PostMapping("/add/multimedia")
    public ResponseEntity<Object> addContent(@RequestBody Multimedia content) {
        IUserPlatform user = content.getAuthor();
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))) {
            if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                addContentNoPending(content);
            } else {
                addContentPending(content);
            }
            return new ResponseEntity<>("Multimedia created", HttpStatus.OK);
        }else throw new UserNotCorrectException();
    }

    /**
     * Adds content to the list of approved content.
     *
     * @param content the content to be added
     */
    public void addContentNoPending(Multimedia content) {
        content.setValidation(true);
        content.getAuthor().incrementPostCount();
        contentList.save(content);
    }

    /**
     * Adds content to the list of content pending approval.
     *
     * @param content the content to be added
     */
    private void addContentPending(Multimedia content) {
        content.setValidation(false);
        contentList.save(content);
    }

    /**
     * Validates content based on the curator's choice (approve or reject).
     *
     * @param userId    the curator making the decision
     * @param choice  {@code true} to approve the content, {@code false} to reject
     * @param id      the ID of the content to be validated
     * @throws UserNotCorrectException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @RequestMapping(value="/multimedia/{choice}/{id}/{userId}", method = RequestMethod.PUT)
    public void validateContent(@PathParam(("userId")) int userId, @PathParam(("choice")) boolean choice, @PathParam(("id")) int id) {
        if (users.existsById(userId)) {
            IUserPlatform user = users.findById(userId).get();
            if (user.equals(UserRole.Curator)){
                if (contentList.existsById(id)) {
                    if (choice) {
                        contentList.findById(id).get().setValidation(true);
                        contentList.findById(id).get().getAuthor().incrementPostCount();
                    } else {
                        contentList.deleteById(id);
                    }
                }else throw new MultimediaNotFoundException();
            }else throw new UserNotCorrectException();
        }
    }

    /**
     * Gets the size of the approved content list.
     *
     * @return the size of the approved content list
     */
    public long getContentListSize() {
        return contentList.count();
    }

    /**
     * Update the description of a multimedia content
     * @param text the new description
     * @throws UserNotCorrectException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @RequestMapping(value="/multimedia/{text}/{id}", method = RequestMethod.PUT)
    public void modifyDesription(@PathParam("text") String text,@PathParam("id") int id){
        if(contentList.existsById(id)){
            IUserPlatform user = contentList.findById(id).get().getAuthor();
            if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))) {
                if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                    this.contentList.findById(id).get().setDescription(text);
                } else {
                    this.contentList.findById(id).get().setDescription(text);
                    this.contentList.findById(id).get().setValidation(false);
                }
            }else throw new UserNotCorrectException();
        }else throw new MultimediaNotFoundException();

    }

    /**
     * Delete a multimedia content
     * @param user the curator user
     * @param id the content to be removed
     * @return a ResponseEntity representing the status of the operation
     * @throws UserNotCorrectException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @DeleteMapping("/delete/multimedia")
    public ResponseEntity<Object> deleteContent(@RequestBody int idUser,@RequestBody int id){
        if (users.findById(idUser).get().getUserType().equals(UserRole.Curator)) {
            if(contentList.existsById(id)){
                contentList.deleteById(id);
                return new ResponseEntity<>("Multimedia deleted",HttpStatus.OK);
            }else throw new MultimediaNotFoundException();
        }else throw new UserNotCorrectException();
    }

    /**
     * Reports a multimedia content
     * @param userId the user that is reporting the content
     * @param id      the ID of the content that the user wants to signal
     * @return a ResponseEntity representing the status of the operation
     * @throws UserNotCorrectException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @RequestMapping(value="/multimedia/{userId}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> signalContent(@PathParam(("userId")) int userId,@PathParam(("id")) int id) {
        if (contentList.existsById(id)) {
            if(users.existsById(userId)){
                IUserPlatform user = users.findById(userId).get();
                if (!(user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.PlatformManager)
                        || user.getUserType().equals(UserRole.Animator))){
                    this.contentList.findById(id).get().setSignaled(true);
                    return new ResponseEntity<>("Multimedia signaled",HttpStatus.OK);
                }else throw new UserNotCorrectException();
            }else throw new MultimediaNotFoundException();
        }else throw new UserNotCorrectException();
    }
}
