package toyproject.hotdeal.dto.controller;

import lombok.Builder;
import lombok.Data;
import toyproject.hotdeal.dto.dao.MemberDTO;

@Data
@Builder
public class MemberViewDTO {

    private Long memberId;
    private String loginId;
    private String role;

    public static MemberViewDTO toMemberViewDTO(MemberDTO memberDTO) {
        MemberViewDTO memberViewDTO = MemberViewDTO.builder()
                .memberId(memberDTO.getMemberId())
                .loginId(memberDTO.getLoginId())
                .role(memberDTO.getRole())
                .build();

        return memberViewDTO;
    }
}
