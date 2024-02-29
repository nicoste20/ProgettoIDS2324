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
import it.unicam.cs.ids.model.observer.MultimediaListener;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

/**
 * The MultimediaController class manages the addition and validation of multimedia,
 * differentiating between immediate addition and pending approval based on the user's role.
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/multimedia")
public class MultimediaController {

    private final MultimediaRepository multimediaRepository;
    private final UserRepository userRepository;
    private final ContestRespository contestRespository;
    private final PointRepository pointRepository;
    public static MultimediaListener listener;

    /**
     * Constructs a new {@code MultimediaController} with empty content lists.
     *
     * @param multimediaRepository the repository for multimedia content
     * @param userRepository       the repository of users
     * @param contestRepository    the repository of contest
     * @param pointRepository  the repository of points
     */
    @Autowired
    public MultimediaController(MultimediaRepository multimediaRepository, UserRepository userRepository, ContestRespository contestRepository, PointRepository pointRepository) {
        this.multimediaRepository = multimediaRepository;
        this.userRepository = userRepository;
        this.contestRespository = contestRepository;
        this.pointRepository = pointRepository;
        listener = new MultimediaListener();
    }

    /**
     * Adds multimedia content to the list based on the user's role.
     *
     * @param file        the multimedia file to be uploaded
     * @param name        the name of the multimedia
     * @param description the description of the multimedia
     * @param path        the path of the multimedia
     * @param userId      the ID of the user adding the multimedia
     * @param pointId     the ID of the point associated with the multimedia
     * @return a ResponseEntity representing the status of the operation
     * @throws UserNotExistException     if the user does not exist
     * @throws PointNotExistException    if the associated point does not exist
     * @throws UserBadTypeException      if the user's role is incorrect
     */
    @RequestMapping(value="/add" , method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addPointMultimedia(@RequestParam("file") MultipartFile file,
                                                @RequestParam("name") String name,
                                                @RequestParam("description") String description,
                                                @RequestParam("path") String path,
                                                @RequestParam("userId") Integer userId,
                                                @RequestParam("pointId") Integer pointId)
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
            this.addFile(file, path);
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
        listener.notifyObservers("Multimedia: " + content.getName());
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
     * @param id the id of the content
     * @param userId the userId
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
    @RequestMapping(value="/signal{userId}{multimediaId}", method = RequestMethod.PUT)
    public ResponseEntity<?> signalContent(@PathParam(("userId")) int userId,@PathParam(("multimediaId")) int multimediaId) {
        Multimedia multimedia = multimediaRepository.findById(multimediaId).orElseThrow(MultimediaNotFoundException::new);
        BaseUser user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        if (!(user.getUserType().equals(UserRole.Curator) || user.getUserType().equals(UserRole.PlatformManager)
                || user.getUserType().equals(UserRole.Animator))){
            multimedia.setSignaled(true);
            multimediaRepository.save(multimedia);
            return new ResponseEntity<>("Multimedia signaled",HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    /**
     * Retrieves all multimedia items.
     * @return A ResponseEntity containing all multimedia items.
     */
    @GetMapping(value ="/getAll")
    public ResponseEntity<?> getMultimedia(){
        return new ResponseEntity<>(multimediaRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Retrieves all authorized multimedia items.
     * @return A ResponseEntity containing all authorized multimedia items.
     */
    @GetMapping(value ="/getAllAuthorized")
    public ResponseEntity<?> getAuthorizedMultimedia(){
        return new ResponseEntity<>(multimediaRepository.findAll().stream().filter(Content::isValidate), HttpStatus.OK);
    }


    /**
     * Adds multimedia to a contest with pending validation.
     * @param file The multimedia file.
     * @param name The name of the multimedia.
     * @param description The description of the multimedia.
     * @param path The path where the multimedia will be stored.
     * @param userId The ID of the user adding the multimedia.
     * @param contestId The ID of the contest to which the multimedia is added.
     * @return A ResponseEntity representing the status of the operation.
     * @throws ContestNotExistException if the contest does not exist.
     * @throws UserNotExistException if the user does not exist.
     */
    @RequestMapping(value="/add/contest" , method=RequestMethod.POST, consumes= MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addWithPending(@RequestParam("file") MultipartFile file,
                                                     @RequestParam("name") String name,
                                                      @RequestParam("description") String description,
                                                      @RequestParam("path") String path,
                                                      @RequestParam("userId") Integer userId,
                                                      @RequestParam("contestId") Integer contestId) {
        Multimedia multimedia = new Multimedia(name,description,path);
        Contest contest = contestRespository.findById(contestId).orElseThrow(ContestNotExistException::new);
        BaseUser user = userRepository.findById(userId).orElseThrow(UserNotExistException::new);
        multimedia.setAuthor(user.getId());
        this.addContentPending(multimedia);
        contest.addMultimedia(multimedia.getId());
        contestRespository.save(contest);
        this.addFile(file, multimedia.getPath());
        return new ResponseEntity<>("Multimedia added", HttpStatus.OK);
    }

    /**
     *     Configuration of the upload directory path through application.properties
     */
    @Value("${upload.directory}")
    private String uploadDirectory;

    /**
     * Adds a file to the specified path.
     * @param file The file to be added.
     * @param path The path where the file will be added.
     * @throws FileException if an error occurs during file transfer.
     */
    private void addFile(MultipartFile file,String path){
        String projectDirectory = System.getProperty("user.dir");
        String finalPath = projectDirectory + File.separator + uploadDirectory + File.separator + path+ ".jpg";
        try {
            file.transferTo(new File(finalPath));
        } catch (IOException e) {
            throw new FileException();
        }
    }

}

