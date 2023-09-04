package toyproject.hotdeal.dao;

import org.apache.ibatis.annotations.Mapper;
import toyproject.hotdeal.dto.controller.PostPreviewParams;
import toyproject.hotdeal.dto.dao.PostCountParams;
import toyproject.hotdeal.dto.dao.PostDTO;
import toyproject.hotdeal.dto.dao.PostListParams;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PostMapper {
    int save(PostDTO postDTO);

    Optional<PostDTO> findByPostId(Long postId);

    List<PostDTO> findPage(PostListParams postListParams);

    Long countPosts(PostCountParams postCountParams);

    List<PostDTO> findByMemberId(Long memberId);

    Long findMemberId(Long postId);

    int delete(Long postId);

    int update(PostDTO postDTO);

    int increaseHit(Long postId);

}
