package it.unicam.cs.ids.model.memento;


import it.unicam.cs.ids.model.content.Festival;

import java.util.Date;
import java.util.List;

public class FestivalMemento {

    private Date currentDate;
    private  Date startDate;
    private  Date endDate;
    private List<Integer> points;
    private List<Integer> comments;
    private int author;
    private String name;
    private String description;
    private boolean isValidate;


    public FestivalMemento(Date currentDate, Festival festival) {
        this.currentDate = currentDate;
        this.startDate = festival.getStartDate();
        this.endDate=festival.getEndDate();
        this.points=festival.getPoints();
        this.comments=festival.getComments();
        this.author=festival.getAuthor();
        this.name=festival.getName();
        this.description= festival.getDescription();
        this.isValidate= festival.isValidate();
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Festival getFestival() {
        return festival;
    }

    public void setFestival(Festival festival) {
        this.festival = festival;
    }
}
