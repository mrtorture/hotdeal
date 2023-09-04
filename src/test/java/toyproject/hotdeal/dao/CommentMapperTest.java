package toyproject.hotdeal.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import toyproject.hotdeal.dto.controller.CommentViewDTO;
import toyproject.hotdeal.dto.dao.CommentDTO;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CommentMapperTest {

    @Autowired
    private CommentMapper commentMapper;

    @Test
    void save() {
        CommentDTO commentDTO = CommentDTO.builder()
                .postId(8L)
                .memberId(5L)
                .commentContent("역대 최저가 얼마였나요")
                .build();
        commentMapper.save(commentDTO);
        System.out.println("commentDTO = " + commentDTO);
    }

    @Test
    void findByCommentId() {
        Optional<CommentDTO> result = commentMapper.findByCommentId(10L);
        System.out.println("result.get() = " + result.get());
    }

    @Test
    void findAll() {
        List<CommentDTO> result = commentMapper.findAll();
        for (CommentDTO commentDTO : result) {
            System.out.println("commentDTO = " + commentDTO);
        }
    }

    @Test
    void findByPostId() {
        List<CommentViewDTO> result = commentMapper.findByPostId(80L);
        for (CommentViewDTO commentViewDTO : result) {
            System.out.println("commentViewDTO = " + commentViewDTO);
        }
    }

    @Test
    void delete() {
        Optional<CommentDTO> before = commentMapper.findByCommentId(5L);
        System.out.println("before.get() = " + before.get());

        commentMapper.delete(5L);

        Optional<CommentDTO> after = commentMapper.findByCommentId(5L);
        System.out.println("after = " + after);
    }

    @Test
    void update() {
        Optional<CommentDTO> optionalBefore = commentMapper.findByCommentId(6L);
        CommentDTO before = optionalBefore.get();
        System.out.println("before = " + before);

        before.setCommentContent("커멘트 수정");
        commentMapper.update(before);

        Optional<CommentDTO> optionalAfter = commentMapper.findByCommentId(6L);
        System.out.println("optionalAfter.get() = " + optionalAfter.get());


    }
}
