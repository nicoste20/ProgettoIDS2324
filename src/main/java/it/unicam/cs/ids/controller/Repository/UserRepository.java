package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Multimedia;
import it.unicam.cs.ids.model.content.Point;
import it.unicam.cs.ids.model.user.BaseUser;
import it.unicam.cs.ids.model.user.IUserPlatform;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<BaseUser,Integer> {
    @Query ("SELECT COUNT(x) FROM BaseUser x WHERE x.email= :email")
    int findByEmail(@Param("email") String email);

    @Query ("SELECT x.id FROM BaseUser x WHERE x.email= :email")
    int selectByEmail(@Param("email") String email);
}
