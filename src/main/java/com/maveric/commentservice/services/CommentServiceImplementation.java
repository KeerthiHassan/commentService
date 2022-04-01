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
    public List<CommentResponse> getComments(String postId,Integer page, Integer size) {
   Pageable pageable= PageRequest.of(page,size);
    List<Comment> commentList=commentRepo.findBypostId(postId,pageable);
        if(commentList==null) {
            log.info("comments not found");
            throw new CommentNotFound("No comment for postId: " + postId);
        }
        List<CommentResponse> commentResponses=new ArrayList<>();
        log.info("Total comments with this post are "+commentList.size());
        for(Comment comment:commentList)
        {
            log.info("Comments"+setCommentResponse(comment));
             commentResponses.add(setCommentResponse(comment));
        }
        log.info("comments found successfully");
        return commentResponses;
    }
	
	 @Override
    public Integer getCommentsCount(String postId) {
        return commentRepo.findBypostId(postId).size();
    }
	
	@Override
    public CommentResponse getCommentDetails(String postId,String commentId) {
        Comment comment=commentRepo.findBycommentId(commentId);
        if(comment==null){
            log.info("comment details not found");
            throw new CommentNotFound("Comment not found with Id "+commentId);
        }
        log.info("Comment details found");
        return setCommentResponse(comment);
    }
	
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
	
	@Override
    public String deleteComment(String postId,String commentId) {
Comment comment=commentRepo.findBycommentId(commentId);
        if(comment==null){
            log.info("comment not found");
        throw new CommentNotFound("Comment Doesn't Exists");
        }
       else if(!comment.getPostId().equals(postId)) {
            log.info("Comment does not match with post");
            throw new CommentNotBelongs("Comment does not match with this post try again");
        }
         commentRepo.deleteById(commentId);
       log.info("comment deleted");
        return "Comment deleted successfully";
    }

    
    
   
    public CommentResponse setCommentResponse(Comment comment){
    return new CommentResponse(comment.getCommentId(),userFeign.getUsersById(comment.getCommentedBy()).getBody(),
            comment.getComment(),comment.getCreatedAt(),comment.getUpdatedAt(),
            feign.getLikesCount(comment.getCommentId()).getBody());
    }
}
