package toyproject.hotdeal.dao;

import org.apache.ibatis.annotations.Mapper;
import toyproject.hotdeal.dto.controller.PostPreviewDTO;
import toyproject.hotdeal.dto.dao.PostDTO;
import toyproject.hotdeal.dto.dao.WishDTO;

import java.util.List;

@Mapper
public interface WishMapper {

    int findByPostIdMemberId(Long postId, Long memberId);

    List<PostDTO> findByMemberId(Long memberId, int pageSize, Long offset);

    Long count(Long memberId);

    int save(WishDTO wishDTO);

    int delete(Long postId, Long memberId);
}
