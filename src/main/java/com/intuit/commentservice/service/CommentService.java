package com.intuit.commentservice.service;

import com.intuit.commentservice.dto.CommentRequestDTO;
import com.intuit.commentservice.dto.CommentResponseDTO;
import com.intuit.commentservice.exceptions.CommentNotFoundException;
import com.intuit.commentservice.model.Comment;
import com.intuit.commentservice.model.ReactionCount;
import com.intuit.commentservice.model.ReactionType;
import com.intuit.commentservice.model.User;
import com.intuit.commentservice.repository.CommentRepository;
import com.intuit.commentservice.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.intuit.commentservice.util.ApiErrorConstants.PARENT_COMMENT_NOT_FOUND_EXCEPTION;
import static java.lang.String.format;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    public List<CommentResponseDTO> findAllComments(
             @NonNull final Long postId, 
             @NonNull final Long parentCommentId,
             @NonNull final Integer page,
             @NonNull final Integer pageSize) {
        final List<Comment> comments = commentRepository.findByParentCommentIdAndPostId(parentCommentId, postId,
                PageRequest.of(page, pageSize));
        if (CollectionUtils.isEmpty(comments)) {
            return new ArrayList<>();
        }
        final List<Long> commentIds = comments.stream().map(Comment::getCommentId).toList();
        final List<ReactionCount> reactionCounts = reactionRepository.findReactionCountForComments(commentIds);

        return mapToCommentDTO(comments, reactionCounts);
    }

    public Long addComment(
            @NonNull final CommentRequestDTO commentRequestDTO,
            @NonNull final Long postId,
            @NonNull final Long parentCommentId)
            throws CommentNotFoundException {
        if (parentCommentId != 0L && !commentRepository.existsByCommentId(parentCommentId)) {
            throw new CommentNotFoundException(format(PARENT_COMMENT_NOT_FOUND_EXCEPTION, parentCommentId));
        }
        return commentRepository.save(mapToComment(commentRequestDTO, postId, parentCommentId))
                .getCommentId();
    }

    private List<CommentResponseDTO> mapToCommentDTO(
            final List<Comment> comments, 
            final List<ReactionCount> reactionCounts) {
        final List<CommentResponseDTO> list = new ArrayList<>();
        for (final Comment comment : comments) {
            Optional<ReactionCount> likeCount = reactionCounts.stream().filter(rec ->
                    Objects.equals(rec.getCommentId(), comment.getCommentId()) &&
                            rec.getType().equals(ReactionType.LIKE)).findFirst();
            Optional<ReactionCount> dislikeCount = reactionCounts.stream().filter(rec ->
                    Objects.equals(rec.getCommentId(), comment.getCommentId()) &&
                            rec.getType().equals(ReactionType.DISLIKE)).findFirst();
            final CommentResponseDTO commentDTO = new CommentResponseDTO();
            commentDTO.setComment(comment.getMessage());
            commentDTO.setCommentDate(comment.getCreateDate());
            final String userName = comment.getUser().getFirstName() + " " + comment.getUser().getLastName();
            commentDTO.setName(userName.trim());
            commentDTO.setTotalLikes(likeCount.isPresent() ? likeCount.get().getTotal() : 0);
            commentDTO.setTotalDislikes(dislikeCount.isPresent() ? dislikeCount.get().getTotal() : 0);
            commentDTO.setId(comment.getCommentId());
            list.add(commentDTO);
        }
        return list;
    }

    private Comment mapToComment(
            final CommentRequestDTO commentRequestDTO, 
            final Long postId,
            final Long parentCommentId) {
        return Comment.builder()
                .parentCommentId(parentCommentId)
                .message(commentRequestDTO.getComment())
                .postId(postId)
                .user(User.builder().userId(commentRequestDTO.getUserId()).build())
                .build();

    }
}
