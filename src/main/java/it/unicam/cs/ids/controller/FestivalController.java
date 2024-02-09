package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.Exception.*;
import it.unicam.cs.ids.controller.Repository.FestivalRepository;
import it.unicam.cs.ids.model.content.Festival;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import it.unicam.cs.ids.model.user.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
/**
 * The  Festival controller class manages the addition and removing of Festivals,
 * It interacts with instances of {@link UserRole}, {@link IUserPlatform}
 */
@RestController
public class FestivalController {
    private final FestivalRepository festivals;
    boolean alreadyIn=false;
    @Autowired
    public FestivalController(FestivalRepository festivals) {
        this.festivals = festivals;
    }

    /**
     * Adds a Festival to the list
     *
     * @param newfestival The Festival to be added.
     */
    @PostMapping("/festival")
    public ResponseEntity<Object> addFestival(@RequestBody Festival newfestival){
        if(newfestival.getEnd().after(new Date())){
            if(!festivals.existsByName(newfestival.getDescription())){
                festivals.save(newfestival);
                return new ResponseEntity<>("Festival created", HttpStatus.OK);
            }
        } throw new FestivalAlreadyInException();
    }
    /**
     * Remove a specific Festival searched by the title
     *
     * @param title the festival's title
     */

    public  ResponseEntity<Object>  removeFestival(String title){
        //TODO:modificare il metodo
        if(!festivals.existsByName(title)){
            festivals.deleteById(newfestival);
            return new ResponseEntity<>("Festival deleted", HttpStatus.OK);
        }throw new FestivalNotFoundException();
        festivals.delete(festival -> festival.getDescription().equals(title));
    }

    /**
     * Gets if the festival is active
     * @return if the Festival is active
     */
    public boolean isActive(Festival festival){
        return festival.getEnd().after(new Date());
    }

}
