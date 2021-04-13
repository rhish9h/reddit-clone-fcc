package com.psl.service;

import java.util.List;
import static java.util.stream.Collectors.toList;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.dto.PostRequest;
import com.psl.dto.PostResponse;
import com.psl.exception.PostNotFoundException;
import com.psl.exception.SubredditNotFoundException;
import com.psl.mapper.PostMapper;
import com.psl.model.Post;
import com.psl.model.Subreddit;
import com.psl.model.User;
import com.psl.repository.PostRepository;
import com.psl.repository.SubredditRepository;
import com.psl.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
	
	private final PostRepository postRepository;
	private final SubredditRepository subredditRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final PostMapper postMapper;
	
	public void save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
	}
	
	@Transactional(readOnly = true)
	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new PostNotFoundException(id.toString()));
		return postMapper.mapToDto(post);
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getAllPosts() {
		return postRepository.findAll()
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		Subreddit subreddit = subredditRepository.findById(subredditId)
				.orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
		return postRepository.findAllBySubreddit(subreddit)
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}

	@Transactional(readOnly = true)
	public List<PostResponse> getPostsByUsername(String name) {
		User user = userRepository.findByUsername(name)
				.orElseThrow(() -> new UsernameNotFoundException(name));
		
		return postRepository.findByUser(user)
				.stream()
				.map(postMapper::mapToDto)
				.collect(toList());
	}
}
