package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {
    UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @PostMapping("/add/user/{managerId}")
    public void addCurator(@RequestBody BaseUser user, @PathParam("managerId") int managerId) {
        if(this.userRepository.existsById(managerId)){
            this.userRepository.save(user);
        }
    }

    @PostMapping("/contributor/update/{id}")
    public void changeRole(@PathParam("id") int id) {
        if(this.userRepository.existsById(id)){
            this.userRepository.findById(id).get().setRole(UserRole.ContributorAuthorized);
        }
    }
}
