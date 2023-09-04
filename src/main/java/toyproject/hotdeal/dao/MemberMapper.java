package toyproject.hotdeal.dao;

import org.apache.ibatis.annotations.Mapper;
import toyproject.hotdeal.dto.dao.MemberDTO;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {

    int save(MemberDTO memberDTO);

    Optional<MemberDTO> findByMemberId(Long memberId);

    List<MemberDTO> findAll();

    Optional<MemberDTO> findByLoginId(String loginId);

    int findLoginId(String loginId);

    int delete(Long memberId);

    int update(MemberDTO memberDTO);
}
