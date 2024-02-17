package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.UserAlreadyInException;
import it.unicam.cs.ids.Exception.UserNotInException;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/addCurator/user{userEmail}")
    public ResponseEntity<Object> addCurator(@RequestBody String managerEmail, @PathParam("userEmail") String email) {
        if(userRepository.findByEmail(email)!=0 && userRepository.findById(userRepository.selectByEmail(managerEmail)).get().getUserType().equals(UserRole.PlatformManager)){

            int id= userRepository.selectByEmail(email);
            BaseUser x = userRepository.findById(id).get();
            x.setRole(UserRole.Curator);
            userRepository.save(x);
            return new ResponseEntity<>("Curator added", HttpStatus.OK);
        }else throw new UserAlreadyInException();
    }

    @PostMapping("/addAnimator/user{userEmail}")
    public ResponseEntity<Object> addAnimator(@RequestBody String managerEmail, @PathParam("userEmail") String email) {
        if(userRepository.findByEmail(email)!=0 && userRepository.findById(userRepository.selectByEmail(managerEmail)).get().getUserType().equals(UserRole.PlatformManager)){
            int id= userRepository.selectByEmail(email);
            BaseUser x = userRepository.findById(id).get();
            x.setRole(UserRole.Animator);
            userRepository.save(x);
            return new ResponseEntity<>("Animator added", HttpStatus.OK);
        }else throw new UserAlreadyInException();
    }

    @PostMapping("/updateContributor/user{contributorEmail}")
    public void changeRole(@RequestBody String managerEmail, @PathParam("contributorEmail") String email) {
        if(userRepository.findByEmail(email)!=0 && userRepository.findById(userRepository.selectByEmail(managerEmail)).get().getUserType().equals(UserRole.PlatformManager)){
            int id= userRepository.selectByEmail(email);
            userRepository.findById(id).get().setRole(UserRole.ContributorAuthorized);
        }else throw new UserAlreadyInException();
    }

    @GetMapping(value ="/get/users")
    public ResponseEntity<Object> getUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add/user")
    public ResponseEntity<Object> addUser(@RequestBody BaseUser user){
        if(userRepository.findByEmail(user.getEmail())==0){
            userRepository.save(user);
            return new ResponseEntity<>("User created", HttpStatus.OK);
        }else throw new UserAlreadyInException();
    }

    @DeleteMapping("/delete/user")
    public ResponseEntity<Object> deleteUser(@RequestBody String email){
        if(userRepository.findByEmail(email)!=0){
            int id= userRepository.selectByEmail(email);
            userRepository.deleteById(id);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        }else throw new UserNotInException();
    }

}
