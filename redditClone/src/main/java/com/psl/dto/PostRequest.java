package com.psl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private Long postid;
	private String subredditName;
	private String postName;
	private String url;
	private String description;
}
