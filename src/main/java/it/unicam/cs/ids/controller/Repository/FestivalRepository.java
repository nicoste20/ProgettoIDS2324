package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Festival;
import org.springframework.data.repository.CrudRepository;

public interface FestivalRepository extends CrudRepository<Festival,String> {
}
