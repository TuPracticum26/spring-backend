package com.sap.documentmgn.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDTO {
    @NotBlank(message = "Comment text cannot be blank")
    @Size(max=500, message = "Comment text cannot exceed 500 characters")
    private String comment;

}
