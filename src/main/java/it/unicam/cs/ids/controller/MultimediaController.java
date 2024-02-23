package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.ContestRespository;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.controller.Repository.PointRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Contest;
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
    private final MultimediaRepository multimediaRepository;

    private final UserRepository userRepository;

    private final ContestRespository contestRespository;

    private final PointRepository pointRepository;

    /**
     * Constructs a new {@code MultimediaController} with empty content lists.
     *
     * @param multimediaRepository the repository for multimedia content
     * @param userRepository       the repository of users
     * @param contestRepository    the repository of contest
     * @param pointRepository
     */
    @Autowired
    public MultimediaController(MultimediaRepository multimediaRepository, UserRepository userRepository, ContestRespository contestRepository, PointRepository pointRepository) {
        this.multimediaRepository = multimediaRepository;
        this.userRepository = userRepository;
        this.contestRespository = contestRepository;
        this.pointRepository = pointRepository;
    }
    /**
     * Adds content to the appropriate list based on the user's role.
     *
     * @param content the content to be added
     * @return a ResponseEntity representing the status of the operation
     * @throws UserBadTypeException if the user's role is not correct
     */
    @PostMapping("/add/multimedia{userId}{pointId}")
    public ResponseEntity<Object> addContent(@RequestBody Multimedia content,@PathParam(("userId"))int userId, @PathParam(("pointId")) Integer pointId) {
        if(userRepository.findById(userId).isEmpty())
           throw new UserNotExistException();
        BaseUser user = userRepository.findById(userId).get();
        content.setAuthor(userId);
        if(pointId != null){
            if(pointRepository.existsById(pointId))
                content.setPointId(pointId);
            else
                throw new PointNotExistException();
        }
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))) {
            if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                addContentNoPending(content);
            } else {
                addContentPending(content);
            }
            return new ResponseEntity<>("Multimedia created", HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    /**
     * Adds content to the list of approved content.
     *
     * @param content the content to be added
     */
    public void addContentNoPending(Multimedia content) {
        content.setValidation(true);
        multimediaRepository.save(content);
    }

    /**
     * Adds content to the list of content pending approval.
     *
     * @param content the content to be added
     */
    private void addContentPending(Multimedia content) {
        content.setValidation(false);
        multimediaRepository.save(content);
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
        if (userRepository.existsById(userId)) {
            BaseUser user = userRepository.findById(userId).get();
            if (user.getUserType().equals(UserRole.Curator)){
                if (multimediaRepository.existsById(id)) {
                    if (choice) {
                        Multimedia multimedia = multimediaRepository.findById(id).get();
                        multimedia.setValidation(true);
                        multimediaRepository.save(multimedia);
                        return new ResponseEntity<>("Multimedia validated", HttpStatus.OK);
                    } else {
                        multimediaRepository.deleteById(id);
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
        return multimediaRepository.count();
    }

    /**
     * Update the description of a multimedia content
     * @param description the new description
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */

    @RequestMapping(value="/modify/multimedia/{description}{id}{userId}", method = RequestMethod.PUT)
    public void modifyDescription(@PathParam("description") String description,@PathParam("id") int id,@PathParam("userId") int userId){
        if(multimediaRepository.findById(id).isPresent()){
            int author = multimediaRepository.findById(id).get().getAuthor();
            if(userRepository.findById(userId).isPresent())
                throw new UserNotExistException();
            BaseUser user = userRepository.findById(userId).get();
            if (author == userId) {
                if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                    Multimedia multimedia = multimediaRepository.findById(id).get();
                    multimedia.setDescription(description);
                    multimediaRepository.save(multimedia);
                } else {
                    Multimedia multimedia = multimediaRepository.findById(id).get();
                    multimedia.setDescription(description);
                    multimedia.setValidation(false);
                    multimediaRepository.save(multimedia);
                }
            }else throw new UserBadTypeException();
        }else throw new MultimediaNotFoundException();

    }

    /**
     * Delete a multimedia content
     * @param userId the curator user
     * @param id the content to be removed
     * @return a ResponseEntity representing the status of the operation
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @DeleteMapping("/delete/multimedia{id}{userId}")
    public ResponseEntity<Object> deleteContent(@PathParam("userId") int userId,@PathParam("id") int id){
        if(userRepository.existsById(userId)) {
            if (userRepository.findById(userId).get().getUserType().equals(UserRole.Curator)) {
                if (multimediaRepository.existsById(id)) {
                    multimediaRepository.deleteById(id);
                    for (Contest contest: contestRespository.findAll()) {
                        if(contest.getMultimediaList().contains(id)){
                            contest.deleteMultimedia(id);
                        }
                    }
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
        if (multimediaRepository.existsById(id)) {
            if(userRepository.existsById(userId)){
                IUserPlatform user = userRepository.findById(userId).get();
                if (!(user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.PlatformManager)
                        || user.getUserType().equals(UserRole.Animator))){
                    Multimedia x = multimediaRepository.findById(id).get();
                    x.setSignaled(true);
                    multimediaRepository.save(x);
                    return new ResponseEntity<>("Multimedia signaled",HttpStatus.OK);
                }else throw new UserBadTypeException();
            }else throw new MultimediaNotFoundException();
        }else throw new UserBadTypeException();
    }

    @GetMapping(value ="/get/multimedias")
    public ResponseEntity<Object> getMultimedia(){
        return new ResponseEntity<>(multimediaRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Adds multimedia to a contest with pending validation.
     *
     * @param multimedia The multimedia to be added.
     * @param contestId    The ID of the contest to which multimedia is added.
     * @throws MultimediaNotFoundException If the multimedia is not found.
     * @throws ContestNotExistException    If the contest does not exist.
     */
    @RequestMapping(value = "/addMultimediaPending/contest{contestId}{userId}", method = RequestMethod.POST)
    public ResponseEntity<Object> addWithPending(@RequestBody Multimedia multimedia, @PathParam(("contestId")) int contestId, @PathParam("userId") int userId)
    {
        if (contestRespository.existsById(contestId)) {
            multimedia.setValidation(false);
            this.addContent(multimedia,userId,null);
            int multimediaId = multimedia.getId();
            Contest contest = contestRespository.findById(contestId).get();
            contest.addMultimedia(multimediaId);
            contestRespository.save(contest);
            return new ResponseEntity<>("Multimedia added", HttpStatus.OK);
        }else throw new ContestNotExistException();
    }
}
