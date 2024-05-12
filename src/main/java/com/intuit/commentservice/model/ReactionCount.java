package com.intuit.commentservice.model;

import lombok.Data;

@Data
public class ReactionCount {
    private ReactionType type;
    private Long total;
    private Long commentId;

    public ReactionCount(final Long commentId, final ReactionType type, final Long total) {
        this.type = type;
        this.total = total;
        this.commentId = commentId;
    }
}
