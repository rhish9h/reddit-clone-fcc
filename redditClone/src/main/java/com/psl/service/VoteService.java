package com.psl.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.psl.dto.VoteDto;
import com.psl.exception.PostNotFoundException;
import com.psl.exception.SpringRedditException;
import com.psl.model.Post;
import com.psl.model.Vote;
import com.psl.repository.PostRepository;
import com.psl.repository.VoteRepository;

import lombok.AllArgsConstructor;
import static com.psl.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
	
	PostRepository postRepository;
	AuthService authService;
	VoteRepository voteRepository;
	
	public void castVote(VoteDto voteDto) {
		Post post = postRepository.findById(voteDto.getPostId())
				.orElseThrow(() -> new PostNotFoundException(voteDto.getPostId().toString()));
		
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDto.getVoteType() + "D for this post");
		}
		
		if (voteDto.getVoteType().equals(UPVOTE)) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return Vote.builder()
				.voteType(voteDto.getVoteType())
				.post(post)
				.user(authService.getCurrentUser())
				.build();
	}
	
}
