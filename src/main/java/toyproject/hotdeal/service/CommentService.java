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

        return commentMapper.getCountByPostId(postId);
    }

    public int save(CommentDTO commentDTO, Long memberId) {
        commentDTO.setMemberId(memberId);

        return commentMapper.save(commentDTO);
    }

    public int delete(Long commentId) {
        log.info("CommentService.delete()");

        Long commentsCount = commentMapper.getCountByParentId(commentId);
        if (commentsCount > 0) {
            CommentDTO commentDTO = commentMapper.findByCommentId(commentId).get();
            commentDTO.setDeleted(1);
            return commentMapper.update(commentDTO);
        }

        return commentMapper.delete(commentId);
    }

    public int update(CommentDTO commentDTO) {
        return commentMapper.update(commentDTO);
    }



}
