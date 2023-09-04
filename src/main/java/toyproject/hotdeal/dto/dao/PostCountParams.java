package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostCountParams {

    private List<String> category;
    private List<String> createDate;
    private String[] searchTokens;
    private Long memberId;
}
