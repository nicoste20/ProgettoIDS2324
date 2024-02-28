package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    @Query("SELECT x.comments FROM Multimedia x WHERE x.id= :multimediaId")
    List<Integer> findByMultimediaId(@Param("multimediaId") int multimediaId);
}
