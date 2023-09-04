package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class VotesCountDTO {

    private Long postId;
    private Integer votesCount;
    private LocalDateTime createDate;
}
