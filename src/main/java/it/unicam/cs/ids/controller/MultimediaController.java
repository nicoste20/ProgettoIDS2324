package it.unicam.cs.ids.controller;


import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.ContestRespository;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.controller.Repository.PointRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Content;
import it.unicam.cs.ids.model.content.Contest;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

/**
 * The  ContentController class manages the addition and validation of content,
 * differentiating between immediate addition and pending approval based on the user's role.
 * It interacts with instances of {@link Multimedia}, {@link BaseUser}, {@link IUserPlatform}, and {@link UserRole}.
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/multimedia")
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
     * @return a ResponseEntity representing the status of the operation
     */
    @RequestMapping(value="/add{userId}{pointId}{name}{description}{path}" , method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPointMultimedia(@RequestParam ("file") MultipartFile file, @PathParam(("userId"))int userId,
     @PathParam(("pointId")) int pointId, @PathParam("name") String name ,
     @PathParam("description") String description,@PathParam("path") String path )
    {
        Multimedia multimedia = new Multimedia(name,description,path);
        BaseUser user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Point point = pointRepository.findById(pointId).orElseThrow(PointNotExistException::new);
        multimedia.setAuthor(user.getId());
        multimedia.setPointId(point.getId());
        if (!(user.getUserType().equals(UserRole.Tourist) || user.getUserType().equals(UserRole.PlatformManager))){
            if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)) {
                addContentNoPending(multimedia);
            } else {
                addContentPending(multimedia);
            }
            this.addFile(file, multimedia.getPath());
        }else throw new UserBadTypeException();
        return new ResponseEntity<>("Multimedia created", HttpStatus.OK);
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
     * @param multimediaId      the ID of the content to be validated
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */

    @RequestMapping(value="/validate{choice}{multimediaId}{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> validateContent(@PathParam(("userId")) int userId, @PathParam(("choice")) boolean choice,
    @PathParam(("multimediaId")) int multimediaId) {
        BaseUser user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        Multimedia multimedia = multimediaRepository.findById(multimediaId).orElseThrow(MultimediaNotFoundException::new);
        if (user.getUserType().equals(UserRole.Curator)){
            if (choice) {
                multimedia.setValidation(true);
                multimediaRepository.save(multimedia);
                return new ResponseEntity<>("Multimedia validated", HttpStatus.OK);
            }
            multimediaRepository.deleteById(multimediaId);
            return new ResponseEntity<>("Multimedia eliminated", HttpStatus.OK);
        }else throw new UserBadTypeException();
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

    @RequestMapping(value="/modify{description}{id}{userId}", method = RequestMethod.PUT)
    public ResponseEntity<?> modifyDescription(@PathParam("description") String description,@PathParam("id") int id,@PathParam("userId") int userId){
        Multimedia multimedia = this.multimediaRepository.findById(id).orElseThrow(MultimediaNotFoundException::new);
        BaseUser user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        if (multimedia.getAuthor() == userId) {
            if (user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.ContributorAuthorized)){
                multimedia.setDescription(description);
                multimediaRepository.save(multimedia);
                return new ResponseEntity<>("Multimedia modified", HttpStatus.OK);
            }
            multimedia.setDescription(description);
            multimedia.setValidation(false);
            multimediaRepository.save(multimedia);
        }else throw new UserBadTypeException();
        return new ResponseEntity<>("Multimedia not modified", HttpStatus.OK);
    }

    /**
     * Delete a multimedia content
     * @param userId the curator user
     * @param id the content to be removed
     * @return a ResponseEntity representing the status of the operation
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @DeleteMapping("/delete{id}{userId}")
    public ResponseEntity<?> deleteContent(@PathParam("userId") int userId,@PathParam("id") int id){
        Multimedia multimedia = multimediaRepository.findById(id).orElseThrow(MultimediaNotFoundException::new);
        if (userRepository.findById(userId).orElseThrow(UserNotExistException::new).getUserType().equals(UserRole.Curator)){
            for (Contest contest: contestRespository.findAll()) {
                if(contest.getMultimediaList().contains(id)){
                    contest.deleteMultimedia(id);
                }
            }
            multimediaRepository.delete(multimedia);
            return new ResponseEntity<>("Multimedia deleted", HttpStatus.OK);
        } else throw new UserBadTypeException();
    }

    /**
     * Reports a multimedia content
     * @param userId the user that is reporting the content
     * @param multimediaId      the ID of the content that the user wants to signal
     * @return a ResponseEntity representing the status of the operation
     * @throws UserBadTypeException if the user's role is not correct
     * @throws MultimediaNotFoundException if the multimedia content is not found
     */
    @RequestMapping(value="/signal{userId}{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> signalContent(@PathParam(("userId")) int userId,@PathParam(("id")) int multimediaId) {
        Multimedia multimedia = multimediaRepository.findById(multimediaId).orElseThrow(MultimediaNotFoundException::new);
        BaseUser user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        if (!(user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.PlatformManager)
                || user.getUserType().equals(UserRole.Animator))){
            multimedia.setSignaled(true);
            multimediaRepository.save(multimedia);
            return new ResponseEntity<>("Multimedia signaled",HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    @GetMapping(value ="/getAll")
    public ResponseEntity<?> getMultimedia(){
        return new ResponseEntity<>(multimediaRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(value ="/getAllAuthorized")
    public ResponseEntity<?> getAuthorizedMultimedia(){
        return new ResponseEntity<>(multimediaRepository.findAll().stream().filter(Content::isValidate), HttpStatus.OK);
    }

    /**
     * Adds multimedia to a contest with pending validation.
     *
     * @param multimedia The multimedia to be added.
     * @param contestId    The ID of the contest to which multimedia is added.
     */
    @RequestMapping(value="/add/contest{contestId}{userId}" , method=RequestMethod.POST, consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addWithPending(@RequestParam ("file") MultipartFile file, @RequestBody Multimedia multimedia,
    @PathParam(("contestId")) int contestId, @PathParam("userId") int userId) {
        Contest contest = contestRespository.findById(contestId).orElseThrow(ContestNotExistException::new);
        BaseUser user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        multimedia.setAuthor(user.getId());
        this.addContentPending(multimedia);
        contest.addMultimedia(multimedia.getId());
        contestRespository.save(contest);
        this.addFile(file, multimedia.getPath());
        return new ResponseEntity<>("Multimedia added", HttpStatus.OK);
    }


    private void addFile(MultipartFile file,String path){
        String finalPath = String.format("%s%s" ,"/src/main/resources/multimedia/",path);
        try {
            file.transferTo( new File(finalPath));
        } catch (Exception e){
            throw new FileException();
        }
    }

}

