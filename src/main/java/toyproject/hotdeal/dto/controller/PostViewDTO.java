package toyproject.hotdeal.dto.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import toyproject.hotdeal.dto.dao.ImageDTO;
import toyproject.hotdeal.dto.dao.PostDTO;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostViewDTO {

    private Long postId;
    private Long memberId;
    private String category;
    private String title;
    private Integer price;
    private String link;
    private String content;
    private MultipartFile imageFile;
    private String originalFilename;
    private String storedFileUrl;
    private Integer hit;
    private Long votesCount;
    private LocalDateTime createDate;


    public static PostViewDTO toPostViewDTO(PostDTO postDTO, Long votesCount) {
        PostViewDTO postViewDTO = PostViewDTO.builder()
                .postId(postDTO.getPostId())
                .memberId(postDTO.getMemberId())
                .category(postDTO.getCategory())
                .title(postDTO.getTitle())
                .price(postDTO.getPrice())
                .link(postDTO.getLink())
                .content(postDTO.getPostContent())
                .hit(postDTO.getHit())
                .votesCount(votesCount)
                .createDate(postDTO.getCreateDate())
                .build();

        //give voteMapper postId to get vote and copy the value.

        return postViewDTO;
    }

    public static PostViewDTO toPostViewDTO(PostDTO postDTO, Long votesCount, ImageDTO imageDTO) {
        PostViewDTO postViewDTO = toPostViewDTO(postDTO, votesCount);
        postViewDTO.setOriginalFilename(imageDTO.getOriginalFilename());
        postViewDTO.setStoredFileUrl(imageDTO.getStoredFileUrl());

        return postViewDTO;
    }
}
