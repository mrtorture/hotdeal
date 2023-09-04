package toyproject.hotdeal.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import toyproject.hotdeal.controller.util.RefererUtils;
import toyproject.hotdeal.dto.controller.*;
import toyproject.hotdeal.dto.dao.PostCountParams;
import toyproject.hotdeal.dto.dao.PostListParams;
import toyproject.hotdeal.model.OrderBy;
import toyproject.hotdeal.service.CommentService;
import toyproject.hotdeal.service.PostService;
import toyproject.hotdeal.service.WishService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;
    private final WishService wishService;
    private final CommentService commentService;

    @GetMapping("/new")
    public String getCreateForm(@ModelAttribute("postSaveDTO") PostSaveDTO postSaveDTO) {
        log.info("PostController.getCreateForm()");

        return "postSaveForm";
    }

    @PostMapping("/new")
    public String create(@Validated @ModelAttribute PostSaveDTO postSaveDTO, BindingResult bindingResult,
                         @SessionAttribute MemberSessionDTO memberSessionDTO,
                         RedirectAttributes redirectAttributes) throws IOException {
        log.info("PostController.create()");

        if (bindingResult.hasErrors()) {
            return "postSaveForm";
        }

        postSaveDTO.setMemberId(memberSessionDTO.getMemberId());
        Long postId = postService.save(postSaveDTO);
        redirectAttributes.addAttribute("postId", postId);

        return "redirect:/posts/{postId}";
    }

//    @GetMapping("/{postId}")
//    public String read(@PathVariable Long postId,
//                       @RequestParam(defaultValue = "posts") String board,
//                       @RequestParam(defaultValue = "1") Long pageNumber,
//                       Model model, HttpServletRequest request) {
//        log.info("PostController.read()");
//
//        model.addAttribute("board", board);
//        model.addAttribute("pageNumber", pageNumber);
//
//        PostViewDTO postViewDTO = postService.getView(postId);
//        model.addAttribute("postViewDTO", postViewDTO);
//        postService.increaseHit(postId);
//
//        RefererUtils.setReferer(request, model);
//
//        return "post";
//    }

    @GetMapping("/{postId}")
    public String read(@PathVariable Long postId,
                       @ModelAttribute("postPreviewParams") PostPreviewParams postPreviewParams,
                       Model model, HttpServletRequest request) {
        log.info("PostController.read()");

        PostViewDTO postViewDTO = postService.getView(postId);
        model.addAttribute("postViewDTO", postViewDTO);
        postService.increaseHit(postId);

        RefererUtils.setReferer(request, model);

        return "post";
    }

    @GetMapping("/list")
    public String getList(@ModelAttribute("postPreviewParams") PostPreviewParams postPreviewParams,
                          Model model, HttpServletRequest request) {
        log.info("PostController.getList()");

        PostCountParams postCountParams = PostCountParams.builder()
                .category(postPreviewParams.getCategory())
                .createDate(postPreviewParams.getCreateDate())
                .memberId(null).build();
        Long postsCount = postService.countPosts(postCountParams); //can be moved to PostService
        PagingDTO pagingDTO = new PagingDTO(postPreviewParams.getPageNumber(), postsCount); //can be moved to PostService
        model.addAttribute("pagingDTO", pagingDTO);

        //addAttribute pageNumber?
        PostListParams postListParams = PostListParams.builder()
                .pageSize(pagingDTO.getPageSize())
                .offset(pagingDTO.getOffset())
                .orderBy(postPreviewParams.getOrderBy())
                .category(postPreviewParams.getCategory())
                .createDate(postPreviewParams.getCreateDate())
                .memberId(null).build();
        List<PostPreviewDTO> postPreviewDTOList = postService.getPreviewList(postListParams);
        model.addAttribute("postPreviewDTOList", postPreviewDTOList);

        RefererUtils.setReferer(request, model);

        return "postList";
    }

    @GetMapping("/my")
    public String getMyList(@SessionAttribute MemberSessionDTO memberSessionDTO,
                            @ModelAttribute("postPreviewParams") PostPreviewParams postPreviewParams,
                            Model model, HttpServletRequest request) {
        log.info("PostController.getMyList()");

        postPreviewParams.setBoard("my");

        PostCountParams postCountParams = PostCountParams.builder()
                .category(postPreviewParams.getCategory())
                .createDate(postPreviewParams.getCreateDate())
                .memberId(memberSessionDTO.getMemberId()).build();
        Long postsCount = postService.countPosts(postCountParams);
        PagingDTO pagingDTO = new PagingDTO(postPreviewParams.getPageNumber(), postsCount);
        model.addAttribute("pagingDTO", pagingDTO);

        PostListParams postListParams = PostListParams.builder()
                .pageSize(pagingDTO.getPageSize())
                .offset(pagingDTO.getOffset())
                .orderBy(postPreviewParams.getOrderBy())
                .category(postPreviewParams.getCategory())
                .createDate(postPreviewParams.getCreateDate())
                .memberId(memberSessionDTO.getMemberId()).build();
        List<PostPreviewDTO> postPreviewDTOList = postService.getPreviewList(postListParams);
        model.addAttribute("postPreviewDTOList", postPreviewDTOList);

        RefererUtils.setReferer(request, model);

        return "postList";
    }

    @GetMapping("/search")
    public String getSearchList(@ModelAttribute("postPreviewParams") PostPreviewParams postPreviewParams,
                                Model model, HttpServletRequest request) {
        log.info("PostController.getSearchList()");

        postPreviewParams.setBoard("search");

        String[] searchTokens = null;
        if (postPreviewParams.getKeyword() != null && !postPreviewParams.getKeyword().trim().equals("")) {
            searchTokens = postPreviewParams.getKeyword().split(" ");
        }
        PostCountParams postCountParams = PostCountParams.builder()
                .category(postPreviewParams.getCategory())
                .createDate(postPreviewParams.getCreateDate())
                .searchTokens(searchTokens)
                .memberId(null).build();
        Long postsCount = postService.countPosts(postCountParams);
        PagingDTO pagingDTO = new PagingDTO(postPreviewParams.getPageNumber(), postsCount);
        model.addAttribute("pagingDTO", pagingDTO);

        PostListParams postListParams = PostListParams.builder()
                .pageSize(pagingDTO.getPageSize())
                .offset(pagingDTO.getOffset())
                .orderBy(postPreviewParams.getOrderBy())
                .category(postPreviewParams.getCategory())
                .createDate(postPreviewParams.getCreateDate())
                .searchTokens(searchTokens)
                .memberId(null).build();
        List<PostPreviewDTO> postPreviewDTOList = postService.getPreviewList(postListParams);
        model.addAttribute("postPreviewDTOList", postPreviewDTOList);

        RefererUtils.setReferer(request, model);

        return "postList";
    }

    @GetMapping("/wish")
    public String getWishList(@SessionAttribute MemberSessionDTO memberSessionDTO,
                              @ModelAttribute("postPreviewParams") PostPreviewParams postPreviewParams,
                              Model model, HttpServletRequest request) {
        log.info("PostController.getWishList()");

        postPreviewParams.setBoard("wish");

        Long postsCount = wishService.count(memberSessionDTO.getMemberId());
        PagingDTO pagingDTO = new PagingDTO(postPreviewParams.getPageNumber(), postsCount);
        model.addAttribute("pagingDTO", pagingDTO);

        List<PostPreviewDTO> postPreviewDTOList = wishService.findByMemberId(memberSessionDTO.getMemberId(), pagingDTO.getPageSize(), pagingDTO.getOffset());
        model.addAttribute("postPreviewDTOList", postPreviewDTOList);

        RefererUtils.setReferer(request, model);

        return "postList";
    }

    @GetMapping("/{postId}/delete")
    public String delete(@PathVariable Long postId,
                         @ModelAttribute PostPreviewParams postPreviewParams,
                         @SessionAttribute MemberSessionDTO memberSessionDTO,
                         RedirectAttributes redirectAttributes) throws IOException {
        log.info("PostController.delete()");

        if (!isAuthor(postId, memberSessionDTO)) {
            redirectAttributes.addAttribute("postId", postId);

            return "redirect:/posts/{postId}";
        }

        postService.delete(postId);
        redirectAttributes.addAttribute("board", postPreviewParams.getBoard());
        redirectAttributes.addAttribute("orderBy", postPreviewParams.getOrderBy());
        redirectAttributes.addAttribute("category", postPreviewParams.getCategory());
        redirectAttributes.addAttribute("pageNumber", postPreviewParams.getPageNumber());

        return "redirect:/posts/{board}";

    }

    @GetMapping("/{postId}/update")
    public String getUpdateForm(@PathVariable Long postId,
                                @SessionAttribute MemberSessionDTO memberSessionDTO,
                                RedirectAttributes redirectAttributes, Model model) {
        log.info("PostController.getUpdateForm()");

        if (!isAuthor(postId, memberSessionDTO)) {
            redirectAttributes.addAttribute("postId", postId);

            return "redirect:/posts/{postId}";
        }

        PostSaveDTO postSaveDTO = postService.getForm(postId);
        model.addAttribute("postSaveDTO", postSaveDTO);

        return "postUpdateForm";
    }

    @PostMapping("/{postId}/update")
    public String update(@PathVariable Long postId,
                         @RequestParam String refererURL,
                         @SessionAttribute MemberSessionDTO memberSessionDTO,
                         @ModelAttribute PostSaveDTO postSaveDTO,
                         RedirectAttributes redirectAttributes) throws IOException {
        log.info("PostController.update()");

        if (!isAuthor(postId, memberSessionDTO)) {
            redirectAttributes.addAttribute("postId", postId);

            return "redirect:/posts/{postId}";
        }

        postService.update(postSaveDTO);

        return "redirect:" + refererURL;

    }

    private boolean isAuthor(Long postId, MemberSessionDTO memberSessionDTO) {

        return memberSessionDTO != null && postService.getMemberId(postId).equals(memberSessionDTO.getMemberId());
    }

}
