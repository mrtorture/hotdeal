package toyproject.hotdeal.dto.controller;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;
import toyproject.hotdeal.dto.dao.PostDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PostSaveDTO {

    private Long postId;
    private Long memberId;
    @NotBlank
    @Length(min = 4, max = 12)
    private String category;
    @NotBlank
    @Length(min = 2, max = 40)
    private String title;
    @NotNull
    @Min(0)
    private Integer price;
    @Length(max = 40)
    private String link;
    @Length(max = 80)
    private String content;
    private MultipartFile imageFile;

    public static PostSaveDTO toPostFormDTO(PostDTO postDTO) {

        PostSaveDTO postSaveDTO = PostSaveDTO.builder()
                .postId(postDTO.getPostId())
                .memberId(postDTO.getMemberId())
                .category(postDTO.getCategory())
                .title(postDTO.getTitle())
                .price(postDTO.getPrice())
                .link(postDTO.getLink())
                .content(postDTO.getPostContent())
                .build();

        return postSaveDTO;
    }

}
