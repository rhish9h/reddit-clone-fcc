package com.psl.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.psl.dto.CommentDto;
import com.psl.exception.PostNotFoundException;
import com.psl.mapper.CommentMapper;
import com.psl.model.Comment;
import com.psl.model.NotificationEmail;
import com.psl.model.Post;
import com.psl.model.User;
import com.psl.repository.CommentRepository;
import com.psl.repository.PostRepository;
import com.psl.repository.UserRepository;

import lombok.AllArgsConstructor;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {
	
	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private AuthService authService;
	private CommentMapper commentMapper;
	private MailContentBuilder mailContentBuilder;
	private static final String POST_URL = "";
	private MailService mailService;
	private UserRepository userRepository;
	
	public void save(CommentDto commentDto) {
		Post post = postRepository.findById(commentDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(commentDto.getPostId().toString()));
		User user = authService.getCurrentUser();
		
		Comment comment = commentMapper.map(commentDto, post, user);
		
		commentRepository.save(comment);
		
		String message = mailContentBuilder.build(authService.getCurrentUser() + " posted a comment on your post." + POST_URL);
		sendCommentNotification(message, post.getUser());
	}

	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
		
	}

	public List<CommentDto> getCommentsByPost(Long postId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new PostNotFoundException(postId.toString()));
		
		return commentRepository.findByPost(post)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(toList());
	}

	public List<CommentDto> getCommentsByUserName(String userName) {
		User user = userRepository.findByUsername(userName)
				.orElseThrow(() -> new UsernameNotFoundException(userName));
		
		return commentRepository.findAllByUser(user)
				.stream()
				.map(commentMapper::mapToDto)
				.collect(toList());
	}
}
