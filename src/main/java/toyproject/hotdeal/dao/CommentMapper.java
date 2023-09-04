package toyproject.hotdeal.dao;

import org.apache.ibatis.annotations.Mapper;
import toyproject.hotdeal.dto.controller.CommentViewDTO;
import toyproject.hotdeal.dto.dao.CommentDTO;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommentMapper {

    int save(CommentDTO commentDTO);

    Optional<CommentDTO> findByCommentId(Long commentId);

    List<CommentDTO> findAll();

    List<CommentViewDTO> findByPostId(Long postId);

    Long getCountByPostId(Long postId);

    Long getCountByParentId(Long parentId);

    int delete(Long commentId);

    int update(CommentDTO commentDTO);

}
