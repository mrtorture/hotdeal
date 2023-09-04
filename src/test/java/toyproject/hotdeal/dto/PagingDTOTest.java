package toyproject.hotdeal.dto;

import org.junit.jupiter.api.Test;
import toyproject.hotdeal.dto.controller.PagingDTO;

public class PagingDTOTest {

    @Test
    void pageDTO() {
        PagingDTO pagingDTO1 = new PagingDTO(4l, 200l);
        System.out.println("pageDTO1 = " + pagingDTO1);
        PagingDTO pagingDTO2 = new PagingDTO(4l, 55l);
        System.out.println("pageDTO2 = " + pagingDTO2);
        PagingDTO pagingDTO3 = new PagingDTO(1l, 0l);
        System.out.println("pageDTO3 = " + pagingDTO3);
        PagingDTO pagingDTO4 = new PagingDTO(1l, 1l);
        System.out.println("pageDTO4 = " + pagingDTO4);
        PagingDTO pagingDTO5 = new PagingDTO(1l, 9l);
        System.out.println("pageDTO5 = " + pagingDTO5);
        PagingDTO pagingDTO6 = new PagingDTO(1l, 10l);
        System.out.println("pageDTO6 = " + pagingDTO6);
        PagingDTO pagingDTO7 = new PagingDTO(1l, 11l);
        System.out.println("pageDTO7 = " + pagingDTO7);
        PagingDTO pagingDTO8 = new PagingDTO(1l, 100l);
        System.out.println("pageDTO8 = " + pagingDTO8);
        PagingDTO pagingDTO9 = new PagingDTO(1l, 101l);
        System.out.println("pageDTO9 = " + pagingDTO9);
        PagingDTO pagingDTO10 = new PagingDTO(14l, 200l);
        System.out.println("pageDTO10 = " + pagingDTO10);
        PagingDTO pagingDTO11 = new PagingDTO(14l, 165l);
        System.out.println("pageDTO11 = " + pagingDTO11);
        PagingDTO pagingDTO12 = new PagingDTO(14l, 139l);
        System.out.println("pageDTO12 = " + pagingDTO12);
        PagingDTO pagingDTO13 = new PagingDTO(14l, 140l);
        System.out.println("pageDTO13 = " + pagingDTO13);
        PagingDTO pagingDTO14 = new PagingDTO(14l, 141l);
        System.out.println("pageDTO14 = " + pagingDTO14);

    }
}
