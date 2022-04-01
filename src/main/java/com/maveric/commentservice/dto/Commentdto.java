package com.maveric.commentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commentdto {
    @NotEmpty(message = "Please provide Comment")
    private String comment;
    @NotEmpty(message = "Please provide By whom is getting changed")
    private String commentedBy;
}
