package toyproject.hotdeal.dao;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.hotdeal.dto.dao.WishDTO;

@SpringBootTest
public class WishMapperTest {

    @Autowired
    private WishMapper wishMapper;

    @Test
    void save() {
        WishDTO wishDTO = WishDTO.builder()
                .postId(80L)
                .memberId(5L)
                .build();

        int result = wishMapper.save(wishDTO);
        System.out.println("save() result = " + result);
    }

    @Test
    void findByPostIdMemberId() {
        int result = wishMapper.findByPostIdMemberId(80L, 5L);

        System.out.println("findByPostIdMemberId() result = " + result);
    }
}
