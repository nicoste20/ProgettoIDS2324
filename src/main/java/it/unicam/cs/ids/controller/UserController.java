package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.UserAlreadyInException;
import it.unicam.cs.ids.Exception.UserNotExistException;
import it.unicam.cs.ids.controller.Repository.UserRepository;
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
    @PostMapping("/addCurator{userEmail}")
    public ResponseEntity<Object> addCurator(@RequestBody String managerEmail, @PathParam("userEmail") String email) {
        if(userRepository.findByEmail(email)!=0 && userRepository.findById(userRepository.selectByEmail(managerEmail)).get().getUserType().equals(UserRole.PlatformManager)){
            BaseUser x = getUserByEmail(email);
            x.setRole(UserRole.Curator);
            userRepository.save(x);
            return new ResponseEntity<>("Curator added", HttpStatus.OK);
        }else throw new UserAlreadyInException();
    }

    /**
     * Adds a user as an animator.
     * @param managerEmail The email of the manager authorizing the action.
     * @param email        The email of the user to be added as an animator.
     * @return A ResponseEntity with the appropriate message and status code.
     */
    @PostMapping("/addAnimator{userEmail}")
    public ResponseEntity<Object> addAnimator(@RequestBody String managerEmail, @PathParam("userEmail") String email) {
        if(userRepository.findByEmail(email)!=0 && userRepository.findById(userRepository.selectByEmail(managerEmail)).get().getUserType().equals(UserRole.PlatformManager)){
            BaseUser x = getUserByEmail(email);
            x.setRole(UserRole.Animator);
            userRepository.save(x);
            return new ResponseEntity<>("Animator added", HttpStatus.OK);
        }else throw new UserAlreadyInException();
    }

    /**
     * Changes the role of a user to a contributor authorized.
     * @param managerEmail The email of the manager authorizing the action.
     * @param email        The email of the user whose role is to be changed.
     */
    @PostMapping("/updateContributor{contributorEmail}")
    public void changeRole(@RequestBody String managerEmail, @PathParam("contributorEmail") String email) {
        if(userRepository.findByEmail(email)!=0 && userRepository.findById(userRepository.selectByEmail(managerEmail)).get().getUserType().equals(UserRole.PlatformManager)){
            BaseUser x = getUserByEmail(email);
            x.setRole(UserRole.ContributorAuthorized);
            userRepository.save(x);
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
    public ResponseEntity<Object> addUser(@RequestBody BaseUser user){
        if(userRepository.findByEmail(user.getEmail())==0){
            userRepository.save(user);
            return new ResponseEntity<>("User created", HttpStatus.OK);
        }else throw new UserAlreadyInException();
    }


    /**
     * Deletes a user.
     * @param email The email of the user to be deleted.
     * @return A ResponseEntity with the appropriate message and status code.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteUser(@RequestBody String email){
        if(userRepository.findByEmail(email)!=0){
            int id= userRepository.selectByEmail(email);
            userRepository.deleteById(id);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }else throw new UserNotExistException();
    }

    /**
     * Return the user instance by the unique email
     * @param email the email of the user
     * @return the user corresponding to the email given
     */
    private BaseUser getUserByEmail(String email){
        int id= userRepository.selectByEmail(email);
        BaseUser x = userRepository.findById(id).get();
        return x;
    }
}
