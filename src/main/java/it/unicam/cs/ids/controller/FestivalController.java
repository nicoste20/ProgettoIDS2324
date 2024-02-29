package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.FestivalRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Festival;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * The FestivalController class manages the addition and removal of festivals.
 */
@CrossOrigin(origins = "http://localhost:63342")
@RestController
@RequestMapping("/festivals")
public class FestivalController {
    private final FestivalRepository festivals;
    private final UserRepository users;

    /**
     * Constructs a new FestivalController with the specified repositories.
     * @param festivals The repository for Festival objects.
     * @param users     The repository for User objects.
     */
    @Autowired
    public FestivalController(FestivalRepository festivals, UserRepository users) {
        this.festivals = festivals;
        this.users = users;
    }

    /**
     * Adds a festival.
     * @param newfestival The festival to be added.
     * @param userId      The ID of the user performing the action.
     * @return A response entity indicating the success of the operation.
     */
    @PostMapping("/add{userId}")
    public ResponseEntity<?> addFestival(@RequestBody Festival newfestival, @PathParam(("userId")) Integer userId){
        newfestival.setAuthor(userId);
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        Date endDate = newfestival.getEndDate();
        Date startDate = newfestival.getStartDate();
        if(user.getUserType().equals(UserRole.Curator)) {
            if (endDate.compareTo(new Date()) >= 0 && endDate.after(startDate)) {
                if (festivals.countFestivalsWithName(newfestival.getName()) == 0) {
                    festivals.save(newfestival);
                    return new ResponseEntity<>("Festival created", HttpStatus.OK);
                } else throw new FestivalAlreadyInException();
            }else throw new BadDateException();
        }else throw new UserBadTypeException();
    }

    /**
     * Removes a festival.
     * @param title  The title of the festival to remove.
     * @param userId The ID of the user performing the action.
     * @return A response entity indicating the success of the operation.
     */
    @RequestMapping(value = "/delete{title}{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> removeFestival(@PathParam(("title")) String title, @PathParam(("userId")) int userId){
        BaseUser user = users.findById(userId).orElseThrow(UserNotExistException::new);
        if(user.getUserType().equals(UserRole.Curator)) {
            if (festivals.countFestivalsWithName(title) > 0) {
                festivals.deleteById(festivals.findFestivalIdByDescription(title));
                return new ResponseEntity<>("Festival cancelled", HttpStatus.OK);
            }
            throw new FestivalNotFoundException();
        }else throw new UserBadTypeException();
    }

    /**
     * Gets if the festival is active
     * @param text The text description of the festival.
     * @return true if the Festival is active, false otherwise.
     */
    private boolean isActive(String text){
        if(festivals.countFestivalsWithName(text) > 0){
            int id = festivals.findFestivalIdByDescription(text);
            return festivals.findById(id).get().getEndDate().after(new Date());
        } throw new FestivalNotFoundException();
    }

    /**
     * Retrieves all festivals.
     * @return A response entity containing all festivals.
     */
    @GetMapping(value ="/getAll")
    public ResponseEntity<?> getFestival(){
        return new ResponseEntity<>(festivals.findAll(), HttpStatus.OK);
    }


}
