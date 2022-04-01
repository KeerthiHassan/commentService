package com.maveric.commentservice.services;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.Commentdto;
import com.maveric.commentservice.model.Comment;

import java.util.List;

public interface CommentService {
	 List<CommentResponse> getComments(String postId);
	 Integer getCommentsCount(String postId);
	 CommentResponse getCommentDetails(String postId,String commentId);
    CommentResponse createComment(String postId,Commentdto commentdto);
	CommentResponse updateComment(String postId,String commentId, Commentdto updateComment);
    String deleteComment(String postId,String commentId);
}
