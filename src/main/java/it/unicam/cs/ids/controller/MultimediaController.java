package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.MultimediaNotFoundException;
import it.unicam.cs.ids.Exception.UserNotCorrectException;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
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



    /**
     * Constructs a new {@code MultimediaController} with empty content lists.
     */
    @Autowired
    public MultimediaController(MultimediaRepository contentList) {
        this.contentList = contentList;
    }
    /**
     * Adds content to the appropriate list based on the user's role.
     *
     * @param content the content to be added
     */
    @PostMapping("/multimediaAdd")
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

        //this.contentList.add(content);
    }

    /**
     * Adds content to the list of content pending approval.
     *
     * @param content the content to be added
     */
    private void addContentPending(Multimedia content) {
        content.setValidation(false);
        contentList.save(content);
        //this.contentList.add(content);
    }

    /**
     * Validates content based on the curator's choice (approve or reject).
     *
     * @param user    the curator making the decision
     * @param choice  {@code true} to approve the content, {@code false} to reject
     * @param content the content to be validated
     */
    @PutMapping("/multimedia")
    public void validateContent(IUserPlatform user, @PostMapping boolean choice,@PostMapping Multimedia content) {
        if (user.getUserType().equals(UserRole.Curator)) {
            if (contentList.existsById(content.getId())) {
                if (choice) {
                    contentList.findById(content.getId()).get().setValidation(true);
                    contentList.findById(content.getId()).get().getAuthor().incrementPostCount();
                   // this.contentList.get(index).setValidation(true);
                    // this.contentList.get(index).getAuthor().incrementPostCount();
                } else {
                    contentList.deleteById(content.getId());
                }
            }else throw new MultimediaNotFoundException();
        }else throw new UserNotCorrectException();
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
     * @param content the content of which to change the description
     */
    @PutMapping("/multimedia")
    public void modifyDesription(String text, Multimedia content){
        if(this.contentList.contains(content)){
            int index = this.contentList.indexOf(content);
            IUserPlatform user = content.getAuthor();
            if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))) {
                if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                    this.contentList.get(index).setDescription(text);
                } else {
                    this.contentList.get(index).setDescription(text);
                    this.contentList.get(index).setValidation(false);
                }
            }
        }

    }

    /**
     * Delete a multimedia content
     * @param user the curator user
     * @param content the content to be removed
     */
    @DeleteMapping("/multimedia")
    public ResponseEntity<Object> deleteContent(@RequestBody IUserPlatform user,@RequestBody Multimedia content){
        if (user.getUserType().equals(UserRole.Curator)) {
            if(contentList.existsById(content.getId())){
                contentList.deleteById(content.getId());
                return new ResponseEntity<>("Multimedia deleted",HttpStatus.OK);
            }else throw new MultimediaNotFoundException();
        }else throw new UserNotCorrectException();
    }

    /**
     * Reports a multimedia content
     * @param user the user that is reporting the content
     * @param content the content that the user want to signal
     */
    public void signalContent(IUserPlatform user, Multimedia content) {
        if (contentList.contains(content)) {
            int index = contentList.indexOf(content);
            if (!(user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.PlatformManager) || user.getUserType().equals(UserRole.Animator))){
                this.contentList.get(index).setSignaled(true);
            }
        }
    }


}
