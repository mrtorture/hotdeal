package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VoteDTO {

    private Long postId;
    private Long memberId;
}
