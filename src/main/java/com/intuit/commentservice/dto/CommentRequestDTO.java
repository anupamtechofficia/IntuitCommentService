package com.intuit.commentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestDTO {
    @NotBlank(message = "message is mandatory")
    @JsonProperty(value = "message")
    private String comment;
    @NotNull(message = "user Id is mandatory")
    @JsonProperty(value = "user_id")
    private Long userId;
}
