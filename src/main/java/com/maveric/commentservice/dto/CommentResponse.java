package com.maveric.commentservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String commentId;
    private UserResponse commentedBy;
    private String comment;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Integer likesCount;
}
