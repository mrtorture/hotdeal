package toyproject.hotdeal.dto.dao;

import lombok.Builder;
import lombok.Data;
import toyproject.hotdeal.dto.controller.PostPreviewParams;
import toyproject.hotdeal.model.OrderBy;

import java.util.List;

@Data
@Builder
public class PostListParams {

    private int pageSize;
    private Long offset;
    private OrderBy orderBy;
    private List<String> category;
    private List<String> createDate;
    private String[] searchTokens;
    private Long memberId;

}
