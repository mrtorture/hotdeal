package toyproject.hotdeal.dto.controller;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentViewDTO {

    private Long commentId;
    private Long postId;
    private Long parentId;
    private Long memberId;
    private String memberName;
    private String commentContent;
    private LocalDateTime createDate;
    private Integer deleted;
}
