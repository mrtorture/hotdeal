package toyproject.hotdeal.dto.controller;

import lombok.Builder;
import lombok.Data;
import toyproject.hotdeal.dto.dao.PostDTO;

import java.time.LocalDateTime;

@Data
@Builder
public class PostPreviewDTO {

    private Long postId;
    private Long memberId;
    private String category;
    private String title;
    private Integer price;
    private String thumbnail;
    private Long commentsCount;
    private Integer hit;
    private Long votesCount;
    private LocalDateTime createDate;

    public static PostPreviewDTO toPostPreviewDTO(PostDTO postDTO, Long commentsCount, Long votesCount) {
        PostPreviewDTO postPreviewDTO = PostPreviewDTO.builder()
                .postId(postDTO.getPostId())
                .memberId(postDTO.getMemberId())
                .category(postDTO.getCategory())
                .title(postDTO.getTitle())
                .price(postDTO.getPrice())
                .commentsCount(commentsCount)
                .hit(postDTO.getHit())
                .votesCount(votesCount)
                .createDate(postDTO.getCreateDate())
                .build();

        return postPreviewDTO;
    }

}
