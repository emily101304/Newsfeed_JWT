package com.example.newsfeed_jwt.domain.likes.service;

import com.example.newsfeed_jwt.domain.likes.entity.BaseLike;
import com.example.newsfeed_jwt.domain.likes.repository.CommentLikesRepository;
import com.example.newsfeed_jwt.domain.likes.repository.PostLikesRepository;
import com.example.newsfeed_jwt.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@RequiredArgsConstructor
public class LikesService<T extends BaseLike> {

    private final JpaRepository<T, Long> likesRepository;
    private final UserRepository userRepository;

    public void addLike(Long userId, Long entityId, T likeEntity) {
        if (userId.equals(likeEntity.getUser().getId())) {
            throw new IllegalStateException("본인의 게시글에는 좋아요를 누를 수 없습니다.");
        }
        if (findLike(entityId, userId).isPresent()) {
            throw new IllegalStateException("이미 좋아요를 눌렀습니다. 좋아요는 한번만 가능합니다.");
        }
        likesRepository.save(likeEntity);
    }

    public void cancelLike(Long entityId, Long userId) {
        T likeEntity = findLike(entityId, userId).orElseThrow(
                () -> new IllegalArgumentException("사용자가 좋아요를 누르지 않은 글 입니다.")
        );
        likesRepository.delete(likeEntity);
    }

    private Optional<T> findLike(Long entityId, Long userId) {
        if (likesRepository instanceof PostLikesRepository postLikesRepository) {
            return (Optional<T>) postLikesRepository.findByPostIdAndUserId(entityId, userId);
        }
        if (likesRepository instanceof CommentLikesRepository commentLikesRepository) {
            return (Optional<T>) commentLikesRepository.findByCommentIdAndUserId(entityId, userId);
        }
        return Optional.empty();
    }
}
