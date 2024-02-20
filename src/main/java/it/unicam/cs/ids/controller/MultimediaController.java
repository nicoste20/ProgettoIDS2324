package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.MultimediaNotFoundException;
import it.unicam.cs.ids.Exception.UserBadTypeException;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Content;
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
     * @throws UserBadTypeException if the user's role is not correct
     */
    @PostMapping("/add/multimedia{userId}")
    public ResponseEntity<Object> addContent(@RequestBody Multimedia content,@PathParam(("userId"))int userId) {
        BaseUser user = users.findById(userId).get();
        content.setAuthor(userId);
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))) {
            if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                addContentNoPending(content,userId);
            } else {
                addContentPending(content,userId);
            }
            return new ResponseEntity<>("Multimedia created", HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    /**
     * Adds content to the list of approved content.
     *
     * @param content the content to be added
     */
    public void addContentNoPending(Multimedia content,int userId) {
        content.setValidation(true);
        contentList.save(content);
    }

    /**
     * Adds content to the list of content pending approval.
     *
     * @param content the content to be added
     */
    private void addContentPending(Multimedia content,int userId) {
        content.setValidation(false);
        contentList.save(content);
    }

    /**
     * Validates content based on the curator's choice (approve or reject).
     *
     * @param userId    the curator making the decision
     * @param choice  {@code true} to approve the content, {@code false} to reject
     * @param id      the ID of the content to be validated
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */

    @RequestMapping(value="/validate/multimedia{choice}{id}{userId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> validateContent(@PathParam(("userId")) int userId, @PathParam(("choice")) boolean choice,
                                                  @PathParam(("id")) int id) {
        if (users.existsById(userId)) {
            BaseUser user = users.findById(userId).get();
            if (user.getUserType().equals(UserRole.Curator)){
                if (contentList.existsById(id)) {
                    if (choice) {
                        Multimedia multimedia = contentList.findById(id).get();
                        multimedia.setValidation(true);
                        contentList.save(multimedia);
                        return new ResponseEntity<>("Multimedia validated", HttpStatus.OK);
                    } else {
                        contentList.deleteById(id);
                        return new ResponseEntity<>("Multimedia eliminated", HttpStatus.OK);
                    }
                }else throw new MultimediaNotFoundException();
            }else throw new UserBadTypeException();
        }
        return new ResponseEntity<>("Multimedia nota validate", HttpStatus.NOT_FOUND);
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
     * @param description the new description
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */

    //TODO:MODIFICA
    @RequestMapping(value="/modify/multimedia/{description}/{id}", method = RequestMethod.PUT)
    public void modifyDesription(@PathParam("description") String description,@PathParam("id") int id){
        if(contentList.existsById(id)){
            int userId = contentList.findById(id).get().getAuthor();
            BaseUser user = users.findById(userId).get();
            if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))) {
                if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                    Multimedia x = contentList.findById(id).get();
                    x.setDescription(description);
                    contentList.save(x);
                } else {
                    Multimedia x = contentList.findById(id).get();
                    x.setDescription(description);
                    x.setValidation(false);
                    contentList.save(x);
                }
            }else throw new UserBadTypeException();
        }else throw new MultimediaNotFoundException();

    }

    /**
     * Delete a multimedia content
     * @param idUser the curator user
     * @param id the content to be removed
     * @return a ResponseEntity representing the status of the operation
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @DeleteMapping("/delete/multimedia{id}{userId}")
    public ResponseEntity<Object> deleteContent(@PathParam("userId") int idUser,@PathParam("id") int id){
        if(users.existsById(idUser)) {
            if (users.findById(idUser).get().getUserType().equals(UserRole.Curator)) {
                if (contentList.existsById(id)) {
                    contentList.deleteById(id);
                    return new ResponseEntity<>("Multimedia deleted", HttpStatus.OK);
                } else throw new MultimediaNotFoundException();
            } else throw new UserBadTypeException();
        }
        return new ResponseEntity<>("Multimedia not deleted", HttpStatus.NOT_FOUND);
    }

    /**
     * Reports a multimedia content
     * @param userId the user that is reporting the content
     * @param id      the ID of the content that the user wants to signal
     * @return a ResponseEntity representing the status of the operation
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @RequestMapping(value="/signal/multimedia{userId}{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> signalContent(@PathParam(("userId")) int userId,@PathParam(("id")) int id) {
        if (contentList.existsById(id)) {
            if(users.existsById(userId)){
                IUserPlatform user = users.findById(userId).get();
                if (!(user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.PlatformManager)
                        || user.getUserType().equals(UserRole.Animator))){
                    this.contentList.findById(id).get().setSignaled(true);
                    return new ResponseEntity<>("Multimedia signaled",HttpStatus.OK);
                }else throw new UserBadTypeException();
            }else throw new MultimediaNotFoundException();
        }else throw new UserBadTypeException();
    }

    @GetMapping(value ="/get/multimedias")
    public ResponseEntity<Object> getMultimedia(){
        return new ResponseEntity<>(contentList.findAll(), HttpStatus.OK);
    }
}
