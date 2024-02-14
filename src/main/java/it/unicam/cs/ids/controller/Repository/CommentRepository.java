package it.unicam.cs.ids.controller.Repository;

import it.unicam.cs.ids.model.content.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Integer>{
}
