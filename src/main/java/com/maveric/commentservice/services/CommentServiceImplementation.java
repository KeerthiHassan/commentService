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
	@Override
    public CommentResponse updateComment(String postId,String commentId, Commentdto updateComments) {
        Comment comment=commentRepo.findBycommentId(commentId);
        if(comment==null){
            log.info("comment not found");
            throw new CommentNotFound("can't find comment on the post ,comment not found");
        }
        else if(comment.getCommentedBy().equals(updateComments.getCommentedBy())){
            log.info("Comment is not created by you");
            throw new CommentNotBelongs("Can't updated,Comment doesn't belongs to you");
        }
        comment.setComment(updateComments.getComment());
        comment.setCommentedBy(updateComments.getCommentedBy());
        comment.setUpdatedAt(LocalDate.now());
        comment.setPostId(postId);
        log.info("Updating comment in repo");
        return setCommentResponse(commentRepo.save(comment));
    }

    
    
   
    public CommentResponse setCommentResponse(Comment comment){
    return new CommentResponse(comment.getCommentId(),userFeign.getUsersById(comment.getCommentedBy()).getBody(),
            comment.getComment(),comment.getCreatedAt(),comment.getUpdatedAt(),
            feign.getLikesCount(comment.getCommentId()).getBody());
    }
}
