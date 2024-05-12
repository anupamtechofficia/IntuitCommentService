package com.intuit.commentservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReactionResponseDTO {
    @JsonProperty("reaction_id")
    private long id;
    @JsonProperty("user_name")
    private String name;
    @JsonProperty("reaction_type")
    private String reactionType;
    @JsonProperty("reaction_date")
    private Date reactionDate;
}
