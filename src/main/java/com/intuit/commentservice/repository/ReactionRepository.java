package com.intuit.commentservice.repository;

import com.intuit.commentservice.model.Reaction;
import com.intuit.commentservice.model.ReactionCount;
import com.intuit.commentservice.model.ReactionType;
import com.intuit.commentservice.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;


// Spring Data JPA creates CRUD implementation at runtime automatically.
public interface ReactionRepository extends JpaRepository<Reaction, Long> {
    List<Reaction> findByCommentIdAndReactionTypeIn(@NonNull Long commentId, @NonNull Set<ReactionType> reactionType,
                                                    @NonNull Pageable pageable);
    Reaction findByUserAndCommentId(@NonNull User user, @NonNull Long commentId);

    @Query("SELECT new com.intuit.commentservice.model.ReactionCount(c.commentId, c.reactionType, COUNT(*)) "
            + "FROM Reaction AS c WHERE c.commentId IN  :commentIds GROUP BY c.commentId,c.reactionType")
    List<ReactionCount> findReactionCountForComments(List<Long> commentIds);

}