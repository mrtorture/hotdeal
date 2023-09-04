package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;
import toyproject.hotdeal.dto.controller.MemberSaveDTO;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberDTO {

    private Long memberId;
    private String loginId;
    private String loginPassword;
    private String memberName;
    private String role;
    private LocalDateTime joinDate;

    public static MemberDTO toMemberDTO(MemberSaveDTO memberSaveDTO) {
        MemberDTO memberDTO = MemberDTO.builder()
                .memberId(memberSaveDTO.getMemberId())
                .loginId(memberSaveDTO.getLoginId())
                .loginPassword(memberSaveDTO.getLoginPassword())
                .memberName(memberSaveDTO.getMemberName())
                .build();

        if (memberSaveDTO.getAdminKey().equals("1234")) {
            memberDTO.setRole("ADMIN");
        } else {
            memberDTO.setRole("USER");
        }

        return memberDTO;
    }

}
