package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;
import toyproject.hotdeal.dto.controller.PostSaveDTO;

import java.time.LocalDateTime;

@Data
@Builder
public class PostDTO {

    private Long postId;
    private Long memberId;
    private String category;
    private String title;
    private Integer price;
    private String link;
    private String postContent;
    private Integer hit;
    private LocalDateTime createDate;

    public static PostDTO toPostDTO(PostSaveDTO postSaveDTO) {
        PostDTO postDTO = PostDTO.builder()
                .postId(postSaveDTO.getPostId())
                .memberId(postSaveDTO.getMemberId())
                .category(postSaveDTO.getCategory())
                .title(postSaveDTO.getTitle())
                .price(postSaveDTO.getPrice())
                .link(postSaveDTO.getLink())
                .postContent(postSaveDTO.getContent())
                .build();
        //to be modified when PostDTO have thumbnail column.

        return postDTO;
    }
}
