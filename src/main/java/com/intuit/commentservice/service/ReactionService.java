package com.intuit.commentservice.service;

import com.intuit.commentservice.dto.ReactionRequestDTO;
import com.intuit.commentservice.dto.ReactionResponseDTO;
import com.intuit.commentservice.exceptions.CommentNotFoundException;
import com.intuit.commentservice.exceptions.CommentServiceException;
import com.intuit.commentservice.model.Reaction;
import com.intuit.commentservice.model.ReactionType;
import com.intuit.commentservice.model.User;
import com.intuit.commentservice.repository.CommentRepository;
import com.intuit.commentservice.repository.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.intuit.commentservice.util.ApiErrorConstants.COMMENT_NOT_FOUND_EXCEPTION;
import static java.lang.String.format;

@Service
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private CommentRepository commentRepository;

    public List<ReactionResponseDTO> findAllUserThatReacted(
            @NonNull final Long commentId,
            @NonNull final Set<ReactionType> reactionType,
            @NonNull final Integer page,
            @NonNull final Integer pageSize) throws CommentServiceException {

        if (!commentRepository.existsByCommentId(commentId)) {
            throw new CommentNotFoundException(format(COMMENT_NOT_FOUND_EXCEPTION, commentId));
        }

        final List<Reaction> reactions = reactionRepository.findByCommentIdAndReactionTypeIn(commentId,
                reactionType, PageRequest.of(page, pageSize));
        return mapToReactionDTO(reactions);
    }

    public Long addReaction(
            @NonNull final Long commentId,
            @NonNull final ReactionRequestDTO reactionRequestDTO) throws CommentServiceException {

        if (!commentRepository.existsByCommentId(commentId)) {
            throw new CommentNotFoundException(format(COMMENT_NOT_FOUND_EXCEPTION, commentId));
        }

        final User user = User.builder().userId(reactionRequestDTO.getUserId()).build();
        Reaction reaction = reactionRepository.findByUserAndCommentId(user, commentId);
        if (Objects.isNull(reaction)) {
            reaction = Reaction.builder()
                    .commentId(commentId)
                    .user(user)
                    .reactionType(reactionRequestDTO.getReactionType())
                    .build();
        } else {
            reaction.setReactionType(reactionRequestDTO.getReactionType());
        }
        return reactionRepository.save(reaction).getReactionId();
    }

    private List<ReactionResponseDTO> mapToReactionDTO(final List<Reaction> reactions) {
        final List<ReactionResponseDTO> list = new ArrayList<>();
        for (final Reaction reaction : reactions) {
            final ReactionResponseDTO reactionDTO = new ReactionResponseDTO();
            reactionDTO.setId(reaction.getReactionId());
            reactionDTO.setReactionDate(reaction.getReactionTime());
            final String userName = reaction.getUser().getFirstName() + " " + reaction.getUser().getLastName();
            reactionDTO.setName(userName.trim());
            reactionDTO.setReactionType(reaction.getReactionType().toString());
            list.add(reactionDTO);
        }
        return list;
    }

}
