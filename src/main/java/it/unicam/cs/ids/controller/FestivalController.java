package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.FestivalRepository;
import it.unicam.cs.ids.controller.Repository.UserRepository;
import it.unicam.cs.ids.model.content.Festival;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * The  Festival controller class manages the addition and removing of Festivals,
 * It interacts with instances of {@link UserRole}, {@link IUserPlatform}
 */
@RestController
public class FestivalController {
    private final FestivalRepository festivals;
    private final UserRepository users;

    @Autowired
    public FestivalController(FestivalRepository festivals, UserRepository users) {
        this.festivals = festivals;
        this.users = users;
    }

    /**
     * Adds a Festival to the list
     *
     * @param newfestival The Festival to be added.
     * @return ResponseEntity with appropriate status and message
     */
    @PostMapping("/add/festival{userId}")
    public ResponseEntity<Object> addFestival(@RequestBody Festival newfestival, @PathParam(("userId")) Integer userId){
        newfestival.setAuthor(userId);
        if(users.findById(userId).get().getUserType().equals(UserRole.Curator)) {
            if (newfestival.getEndDate().after(new Date())) {
                if (festivals.countFestivalsWithDescription(newfestival.getName()) == 0) {
                    festivals.save(newfestival);
                    return new ResponseEntity<>("Festival created", HttpStatus.OK);
                } else throw new FestivalNotFoundException();
            }else throw new FestivalAlreadyInException();
        }else throw new UserBadTypeException();
    }

    /**
     * Remove a specific Festival searched by the title
     *
     * @param title the festival's title
     * @return ResponseEntity with appropriate status and message.
     */
    @RequestMapping(value = "/del/festival{title}{userId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object>  removeFestival(@PathParam(("title")) String title, @PathParam(("userId")) int userId){
        if(users.findById(userId).get().getUserType().equals(UserRole.Curator)) {
            if (festivals.countFestivalsWithDescription(title) > 0) {
                festivals.deleteById(festivals.findFestivalIdByDescription(title));
                return new ResponseEntity<>("Festival cancelled", HttpStatus.OK);
            }
            throw new FestivalNotFoundException();
        }else throw new UserBadTypeException();
    }

    /**
     * Gets if the festival is active
     *
     * @param text The text description of the festival.
     * @return true if the Festival is active, false otherwise.
     */
    private boolean isActive(String text){
        if(festivals.countFestivalsWithDescription(text)>0){
            int id = festivals.findFestivalIdByDescription(text);
            return festivals.findById(id).get().getEndDate().after(new Date());
        } throw new FestivalNotFoundException();
    }
    @GetMapping(value ="/get/festivals")
    public ResponseEntity<Object> getFestival(){
        return new ResponseEntity<>(festivals.findAll(), HttpStatus.OK);
    }


}
