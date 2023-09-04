package toyproject.hotdeal.dao;

import org.apache.ibatis.annotations.Mapper;
import toyproject.hotdeal.dto.dao.VoteDTO;

import java.util.List;

@Mapper
public interface VoteMapper {

    int save(VoteDTO voteDTO);

    int findByPostIdMemberId(Long postId, Long memberId);

    List<VoteDTO> findByPostId(Long postId);

    Long getCount(Long postId);

    List<VoteDTO> findAll();

    int delete(Long postId, Long memberId);
}
