package com.example.bemission2advancedscheduleapp.lv3_6.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateWriterRequestDto {

    @NotBlank(message = "작성자 이름은 필수입니다.")
    private String writerName;

    @NotBlank(message = "작성자 이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String writerEmail;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;
}
