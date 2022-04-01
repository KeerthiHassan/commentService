package com.maveric.commentservice.model;

import com.maveric.commentservice.dto.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Document(collection = "comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    private String commentId;

    private String commentedBy;
    @NotEmpty(message = "Please provide Comment")
    private String comment;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String postId;

}
