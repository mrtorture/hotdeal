package toyproject.hotdeal.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.hotdeal.dto.dao.ImageDTO;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ImageMapperTest {

    @Autowired
    private ImageMapper imageMapper;

    @Test
    void save() {
        ImageDTO imageDTO = ImageDTO.builder()
                .postId(2L)
                .originalFilename("tomato.jpg")
                .storedFilename("1686807613590_tomato.jpg")
                .build();

        imageMapper.save(imageDTO);
        System.out.println("imageDTO = " + imageDTO);

    }

    @Test
    void findByImageId() {
        Optional<ImageDTO> result = imageMapper.findByImageId(2L);
        System.out.println("result.get() = " + result.get());
    }

    @Test
    void findAll() {
        List<ImageDTO> result = imageMapper.findAll();
        for (ImageDTO imageDTO : result) {
            System.out.println("imageDTO = " + imageDTO);
        }

    }

    @Test
    void findByPostId() {
        List<ImageDTO> result = imageMapper.findByPostId(2L);
        for (ImageDTO imageDTO : result) {
            System.out.println("imageDTO = " + imageDTO);
        }

    }

    @Test
    void delete() {
        Optional<ImageDTO> before = imageMapper.findByImageId(3L);
        System.out.println("before.get() = " + before.get());

        imageMapper.delete(3L);

        Optional<ImageDTO> after = imageMapper.findByImageId(3L);
        System.out.println("after = " + after);
    }
}
