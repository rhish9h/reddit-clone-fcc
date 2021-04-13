package com.psl.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.CommentDto;
import com.psl.service.CommentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
	
	private CommentService commentService;
	
	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentDto commentDto) {
		commentService.save(commentDto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/by-post/{postId}")
	public ResponseEntity<List<CommentDto>> getCommentsByPost(@PathVariable Long postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
	}
	
	@GetMapping("/by-user/{userName}")
	public ResponseEntity<List<CommentDto>> getCommentsByUserName(@PathVariable String userName) {
		return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUserName(userName));
	}
	
}
