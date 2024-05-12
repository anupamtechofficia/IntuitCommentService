package com.intuit.commentservice.util;

public final class ApiConstants {
    private ApiConstants() {
    }

    static final String POST_ID = "postId";
    static final String COMMENT_ID = "commentId";

    public static final String GET_COMMENTS = "/{" + POST_ID + "}";
    public static final String POST_COMMENT = "/{" + POST_ID + "}";
    public static final String POST_REACTION = "/{" + COMMENT_ID + "}";
    public static final String GET_LIST_OF_USER_REACTED = "/{" + COMMENT_ID + "}";
    public static final String API_NAME_GET_COMMENTS = "getComments";
    public static final String API_NAME_GET_USER_REACTION_LIST = "getListOfUserReacted";
    public static final String API_NAME_POST_COMMENT = "postComment";
    public static final String API_NAME_POST_REACTION = "postReaction";
    public static final String HEADER_COMMENT_ID = "commentId";
    public static final String HEADER_REACTION_ID = "reactionId";
    public static final String HEADER_USER_ID = "userId";
    public static final String HEADER_POST_ID = "postId";
    public static final String HEADER_PARENT_COMMENT_ID = "parentCommentId";

    public static final String REQUEST_PARAM_REACTION_TYPE = "reactionType";
    public static final String REQUEST_PARAM_PARENT_COMMENT_IS = "parentCommentId";
    public static final String REQUEST_FIRST_NAME = "firstName";
    public static final String REQUEST_SECOND_NAME = "secondName";


}