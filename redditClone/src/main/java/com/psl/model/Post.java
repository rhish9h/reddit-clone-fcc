package com.psl.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long postId;
	
	@NotBlank(message = "Post name cannot be empty or Null")
	private String postName;
	
	@Nullable
	private String url;
	
	@Nullable
	@Lob
	private String description;
	
	private Integer voteCount = 0;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
	
	private Instant createdDate;
	
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "id", referencedColumnName = "id")
	private Subreddit subreddit;
}
