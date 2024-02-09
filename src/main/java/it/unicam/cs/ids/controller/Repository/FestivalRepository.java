package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Festival;
import org.springframework.data.repository.CrudRepository;

public interface FestivalRepository extends CrudRepository<Festival,Integer> {
    boolean existsByName(String text); //TODO:mettere la query e una per il ritorno dell'id data la descirizione

}
