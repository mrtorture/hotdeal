package toyproject.hotdeal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import toyproject.hotdeal.dao.VoteMapper;
import toyproject.hotdeal.dao.VotesCountMapper;
import toyproject.hotdeal.dto.dao.VoteDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class VoteService {

    private final VoteMapper voteMapper;
    private final VotesCountMapper votesCountMapper;

    public Long getCount(Long postId) {
        log.info("VoteService.getCount()");

        return voteMapper.getCount(postId);
    }

    public int findByPostIdMemberId(Long postId, Long memberId) {
        log.info("VoteService.findByPostIdMemberId()");

        return voteMapper.findByPostIdMemberId(postId, memberId);
    }

    public int save(Long postId, Long memberId) {
        log.info("VoteService.save()");

        VoteDTO voteDTO = VoteDTO.builder()
                .postId(postId)
                .memberId(memberId)
                .build();
        int result = voteMapper.save(voteDTO);
        votesCountMapper.increase(postId);

        return result;
    }

    public int delete(Long postId, Long memberId) {
        log.info("VoteService.delete()");

        int result = voteMapper.delete(postId, memberId);
        votesCountMapper.decrease(postId);

        return result;
    }
}
