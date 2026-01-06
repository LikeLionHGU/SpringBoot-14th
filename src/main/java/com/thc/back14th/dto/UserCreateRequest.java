package com.thc.back14th.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
// import lombok.*;이렇게 써도 되지만 처음엔 lombok.Getter쓰는걸 추천.
// dto마다 쓰는 어노테이션을 다를 수 있어서 자신이 어떤 어노테이션을 사용할지 확실히 인지하고 사용하는것이 좋다고 생각함.


@Getter
// @Setter를 안쓰는 이유 : 요청 DTO는 받기만 하면 됨.
public class UserCreateRequest {

    @NotBlank(message = "이름은 필수입니다.") // null X, 빈 문자열 X, 공백 X
    private String name;

    @Email(message = "이메일 형식이 아닙니다.") // 이메일 형식 검증.
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;
}
