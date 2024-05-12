package com.intuit.commentservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.intuit.commentservice.model.ReactionType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReactionRequestDTO {
    @NotNull(message = "reaction type is mandatory")
    @JsonProperty(value = "reaction")
    private ReactionType reactionType;
    @NotNull(message = "user Id is mandatory")
    @JsonProperty(value = "user_id")
    private Long userId;
}
