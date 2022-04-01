package com.maveric.commentservice.services;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.Commentdto;
import com.maveric.commentservice.model.Comment;

import java.util.List;

public interface CommentService {
    CommentResponse createComment(String postId,Commentdto commentdto);
	CommentResponse updateComment(String postId,String commentId, Commentdto updateComment);
    String deleteComment(String postId,String commentId);
}
