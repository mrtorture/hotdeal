package toyproject.hotdeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.hotdeal.dto.controller.MemberSessionDTO;
import toyproject.hotdeal.dto.controller.PagingDTO;
import toyproject.hotdeal.dto.controller.PostPreviewDTO;
import toyproject.hotdeal.dto.controller.PostPreviewParams;
import toyproject.hotdeal.dto.dao.WishDTO;
import toyproject.hotdeal.service.WishService;

import java.util.List;

@Controller
@RequestMapping("/wishes")
@RequiredArgsConstructor
@Slf4j
public class WishController {

    private final WishService wishService;

    @GetMapping("/check")
    public ResponseEntity check(@RequestParam Long postId,
                               @SessionAttribute(required = false) MemberSessionDTO memberSessionDTO) {
        log.info("WishController.findByPostIdMemberId()");

        int count = 0;
        if (memberSessionDTO != null) {
            count = wishService.findByPostIdMemberId(postId, memberSessionDTO.getMemberId());
        }

        return new ResponseEntity(count, HttpStatus.OK);
    }

//    @GetMapping
//    public String getList(@SessionAttribute MemberSessionDTO memberSessionDTO,
//                          @ModelAttribute("postPreviewParams") PostPreviewParams postPreviewParams,
//                          Model model) {
//        log.info("WishController.findByMemberId()");
//
//        postPreviewParams.setBoard("wishes");
//
//        Long postsCount = wishService.count(memberSessionDTO.getMemberId());
//        PagingDTO pagingDTO = new PagingDTO(postPreviewParams.getPageNumber(), postsCount);
//        model.addAttribute("pagingDTO", pagingDTO);
//
//        List<PostPreviewDTO> postPreviewDTOList = wishService.findByMemberId(memberSessionDTO.getMemberId(), pagingDTO.getPageSize(), pagingDTO.getOffset());
//        model.addAttribute("postPreviewDTOList", postPreviewDTOList);
//
//        return "postList";
//    }

    @PostMapping("/new")
    public ResponseEntity create(@RequestParam Long postId,
                                 @SessionAttribute MemberSessionDTO memberSessionDTO) {
        log.info("WishController.create()");

        if (hasWished(postId, memberSessionDTO)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        WishDTO wishDTO = WishDTO.builder()
                .postId(postId)
                .memberId(memberSessionDTO.getMemberId()).build();
        wishService.save(wishDTO);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/delete")
    public ResponseEntity delete(@RequestParam Long postId,
                                 @SessionAttribute MemberSessionDTO memberSessionDTO) {
        log.info("WishController.delete()");

        if (!hasWished(postId, memberSessionDTO)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        wishService.delete(postId, memberSessionDTO.getMemberId());

        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean hasWished(Long postId, MemberSessionDTO memberSessionDTO) {
        return memberSessionDTO != null && wishService.findByPostIdMemberId(postId, memberSessionDTO.getMemberId()) != 0;
    }
}
