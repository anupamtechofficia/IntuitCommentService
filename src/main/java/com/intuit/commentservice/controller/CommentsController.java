package com.intuit.commentservice.controller;

import com.intuit.commentservice.dto.CommentRequestDTO;
import com.intuit.commentservice.dto.CommentResponseDTO;
import com.intuit.commentservice.exceptions.CommentServiceException;
import com.intuit.commentservice.service.CommentService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.intuit.commentservice.util.ApiConstants.*;

@RestController
@Validated
@RequestMapping(value = "api/v1/intuit/comments")
public class CommentsController {


    @Autowired
    private CommentService commentService;

    @GetMapping(
            name = API_NAME_GET_COMMENTS,
            value = GET_COMMENTS)
    ResponseEntity<?> getComments(
            @PathVariable(required = true) final Long postId,
            @Nullable @RequestParam(value = REQUEST_PARAM_PARENT_COMMENT_IS,
                    defaultValue = "0") final Long parentCommentId,
            @Nullable @RequestParam(name = "page", defaultValue = "0") Integer page,
            @Max(100) @Min(1) @Nullable @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        final List<CommentResponseDTO> comments = commentService.findAllComments(postId, parentCommentId, page, pageSize);
        ResponseEntity.BodyBuilder entity = ResponseEntity.ok()
                .header(HEADER_POST_ID, String.valueOf(postId));
        if (parentCommentId != 0L) {
            entity.header(HEADER_PARENT_COMMENT_ID, String.valueOf(parentCommentId));
        }
        return entity.body(comments);
    }

    @PostMapping(
            name = API_NAME_POST_COMMENT,
            value = POST_COMMENT)
    ResponseEntity<?> postComments(
            @PathVariable(required = true) final Long postId,
            @Nullable @RequestParam(value = REQUEST_PARAM_PARENT_COMMENT_IS, defaultValue = "0") final Long parentCommentId,
            @Valid @RequestBody(required = true) CommentRequestDTO commentRequestDTO) throws CommentServiceException {
        Long commentId = commentService.addComment(commentRequestDTO, postId, parentCommentId);
        return ResponseEntity.noContent()
                .header(HEADER_COMMENT_ID, String.valueOf(commentId))
                .build();
    }

}
