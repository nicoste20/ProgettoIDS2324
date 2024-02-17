package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Multimedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface MultimediaRepository extends JpaRepository<Multimedia,Integer> {
}
