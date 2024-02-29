package it.unicam.cs.ids.model.memento;


import it.unicam.cs.ids.model.content.Festival;
import java.util.Date;
import java.util.List;

public class FestivalMemento {
    private final Date startDate;
    private final Date endDate;
    private final List<Integer> points;
    private final List<Integer> comments;
    private final int author;
    private final String name;
    private final String description;
    private final boolean isValidate;


    public FestivalMemento(Festival festival) {
        this.startDate = festival.getStartDate();
        this.endDate=festival.getEndDate();
        this.points=festival.getPoints();
        this.comments=festival.getComments();
        this.author=festival.getAuthor();
        this.name=festival.getName();
        this.description= festival.getDescription();
        this.isValidate= festival.isValidate();
    }

    public Festival getFestival() {
        Festival festival = new Festival(this.name ,this.points,this.startDate, this.endDate, this.description);
        festival.setAuthor(this.author);
        festival.setComments(this.comments);
        festival.setValidation(this.isValidate);
        return festival;
    }
}
