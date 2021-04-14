package com.psl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psl.dto.VoteDto;
import com.psl.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/votes")
@AllArgsConstructor
public class VoteController {

	private VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> castVote(@RequestBody VoteDto voteDto) {
		voteService.castVote(voteDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
