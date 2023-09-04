package toyproject.hotdeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toyproject.hotdeal.dao.CommentMapper;
import toyproject.hotdeal.dto.controller.CommentViewDTO;
import toyproject.hotdeal.dto.controller.MemberSessionDTO;
import toyproject.hotdeal.dto.dao.CommentDTO;
import toyproject.hotdeal.service.CommentService;

import java.util.List;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity getList(@RequestParam Long postId) {
        log.info("CommentController.getList()");

        List<CommentViewDTO> commentViewDTOList = commentService.findByPostId(postId);

        return new ResponseEntity(commentViewDTOList, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity save(@SessionAttribute MemberSessionDTO memberSessionDTO,
                               @ModelAttribute CommentDTO commentDTO) {
        log.info("CommentController.save()");

        commentService.save(commentDTO, memberSessionDTO.getMemberId());
        List<CommentViewDTO> commentViewDTOList = commentService.findByPostId(commentDTO.getPostId());

        return new ResponseEntity(commentViewDTOList, HttpStatus.OK);
    }

    @GetMapping("/{commentId}/delete")
    public ResponseEntity delete(@SessionAttribute MemberSessionDTO memberSessionDTO,
                                 @PathVariable Long commentId) {
        log.info("CommentController.delete()");

        if (!isAuthor(memberSessionDTO, commentId)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        commentService.delete(commentId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{commentId}/update")
    public ResponseEntity update(@SessionAttribute MemberSessionDTO memberSessionDTO,
                                 @PathVariable Long commentId,
                                 @RequestParam String commentContent) {
        log.info("CommentController.update()");

        if (!isAuthor(memberSessionDTO, commentId)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        CommentDTO commentDTO = commentService.findByCommentId(commentId).get();
        commentDTO.setCommentContent(commentContent);
        commentService.update(commentDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean isAuthor(MemberSessionDTO memberSessionDTO, Long commentId) {

        return memberSessionDTO != null && commentService.findByCommentId(commentId).get().getMemberId().equals(memberSessionDTO.getMemberId());
    }
}
