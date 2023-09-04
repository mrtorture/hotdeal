package toyproject.hotdeal.dao;

import org.apache.ibatis.annotations.Mapper;
import toyproject.hotdeal.dto.dao.ImageDTO;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ImageMapper {

    int save(ImageDTO imageDTO);

    Optional<ImageDTO> findByImageId(Long imageId);

    List<ImageDTO> findAll();

    List<ImageDTO> findByPostId(Long postId);

    int delete(Long imageId);
}
