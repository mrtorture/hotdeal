package toyproject.hotdeal.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.hotdeal.dto.dao.PostDTO;
import toyproject.hotdeal.dto.dao.PostListParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    void insert() {
        PostDTO postDTO = PostDTO.builder()
                .memberId(6L)
                .category("FOOD")
                .title("롯데햄 의성마늘 프랑크")
                .price(13680)
                .link("https://www.naver.com")
                .build();
        postMapper.save(postDTO);
        System.out.println("postDTO = " + postDTO);
    }

    @Test
    void findByPostId() {
        Optional<PostDTO> result = postMapper.findByPostId(74L);
        System.out.println("result.get() = " + result.get());
    }

    @Test
    void findPage() {
        PostListParams postListParams = PostListParams.builder()
                .pageSize(10)
                .offset(0L)
                .category(new ArrayList<String>())
                .build();
        List<PostDTO> result = postMapper.findPage(postListParams);
        for (PostDTO postDTO : result) {
            System.out.println("postDTO = " + postDTO);
        }
    }

    @Test
    void findByMemberId() {
        List<PostDTO> result = postMapper.findByMemberId(6L);
        for (PostDTO postDTO : result) {
            System.out.println("postDTO = " + postDTO);
        }
    }

    @Test
    void update() {
        Optional<PostDTO> optionalBefore = postMapper.findByPostId(74L);
        PostDTO before = optionalBefore.get();
        System.out.println("before = " + before);

        before.setTitle("롯데 의성마늘 프랑크 수정");

        postMapper.update(before);
        Optional<PostDTO> optionalAfter = postMapper.findByPostId(74L);
        PostDTO after = optionalAfter.get();
        System.out.println("after = " + after);

    }

    @Test
    void delete() {
        Optional<PostDTO> before = postMapper.findByPostId(74L);
        System.out.println("before.get() = " + before.get());
        
        postMapper.delete(74L);

        Optional<PostDTO> after = postMapper.findByPostId(74L);
        System.out.println("after = " + after);
    }

}
