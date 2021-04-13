package com.psl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.psl.dto.CommentDto;
import com.psl.model.Comment;
import com.psl.model.Post;
import com.psl.model.User;

@Mapper(componentModel = "spring")
abstract public class CommentMapper {
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "text", source = "commentDto.text")
	@Mapping(target = "post", source = "post")
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "user", source = "user")
	abstract public Comment map(CommentDto commentDto, Post post, User user);
	
	@Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
	@Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
	abstract public CommentDto mapToDto(Comment comment);
}
