package com.intuit.commentservice.controller;

import com.intuit.commentservice.dto.ReactionRequestDTO;
import com.intuit.commentservice.dto.ReactionResponseDTO;
import com.intuit.commentservice.exceptions.CommentServiceException;
import com.intuit.commentservice.model.ReactionType;
import com.intuit.commentservice.service.ReactionService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static com.intuit.commentservice.util.ApiConstants.*;

@RestController
@Validated
@RequestMapping(value = "api/v1/intuit/reactions")
public class ReactionsController {

    @Autowired
    private ReactionService reactionService;

    @GetMapping(
            name = API_NAME_GET_USER_REACTION_LIST,
            value = GET_LIST_OF_USER_REACTED)
    ResponseEntity<List<ReactionResponseDTO>> getListOfUserReacted(
            @PathVariable(required = true) final Long commentId,
            @RequestParam(value = REQUEST_PARAM_REACTION_TYPE) final Set<ReactionType> reactionType,
            @Nullable @RequestParam(name = "page", defaultValue = "0") Integer page,
            @Max(100) @Min(1) @Nullable @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize)
            throws CommentServiceException {
        final List<ReactionResponseDTO> reactionDTO = reactionService.findAllUserThatReacted(commentId, reactionType,
                page, pageSize);
        return ResponseEntity.ok()
                .header(HEADER_PARENT_COMMENT_ID, String.valueOf(commentId))
                .body(reactionDTO);
    }

    @PostMapping(
            name = API_NAME_POST_REACTION,
            value = POST_REACTION)
    ResponseEntity<?> postReaction(
            @PathVariable(required = true) final Long commentId,
            @Valid @RequestBody(required = true) ReactionRequestDTO reactionRequestDTO) throws CommentServiceException {
        Long reaction = reactionService.addReaction(commentId, reactionRequestDTO);
        return ResponseEntity.noContent()
                .header(HEADER_REACTION_ID, String.valueOf(reaction)).build();
    }
}
