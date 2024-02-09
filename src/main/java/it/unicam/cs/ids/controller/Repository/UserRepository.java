package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.user.IUserPlatform;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<IUserPlatform,Integer> {
}
