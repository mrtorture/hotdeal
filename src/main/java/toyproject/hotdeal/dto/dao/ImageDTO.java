package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ImageDTO {

    private Long imageId;
    private Long postId;
    private String originalFilename;
    private String storedFilename;
    private LocalDateTime createDate;

}
