package com.maveric.commentservice.controller;

import com.maveric.commentservice.dto.CommentResponse;
import com.maveric.commentservice.dto.Commentdto;
import com.maveric.commentservice.model.Comment;
import com.maveric.commentservice.services.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class CommentController {
    private static Logger log = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    CommentService commentService;


    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>>  getComments(@PathVariable ("postId") String postId, @QueryParam("page") Integer page, @QueryParam("size") Integer size){
        log.info("getting all comments");
        return new ResponseEntity<List<CommentResponse>>(commentService.getComments(postId,page,size), HttpStatus.OK);
    }
    
	@GetMapping("/{postId}/comments/count")
    public ResponseEntity<Integer>  getCommentsCount(@PathVariable ("postId") String postId){
        log.info("getting comment count");
        return new ResponseEntity<Integer>(commentService.getCommentsCount(postId), HttpStatus.OK);
    }
	
	@GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse>  getCommentDetails(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId){
        log.info("getting comment details");
        return new ResponseEntity<CommentResponse>(commentService.getCommentDetails(postId,commentId), HttpStatus.OK);
    }
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable ("postId") String postId,@Valid  @RequestBody Commentdto commentdto){
        log.info("creating  new comment for the post");
        return new ResponseEntity<CommentResponse>(commentService.createComment(postId,commentdto),HttpStatus.CREATED);
    }
	
	@PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId,@Valid @RequestBody Commentdto updateComments){
        log.info("Updating comment" );
        return new ResponseEntity<CommentResponse>(commentService.updateComment(postId,commentId,updateComments),HttpStatus.OK);
    }
	
	@DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletePost(@PathVariable ("postId") String postId,@PathVariable("commentId") String commentId){
        log.info("Deleting comment");
        return new ResponseEntity<String>(commentService.deleteComment(postId,commentId),HttpStatus.OK);
    }
    
    
}
