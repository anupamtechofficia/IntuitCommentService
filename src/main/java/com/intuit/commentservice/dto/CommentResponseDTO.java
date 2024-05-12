package com.intuit.commentservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseDTO {
    @JsonProperty("comment_id")
    private long id;
    @JsonProperty("message")
    private String comment;
    @JsonProperty("user_name")
    private String name;
    @JsonProperty("like_count")
    private Long totalLikes;
    @JsonProperty("dislike_count")
    private Long totalDislikes;
    @JsonProperty("comment_date")
    private Date commentDate;
}
