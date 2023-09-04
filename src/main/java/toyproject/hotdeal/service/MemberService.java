package toyproject.hotdeal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.hotdeal.dao.MemberMapper;
import toyproject.hotdeal.dto.controller.LoginDTO;
import toyproject.hotdeal.dto.controller.MemberSaveDTO;
import toyproject.hotdeal.dto.controller.MemberViewDTO;
import toyproject.hotdeal.dto.dao.MemberDTO;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;

    public int save(MemberSaveDTO memberSaveDTO) {
        log.info("MemberService.save()");

        MemberDTO memberDTO = MemberDTO.toMemberDTO(memberSaveDTO);

        int result = memberMapper.save(memberDTO);
        if (result == 0) {
            throw new RuntimeException("해당 회원정보 내용을 등록할 수 없음.");
        }

        return result;
    }

    public Optional<MemberDTO> login(LoginDTO loginDTO) {
        log.info("LoginService.login()");

        Optional<MemberDTO> result = memberMapper.findByLoginId(loginDTO.getLoginId());
        if (result.isEmpty()) {
            return result;
        }
        MemberDTO memberDTO = result.get();
        if (!memberDTO.getLoginPassword().equals(loginDTO.getLoginPassword())) {
            return Optional.empty();
        }

        return result;
    }

    public MemberViewDTO getMemberViewDTO(Long memberId) {
        log.info("MemberService.getMemberViewDTO()");

        Optional<MemberDTO> result = memberMapper.findByMemberId(memberId);
        if (result.isEmpty()) {
            throw new RuntimeException("해당 멤버를 찾을수 없습니다.");
        }

        MemberDTO memberDTO = result.get();

        return MemberViewDTO.toMemberViewDTO(memberDTO);
    }

    public int findLoginId(String loginId) {
        log.info("MemberService.findLoginId()");

        return memberMapper.findLoginId(loginId);
    }

}
