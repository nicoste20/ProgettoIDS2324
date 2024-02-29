package it.unicam.cs.ids.model.command;

import it.unicam.cs.ids.controller.Repository.FestivalRepository;
import it.unicam.cs.ids.model.content.Festival;

public class DeleteFestivalCommand implements Command{
    private final FestivalRepository festivals;
    private Festival festival;

    public DeleteFestivalCommand(FestivalRepository festivals, Festival festival) {
        this.festivals = festivals;
        this.festival=festival;
    }

    @Override
    public void execute() {
        festivals.delete(festival);
    }
}
