package toyproject.hotdeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toyproject.hotdeal.dto.controller.MemberSessionDTO;
import toyproject.hotdeal.service.VoteService;

@Controller
@RequestMapping("/votes")
@RequiredArgsConstructor
@Slf4j
public class VoteController {

    private final VoteService voteService;

    @GetMapping("/check")
    public ResponseEntity findByPostIdMemberId(@RequestParam Long postId,
                                    @SessionAttribute(required = false) MemberSessionDTO memberSessionDTO) {
        log.info("VoteController.findByPostIdMemberId()");

        int count = 0;
        if (memberSessionDTO != null) {
            count = voteService.findByPostIdMemberId(postId, memberSessionDTO.getMemberId());
        }

        return new ResponseEntity(count, HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity getCount(@RequestParam Long postId) {
        log.info("VoteController.getCount()");

        Long votesCount = voteService.getCount(postId);

        return new ResponseEntity(votesCount, HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity save(@RequestParam Long postId,
                               @SessionAttribute MemberSessionDTO memberSessionDTO) {
        log.info("VoteController.save()");

        if (hasVoted(postId, memberSessionDTO)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        voteService.save(postId, memberSessionDTO.getMemberId());

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity delete(@RequestParam Long postId,
                                 @SessionAttribute MemberSessionDTO memberSessionDTO) {
        log.info("VoteController.delete()");

        if (!hasVoted(postId, memberSessionDTO)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        voteService.delete(postId, memberSessionDTO.getMemberId());

        return new ResponseEntity(HttpStatus.OK);

    }

    private boolean hasVoted(Long postId, MemberSessionDTO memberSessionDTO) {

        return memberSessionDTO != null && voteService.findByPostIdMemberId(postId, memberSessionDTO.getMemberId()) != 0;
    }
}
