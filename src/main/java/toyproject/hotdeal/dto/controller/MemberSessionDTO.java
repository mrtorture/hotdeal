package toyproject.hotdeal.dto.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSessionDTO {

    private Long memberId;
    private String memberName;
}
