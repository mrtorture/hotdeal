package toyproject.hotdeal.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.hotdeal.dto.dao.VoteDTO;

import java.util.List;

@SpringBootTest
public class VoteMapperTest {

    @Autowired
    private VoteMapper voteMapper;

    @Test
    void save() {
        VoteDTO voteDTO = VoteDTO.builder()
                .postId(7L)
                .memberId(5L)
                .build();
        voteMapper.save(voteDTO);
    }

    @Test
    void findByPostIdMemberId() {
        int result = voteMapper.findByPostIdMemberId(7L, 5L);
        System.out.println("result = " + result);
    }

    @Test
    void findByPostId() {
        List<VoteDTO> result = voteMapper.findByPostId(7L);
        for (VoteDTO voteDTO : result) {
            System.out.println("voteDTO = " + voteDTO);
        }
    }

    @Test
    void findAll() {
        List<VoteDTO> result = voteMapper.findAll();
        for (VoteDTO voteDTO : result) {
            System.out.println("voteDTO = " + voteDTO);
        }
    }

    @Test
    void delete() {
        int before = voteMapper.findByPostIdMemberId(7L, 5L);
        System.out.println("before = " + before);

        voteMapper.delete(7L, 5L);

        int after = voteMapper.findByPostIdMemberId(7L, 5L);
        System.out.println("after = " + after);
    }
}
