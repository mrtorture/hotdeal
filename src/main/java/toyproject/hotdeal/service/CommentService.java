package toyproject.hotdeal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.hotdeal.dao.CommentMapper;
import toyproject.hotdeal.dto.controller.CommentViewDTO;
import toyproject.hotdeal.dto.dao.CommentDTO;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final CommentMapper commentMapper;
    private final PostService postService;

    public List<CommentViewDTO> findByPostId(Long postId) {
        log.info("CommentService.findByPostId()");

        return commentMapper.findByPostId(postId);
    }

    public Optional<CommentDTO> findByCommentId(Long commentId) {
        log.info("CommentService.findByCommentId()");

        return commentMapper.findByCommentId(commentId);
    }

    public Long getCountByPostId(Long postId) {
        log.info("CommentService.getCount()");

        return postService.getCommentsCount(postId);
    }

    public int save(CommentDTO commentDTO, Long memberId) {
        commentDTO.setMemberId(memberId);

        int result = commentMapper.save(commentDTO);
        if (result != 0) {
            postService.increaseCommentsCount(commentDTO.getPostId());
        }

        return result;
    }

    public int delete(Long commentId) {
        log.info("CommentService.delete()");

        Long commentsCount = commentMapper.getCountByParentId(commentId);
        CommentDTO commentDTO = commentMapper.findByCommentId(commentId).get();
        int result = 0;
        if (commentsCount > 0) {
            commentDTO.setDeleted(1);
            result = commentMapper.update(commentDTO);
        } else {
            result = commentMapper.delete(commentId);
        }

        if (result != 0) {
            postService.decreaseCommentsCount(commentDTO.getPostId());
        }

        return result;
    }

    public int update(CommentDTO commentDTO) {
        return commentMapper.update(commentDTO);
    }



}
