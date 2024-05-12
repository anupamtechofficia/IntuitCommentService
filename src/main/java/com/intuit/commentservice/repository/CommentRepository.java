package com.intuit.commentservice.repository;

import com.intuit.commentservice.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;


// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface CommentRepository extends JpaRepository<Comment, Long> {


    List<Comment> findByParentCommentIdAndPostId(@NonNull Long parentCommentId, @NonNull Long postId,
                                                 @NonNull Pageable pageable);

    boolean existsByCommentId(@NonNull Long commentId);

    List<Comment> findByPostId(@NonNull Long postId);

}