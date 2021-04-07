package com.psl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.psl.model.Post;
import com.psl.model.Subreddit;
import com.psl.model.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findAllBySubreddit(Subreddit subreddit);
	List<Post> findByUser(User user);
}
