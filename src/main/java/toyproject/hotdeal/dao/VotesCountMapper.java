package toyproject.hotdeal.dao;

import org.apache.ibatis.annotations.Mapper;
import toyproject.hotdeal.dto.dao.VotesCountDTO;

@Mapper
public interface VotesCountMapper {

    int save(Long postId);

    int delete(Long postId);

    int increase(Long postId);

    int decrease(Long postId);


}
