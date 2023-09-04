package toyproject.hotdeal.dto.controller;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class LoginDTO {

    @NotBlank
    @Length(min = 6, max = 20)
    @Pattern(regexp = "^[a-z0-9]{1,}$")
    private String loginId;
    @NotBlank
    @Length(min = 6, max = 20)
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[~!@#$%^&*?])[0-9a-zA-Z~!@#$%^&*?]{3,}$")
    private String loginPassword;
}
