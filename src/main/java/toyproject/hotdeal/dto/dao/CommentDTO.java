package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDTO {

    private Long commentId;
    private Long postId;
    private Long parentId;
    private Long memberId;
    private String commentContent;
    private LocalDateTime createDate;
    private Integer deleted;
}
