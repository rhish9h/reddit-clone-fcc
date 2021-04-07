package com.psl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.model.Comment;
import com.psl.model.Post;
import com.psl.model.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List <Comment> findByPost(Post post);
	List <Comment> findAllByUser(User user);
}
