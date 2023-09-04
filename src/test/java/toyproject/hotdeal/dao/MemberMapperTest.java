package toyproject.hotdeal.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.hotdeal.dto.dao.MemberDTO;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class MemberMapperTest {

    @Autowired
    private MemberMapper memberMapper;

    @Test
    void insert() {
        MemberDTO memberDTO = MemberDTO.builder()
                .loginId("joejoe4")
                .loginPassword("1234")
                .memberName("joejoe4")
                .role("ADMIN")
                .build();

        int result = memberMapper.save(memberDTO);

        System.out.println("memberDTO = " + memberDTO);
    }

    @Test
    void findByMemberId() {
        Optional<MemberDTO> result = memberMapper.findByMemberId(9L);

        MemberDTO memberDTO = result.get();

        System.out.println("memberDTO = " + memberDTO);
    }

    @Test
    void findAll() {
        List<MemberDTO> memberDTOList = memberMapper.findAll();

        for (MemberDTO memberDTO : memberDTOList) {
            System.out.println("memberDTO = " + memberDTO);
        }
    }

    @Test
    void findByLoginId() {
        Optional<MemberDTO> result = memberMapper.findByLoginId("joejoe4");
        MemberDTO memberDTO1 = result.get();
        Optional<MemberDTO> result2 = memberMapper.findByLoginId("joejoe5");

        System.out.println("memberDTO1 = " + memberDTO1);
        System.out.println("result2 = " + result2);
    }

    @Test
    void update() {
        Optional<MemberDTO> before = memberMapper.findByMemberId(9L);
        MemberDTO memberDTO = before.get();
        System.out.println("before.get() = " + memberDTO);

        memberDTO.setMemberName("updated");
        memberMapper.update(memberDTO);
        Optional<MemberDTO> after = memberMapper.findByMemberId(9L);
        System.out.println("after.get() = " + after.get());

    }

    @Test
    void delete() {
        Optional<MemberDTO> before = memberMapper.findByMemberId(9L);
        System.out.println("before.get() = " + before.get());

        memberMapper.delete(9L);

        Optional<MemberDTO> after = memberMapper.findByMemberId(9L);
        System.out.println("after = " + after);
    }



}
