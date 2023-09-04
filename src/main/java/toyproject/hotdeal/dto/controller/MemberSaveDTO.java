package toyproject.hotdeal.dto.controller;

import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@Builder
public class MemberSaveDTO {

    private Long memberId;
    @NotBlank
    @Length(min = 6, max = 20)
    @Pattern(regexp = "^[a-z0-9]{1,}$")
    private String loginId;
    @NotBlank
    @Length(min = 6, max = 20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[~!@#$%^&*?])[0-9a-zA-Z~!@#$%^&*?]{3,}$")
    private String loginPassword;
    @NotBlank
    @Length(min = 2, max = 10)
    @Pattern(regexp = "^[가-힣]{1,}$")
    private String memberName;
    private String adminKey;
}
