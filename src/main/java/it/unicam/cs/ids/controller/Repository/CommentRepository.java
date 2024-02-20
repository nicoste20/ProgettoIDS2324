package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByContentId(Integer contentId);
}
