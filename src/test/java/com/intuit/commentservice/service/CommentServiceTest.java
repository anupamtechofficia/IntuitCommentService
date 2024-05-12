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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    private static final Long PARENT_COMMENT_ID = 1L;
    private static final Long COMMENT_ID = 1L;
    private static final Long NEW_COMMENT_ID = 2L;
    private static final Integer PAGE = 0;
    private static final Integer PAGE_SIZE = 1;
    private static final String FIRST_NAME = "test";


    @Mock
    private CommentRepository commentRepository;
    @Mock
    private ReactionRepository reactionRepository;
    @InjectMocks
    protected CommentService commentService;


    @BeforeEach
    public void setUp() {
    }

    @Test
    public void test_findAllComments_noCommentExits() {
        when(commentRepository.findByParentCommentIdAndPostId(anyLong(), anyLong(), ArgumentMatchers.any()))
                .thenReturn(new ArrayList<>());

        List<CommentResponseDTO> comments = commentService.findAllComments(PARENT_COMMENT_ID, COMMENT_ID,
                PAGE, PAGE_SIZE);
        assertThat(comments.size()).isEqualTo(0);
    }

    @Test
    public void test_findAllComments_successful() {
        when(commentRepository.findByParentCommentIdAndPostId(anyLong(), anyLong(), ArgumentMatchers.any()))
                .thenReturn(List.of(
                        Comment.builder()
                                .commentId(NEW_COMMENT_ID)
                                .user(User.builder().firstName(FIRST_NAME).build())
                                .build()));
        when(reactionRepository.findReactionCountForComments(any())).
                thenReturn(List.of(
                        new ReactionCount(ReactionType.LIKE, 1L, NEW_COMMENT_ID))
                );

        List<CommentResponseDTO> comments = commentService.findAllComments(PARENT_COMMENT_ID, COMMENT_ID,
                PAGE, PAGE_SIZE);
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0).getId()).isEqualTo(NEW_COMMENT_ID);
        assertThat(comments.get(0).getName()).isEqualTo(FIRST_NAME);
        assertThat(comments.get(0).getTotalLikes()).isEqualTo(1);
        assertThat(comments.get(0).getTotalDislikes()).isEqualTo(0);
    }

    @Test
    public void test_postComments_forNonExistingParentComment() {
        when(commentRepository.existsByCommentId(PARENT_COMMENT_ID)).thenReturn(false);

        assertThrows(CommentNotFoundException.class, () -> {
            commentService.addComment(new CommentRequestDTO(), PARENT_COMMENT_ID, COMMENT_ID);
        });
    }

    @Test
    public void test_postComments_successful() throws CommentNotFoundException {
        when(commentRepository.existsByCommentId(PARENT_COMMENT_ID)).thenReturn(true);
        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(
                Comment.builder().commentId(NEW_COMMENT_ID).build());
        final Long newCommentId = commentService.addComment(new CommentRequestDTO(), PARENT_COMMENT_ID, COMMENT_ID);
        assertThat(newCommentId).isEqualTo(2L);
    }
}