package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.ContestRespository;
import it.unicam.cs.ids.controller.Repository.MultimediaRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Contest;
import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The ContestController class manages operations related to contests and multimedia within a platform.
 * This controller provides endpoints for adding, removing, inviting users, and managing multimedia in contests.
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/contest")
public class ContestController {

    private final ContestRespository contestList;
    private final UserRepository users;
    private final MultimediaRepository multimediaRepository;

    /**
     * Constructs a new ContestController with the specified repositories.
     *
     * @param contestList          The repository for contests.
     * @param users                The repository for users.
     * @param multimediaRepository The repository for multimedia.
     */
    @Autowired
    public ContestController(ContestRespository contestList, UserRepository users, MultimediaRepository multimediaRepository) {
        this.contestList = contestList;
        this.users = users;
        this.multimediaRepository = multimediaRepository;
    }

    /**
     * Adds a contest to the list of contests.
     *
     * @param contest The contest to be added.
     */
    @PostMapping("/add{userId}")
    public ResponseEntity<?> addContest(@RequestBody Contest contest, @PathParam(("userId")) int userId) {
        contest.setAuthor(userId);
        contest.setValidation(true);
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        if (user.getUserType().equals(UserRole.Animator)) {
            this.contestList.save(contest);
            return new ResponseEntity<>("Contest created", HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    /**
     * Removes a contest identified by its ID.
     * @param contestId The ID of the contest to be removed.
     * @param userId The ID of the user requesting the removal.
     * @return A ResponseEntity representing the status of the operation.
     * @throws ContestNotExistException if the contest does not exist.
     * @throws UserNotExistException if the user does not exist.
     * @throws UserBadTypeException if the user's role is not correct.
     */
    @DeleteMapping("/delete{userId}")
    public ResponseEntity<?> removeContest(@RequestBody int contestId, @PathParam(("userId")) int userId) {
        Contest contest = contestList.findById(contestId).orElseThrow(ContestNotExistException::new);
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        if (user.getUserType().equals(UserRole.Animator)) {
            this.contestList.delete(contest);
            return new ResponseEntity<>("Contest deleted", HttpStatus.OK);
        }else throw new UserBadTypeException();
    }


    /**
     * Invites a user to a contest, given the contest ID, user ID, and animator ID.
     * @param contestId The ID of the contest to which the user is invited.
     * @param userId The ID of the user being invited.
     * @param animatorId The ID of the animator inviting the user.
     * @return A ResponseEntity representing the status of the invitation.
     * @throws ContestNotExistException if the contest does not exist.
     * @throws UserNotExistException if the user or animator does not exist.
     * @throws UninvitableContestException if the contest cannot be invited to due to its privacy setting.
     * @throws UserBadTypeException if the animator's role is not correct.
     */
    @RequestMapping(value = "/invite{contestId}{userId}{animatorId}", method = RequestMethod.PUT)
    public ResponseEntity<?> invite(@PathParam(("contestId")) int contestId, @PathParam(("userId")) int userId, @PathParam(("animatorId")) int animatorId) {
        Contest contest = contestList.findById(contestId).orElseThrow(ContestNotExistException::new);
        BaseUser animator = users.findById(animatorId).orElseThrow(UserNotExistException::new);
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        if(animator.getUserType().equals(UserRole.Animator)){
            if (contest.getPrivacy()) {
                contest.addAllowedUsers(user.getId());
                contestList.save(contest);
                return new ResponseEntity<>("User invited", HttpStatus.OK);
            }else throw new UninvitableContestException();
        }else throw new UserBadTypeException();
    }


    /**
     * Validates multimedia in a contest by a curator user, based on a choice.
     *
     * @param multimediaId The ID of the multimedia to be validated.
     * @param contestId    The ID of the contest containing the multimedia.
     * @param animatorId   The ID of the user performing the validation.
     * @param choice       The choice for validation.
     */
    @RequestMapping(value = "/validateMultimedia{contestId}{multimediaId}{animatorId}{choice}", method = RequestMethod.PUT)
    public ResponseEntity<?> validateMultimedia(@PathParam(("multimediaId")) int multimediaId,
    @PathParam(("contestId")) int contestId, @PathParam(("animatorId")) int animatorId, @PathParam(("choice")) boolean choice)
    {
        BaseUser animator = users.findById(animatorId).orElseThrow(UserNotExistException::new);
        Contest contest = contestList.findById(contestId).orElseThrow(ContestNotExistException::new);
        Multimedia multimedia = multimediaRepository.findById(multimediaId).orElseThrow(MultimediaNotFoundException::new);

        if (!animator.getUserType().equals(UserRole.Animator) || !contest.getMultimediaList().contains(multimediaId))
            return new ResponseEntity<>("Multimedia not validated", HttpStatus.OK);
        if (choice) {
            multimedia.setValidation(true);
            multimediaRepository.save(multimedia);
            contestList.save(contest);
            return new ResponseEntity<>("multimedia validated", HttpStatus.OK);
        }
        multimediaRepository.delete(multimedia);
        return new ResponseEntity<>("Multimedia eliminated", HttpStatus.OK);
    }

    /**
     * Searches for a contest by its name.
     * @param contestName The name of the contest to search for.
     * @return An Optional containing the found contest, if any.
     */
    @RequestMapping(value = "/search{contestName}", method = RequestMethod.POST)
    public Optional<Contest> searchContest(@PathParam(("contestName")) String contestName) {
        return Optional.of(contestList.findAllByTitle(contestName));
    }
    /**
     * Retrieves all contests.
     * @return A ResponseEntity containing all contests.
     */
    @GetMapping(value ="/get")
    public ResponseEntity<?> getContest(){
        return new ResponseEntity<>(contestList.findAll(), HttpStatus.OK);
    }
    /**
     * Retrieves multimedia associated with a specific contest.
     * @param contestId The ID of the contest.
     * @return A ResponseEntity containing multimedia associated with the specified contest.
     * @throws ContestNotExistException if the contest does not exist.
     * @throws MultimediaNotFoundException if multimedia associated with the contest is not found.
     */
    @GetMapping(value ="/get{contestId}")
    public ResponseEntity<?> getMultimedia(@PathParam("contestId") int contestId){
        Contest contest = contestList.findById(contestId).orElseThrow(ContestNotExistException::new);
        List<Multimedia> multimedias = new ArrayList<Multimedia>();
        for (Integer id: contest.getMultimediaList()) {
            multimedias.add(multimediaRepository.findById(id).orElseThrow(MultimediaNotFoundException::new));
        }
        return new ResponseEntity<>(multimedias, HttpStatus.OK);
    }
}
