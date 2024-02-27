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
     * Removes a contest from the list of contests.
     *
     * @param contestId The ID of the contest to be removed.
     * @return
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
     * Invites a user to a private contest, adding them to the allowed users if the contest is private and exists.
     *
     * @param contestId The ID of the contest to invite the user to.
     * @param userId    The ID of the user to be invited.
     * @throws ContestNotExistException If the contest does not exist.
     * @throws UserBadTypeException     If the user is not correct.
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

    @GetMapping(value ="/get")
    public ResponseEntity<?> getContest(){
        return new ResponseEntity<>(contestList.findAll(), HttpStatus.OK);
    }

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
