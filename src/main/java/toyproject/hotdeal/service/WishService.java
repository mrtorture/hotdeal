package toyproject.hotdeal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.hotdeal.dao.CommentMapper;
import toyproject.hotdeal.dao.ImageMapper;
import toyproject.hotdeal.dao.VoteMapper;
import toyproject.hotdeal.dao.WishMapper;
import toyproject.hotdeal.dto.controller.PostPreviewDTO;
import toyproject.hotdeal.dto.dao.ImageDTO;
import toyproject.hotdeal.dto.dao.PostDTO;
import toyproject.hotdeal.dto.dao.WishDTO;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WishService {

    private final WishMapper wishMapper;
    private final ImageMapper imageMapper;
    private final CommentMapper commentMapper;
    private final VoteMapper voteMapper;

    public int findByPostIdMemberId(Long postId, Long memberId) {
        log.info("WishService.findByPostIdMemberId()");

        return wishMapper.findByPostIdMemberId(postId, memberId);
    }

    public List<PostPreviewDTO> findByMemberId(Long memberId, int pageSize, Long offset) {
        log.info("WishService.findByMemberId()");

        List<PostDTO> postDTOList = wishMapper.findByMemberId(memberId, pageSize, offset);
        List<PostPreviewDTO> postPreviewDTOList = new ArrayList<>();
        for (PostDTO postDTO : postDTOList) {
//            Long commentsCount = commentMapper.getCountByPostId(postDTO.getPostId());
            Long votesCount = voteMapper.getCount(postDTO.getPostId());
            PostPreviewDTO postPreviewDTO = PostPreviewDTO.toPostPreviewDTO(postDTO, votesCount);

            List<ImageDTO> imageDTOList = imageMapper.findByPostId(postDTO.getPostId());
            if (!imageDTOList.isEmpty()) {
                postPreviewDTO.setThumbnail(imageDTOList.get(0).getStoredFileUrl());
            }

            postPreviewDTOList.add(postPreviewDTO);
        }

        return postPreviewDTOList;
    }

    public Long count(Long memberId) {
        log.info("WishService.count()");

        return wishMapper.count(memberId);
    }

    public int save(WishDTO wishDTO) {
        log.info("WishService.save()");

        return wishMapper.save(wishDTO);
    }

    public int delete(Long postId, Long memberId) {
        log.info("WishService.delete()");

        return wishMapper.delete(postId, memberId);
    }
}
