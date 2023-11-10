package toyproject.hotdeal.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import toyproject.hotdeal.dao.*;
import toyproject.hotdeal.dto.controller.PostSaveDTO;
import toyproject.hotdeal.dto.dao.*;
import toyproject.hotdeal.dto.controller.PostPreviewDTO;
import toyproject.hotdeal.dto.controller.PostViewDTO;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final PostMapper postMapper;
    private final ImageMapper imageMapper;
    private final CommentMapper commentMapper;
    private final VoteMapper voteMapper;
    private final VotesCountMapper votesCountMapper;
    private final AmazonS3Client amazonS3Client;

    @Value("${image.fileDir}")
    private String fileDir;
    @Value("${aws.s3.bucket}")
    private String bucket;

    public Long save(PostSaveDTO postSaveDTO) throws IOException {
        log.info("PostService.save()");

        PostDTO postDTO = PostDTO.toPostDTO(postSaveDTO);
        int result = postMapper.save(postDTO);
        if (result == 0) {
            throw new RuntimeException("해당 게시글 내용을 등록할 수 없음.");
        }

        votesCountMapper.save(postDTO.getPostId());

        if (!postSaveDTO.getImageFile().isEmpty()) {
            ImageDTO imageDTO = toImageDTO(postSaveDTO, postDTO.getPostId());
            imageMapper.save(imageDTO);

            //to be modified when PostDTO have thumbnail column.
        }

        return postDTO.getPostId();
    }

    public PostViewDTO getView(Long postId) {
        log.info("PostService.getView()");

        Optional<PostDTO> result = postMapper.findByPostId(postId);
        if (result.isEmpty()) {
            throw new RuntimeException("해당 게시글을 찾을 수 없음.");
        }

        PostDTO postDTO = result.get();
        PostViewDTO postViewDTO;
        Long votesCount = voteMapper.getCount(postDTO.getPostId());
        List<ImageDTO> imageDTOList = imageMapper.findByPostId(postDTO.getPostId());
        if (imageDTOList.isEmpty()) {
            postViewDTO = PostViewDTO.toPostViewDTO(postDTO, votesCount);
        } else {
            postViewDTO = PostViewDTO.toPostViewDTO(postDTO, votesCount, imageDTOList.get(0));
        }

        return postViewDTO;
    }

    public PostSaveDTO getForm(Long postId) {
        log.info("PostService.getForm()");

        Optional<PostDTO> result = postMapper.findByPostId(postId);
        if (result.isEmpty()) {
            throw new RuntimeException("해당 게시글을 찾을 수 없음.");
        }

        PostDTO postDTO = result.get();

        return PostSaveDTO.toPostSaveDTO(postDTO);
    }

    public Long getMemberId(Long postId) {
        log.info("PostService.getAuthor()");

        return postMapper.findMemberId(postId);
    }

    public int increaseHit(Long postId) {
        log.info("PostService.increaseHit()");

        return postMapper.increaseHit(postId);
    }

    public List<PostPreviewDTO> getPreviewList(PostListParams postListParams) {
        log.info("PostService.getPreviewList()");

        List<PostDTO> postDTOList = postMapper.findPage(postListParams);
        List<PostPreviewDTO> postPreviewDTOList = new ArrayList<>();
        for (PostDTO postDTO : postDTOList) {
            Long commentsCount = commentMapper.getCountByPostId(postDTO.getPostId());
            Long votesCount = voteMapper.getCount(postDTO.getPostId());
            PostPreviewDTO postPreviewDTO = PostPreviewDTO.toPostPreviewDTO(postDTO, commentsCount, votesCount);

            //to be modified when PostDTO have thumbnail column.
            List<ImageDTO> imageDTOList = imageMapper.findByPostId(postDTO.getPostId());
            if (!imageDTOList.isEmpty()) {
                postPreviewDTO.setThumbnail(imageDTOList.get(0).getStoredFileUrl());

            }

            postPreviewDTOList.add(postPreviewDTO);
        }

        return postPreviewDTOList;

    }

    public Long countPosts(PostCountParams postCountParams) {
        return postMapper.countPosts(postCountParams);
    }

    public int delete(Long postId) throws IOException {
        log.info("PostService.delete()");

        List<ImageDTO> imageDTOList = imageMapper.findByPostId(postId);
        if (!imageDTOList.isEmpty()) {

            //to be modified to delete stored image files.
            for (ImageDTO imageDTO : imageDTOList) {
//                Files.deleteIfExists(Paths.get(fileDir + imageDTO.getStoredFileUrl()));

                String storedFilename = imageDTO.getStoredFilename();
                amazonS3Client.deleteObject(bucket, storedFilename);
                imageMapper.delete(imageDTO.getImageId());
            }
        }

        int result = postMapper.delete(postId);
        if (result == 0) {
            throw new RuntimeException("해당 게시글을 삭제할 수 없음.");
        }
        votesCountMapper.delete(postId);

        return result;
    }

    public int update(PostSaveDTO postSaveDTO) throws IOException {
        log.info("PostService.update()");

        PostDTO postDTO = PostDTO.toPostDTO(postSaveDTO);
        int result = postMapper.update(postDTO);
        if (result == 0) {
            throw new RuntimeException("해당 게시글을 수정할 수 없음.");
        }

//        if (!postViewDTO.getImageFile().isEmpty()) {
//            List<ImageDTO> imageDTOList = imageMapper.findByPostId(postDTO.getPostId());
//            if (!imageDTOList.isEmpty()) {
//
//                //to be modified to delete stored image files.
//
//                for (ImageDTO imageDTO : imageDTOList) {
//                    imageMapper.delete(imageDTO.getImageId());
//                }
//            }
//
//            ImageDTO imageDTO = toImageDTO(postViewDTO, postDTO.getPostId());
//            imageMapper.save(imageDTO);
//
//            //to be modified when PostDTO have thumbnail column.
//        }

        return result;
    }

    private ImageDTO toImageDTO(PostSaveDTO postSaveDTO, Long postId) throws IOException {
        MultipartFile thumbnail = postSaveDTO.getImageFile();
        String originalFilename = thumbnail.getOriginalFilename();
        String storedFilename = System.currentTimeMillis() + "_" + originalFilename;
        String filePath = fileDir + storedFilename;
        File storedFile = new File(filePath);
        thumbnail.transferTo(storedFile);

        //must need metadata or acl?
        amazonS3Client.putObject(new PutObjectRequest(bucket, storedFilename, storedFile).withCannedAcl(CannedAccessControlList.PublicRead));
        String storedFileUrl = amazonS3Client.getUrl(bucket, storedFilename).toString();
        storedFile.delete();

        ImageDTO imageDTO = ImageDTO.builder()
                .postId(postId)
                .originalFilename(originalFilename)
                .storedFilename(storedFilename)
                .storedFileUrl(storedFileUrl)
                .build();
        return imageDTO;
    }

}
