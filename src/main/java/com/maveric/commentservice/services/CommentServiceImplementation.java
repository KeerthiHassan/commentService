package com.maveric.commentservice.services;

import com.maveric.commentservice.controller.CommentController;
import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.Commentdto;
import com.maveric.commentservice.dto.UserResponse;
import com.maveric.commentservice.exception.CommentNotBelongs;
import com.maveric.commentservice.exception.CommentNotFound;
import com.maveric.commentservice.feign.LikeFeign;
import com.maveric.commentservice.feign.UserFeign;
import com.maveric.commentservice.model.Comment;
import com.maveric.commentservice.repo.CommentRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class CommentServiceImplementation implements CommentService{
    private static Logger log = LoggerFactory.getLogger(CommentServiceImplementation.class);

    @Autowired
    CommentRepo commentRepo;

    @Autowired
    LikeFeign feign;

    @Autowired
    UserFeign userFeign;
@LoadBalanced

    

    @Override
    public CommentResponse createComment(String postId,Commentdto commentdto) {
        Comment comments=new Comment();
        comments.setCreatedAt(LocalDate.now());
        comments.setUpdatedAt(LocalDate.now());
        comments.setComment(commentdto.getComment());
        comments.setCommentedBy(commentdto.getCommentedBy());
        comments.setPostId(postId);
        Comment comment=commentRepo.save(comments);
        log.info("comment created successfully");
        return new CommentResponse(comment.getCommentId(),userFeign.getUsersById(comment.getCommentedBy()).getBody(),
                comment.getComment(),comment.getCreatedAt(),comment.getUpdatedAt(),0);
    }

    
    
   
    public CommentResponse setCommentResponse(Comment comment){
    return new CommentResponse(comment.getCommentId(),userFeign.getUsersById(comment.getCommentedBy()).getBody(),
            comment.getComment(),comment.getCreatedAt(),comment.getUpdatedAt(),
            feign.getLikesCount(comment.getCommentId()).getBody());
    }
}
