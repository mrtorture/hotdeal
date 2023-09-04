package toyproject.hotdeal.dto.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PagingDTO {

    private final int pageSize = 6; //can be modified
    private Long offset;
    private Long pageNumber;
    private Long postsCount;
    private Long startPage;
    private Long endPage;
    private boolean hasPrev;
    private boolean hasNext;

    public PagingDTO(Long pageNumber, Long postsCount) {
        this.offset = (pageNumber - 1) * pageSize;
        this.pageNumber = pageNumber;
        this.postsCount = postsCount;

        this.startPage = ((pageNumber - 1) / 10) * 10 + 1;
        if (startPage == 1) {
            hasPrev = false;
        } else {
            hasPrev = true;
        }

        if (postsCount == 0) {
            this.endPage = 1l;
            hasNext = false;
        } else if (postsCount > (startPage - 1) * pageSize + 10 * pageSize) {
            this.endPage = startPage + 9;
            hasNext = true;
        } else {
//            Long quo = postsCount / pageSize;
//            double rem = postsCount * (1.0 / pageSize) - quo;
//            if (rem > 0) {
//                this.endPage = quo + 1;
//            } else {
//                this.endPage = quo;
//            }
            this.endPage = (postsCount - 1) /  pageSize + 1;
            hasNext = false;
        }
    }
}
