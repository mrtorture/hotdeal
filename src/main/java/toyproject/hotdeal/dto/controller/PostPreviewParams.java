package toyproject.hotdeal.dto.controller;

import lombok.Data;
import toyproject.hotdeal.model.OrderBy;

import java.util.List;

@Data
public class PostPreviewParams {

    private String board = "list";
    private OrderBy orderBy = OrderBy.DATE;
    private List<String> category;
    private List<String> createDate;
    private String keyword;
    private Long pageNumber = 1L;
}
