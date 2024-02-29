package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.UserAlreadyInException;
import it.unicam.cs.ids.Exception.UserBadTypeException;
import it.unicam.cs.ids.Exception.UserNotExistException;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.observer.MultimediaListener;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Controller class for managing users.
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/users")
public class UserController {
    UserRepository userRepository;

    /**
     * Constructs a new UserController with the specified UserRepository.
     * @param userRepository The UserRepository to be used.
     */
    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Adds a user as a curator.
     * @param managerEmail The email of the manager authorizing the action.
     * @param email        The email of the user to be added as a curator.
     * @return A ResponseEntity with the appropriate message and status code.
     */
    @PostMapping("/addCurator{email}")
    public ResponseEntity<?> addCurator(@RequestBody String managerEmail, @PathParam("email") String email) {
        BaseUser manager = this.getUserByEmail(managerEmail);
        if(manager.getUserType().equals(UserRole.PlatformManager)){
            BaseUser user = getUserByEmail(email);
            user.setRole(UserRole.Curator);
            userRepository.save(user);
            return new ResponseEntity<>("Curator added", HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    /**
     * Adds a user as an animator.
     * @param managerEmail The email of the manager authorizing the action.
     * @param email        The email of the user to be added as an animator.
     * @return A ResponseEntity with the appropriate message and status code.
     */
    @PostMapping("/addAnimator{email}")
    public ResponseEntity<?> addAnimator(@RequestBody String managerEmail, @PathParam("email") String email) {
        BaseUser manager = this.getUserByEmail(managerEmail);
        if(manager.getUserType().equals(UserRole.PlatformManager)){
            BaseUser user = getUserByEmail(email);
            user.setRole(UserRole.Curator);
            userRepository.save(user);
            return new ResponseEntity<>("Animator added", HttpStatus.OK);
        }else throw new UserBadTypeException();
    }

    /**
     * Changes the role of a user to a contributor authorized.
     * @param managerEmail The email of the manager authorizing the action.
     * @param email        The email of the user whose role is to be changed.
     */
    @PostMapping("/updateContributor{contributorEmail}")
    public void changeRole(@RequestBody String managerEmail, @PathParam("contributorEmail") String email) {
        BaseUser manager = this.getUserByEmail(managerEmail);
        if(manager.getUserType().equals(UserRole.PlatformManager)){
            BaseUser user = getUserByEmail(email);
            user.setRole(UserRole.ContributorAuthorized);
            userRepository.save(user);
        }else throw new UserAlreadyInException();
    }

    /**
     * Retrieves all users.
     * @return A ResponseEntity containing a list of all users and the appropriate status code.
     */
    @GetMapping(value ="/getAll")
    public ResponseEntity<Object> getUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    /**
     * Adds a new user.
     * @param user The user to be added.
     * @return A ResponseEntity with the appropriate message and status code.
     */
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody BaseUser user){
        if(userRepository.countByEmail(user.getEmail()) == 0){
            userRepository.save(user);
            MultimediaController.listener.registerObserver(user);
            return new ResponseEntity<>("User created", HttpStatus.OK);
        }else throw new UserAlreadyInException();
    }


    /**
     * Deletes a user.
     * @param email The email of the user to be deleted.
     * @return A ResponseEntity with the appropriate message and status code.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@RequestBody String email){
        BaseUser user = this.getUserByEmail(email);
        this.userRepository.delete(user);
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    /**
     * Return the user instance by the unique email
     * @param email the email of the user
     * @return the user corresponding to the email given
     */
    private BaseUser getUserByEmail(String email){
       if(userRepository.countByEmail(email) == 0)
           throw new UserNotExistException();
       return userRepository.selectByEmail(email);
    }
}
