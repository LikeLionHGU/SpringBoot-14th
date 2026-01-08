package com.thc.back14th.dto.user;

import com.thc.back14th.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor // 모든 필드를 받는 생성자 자동 생성.
public class UserResponse {
    private Long id;
    private String name;
    private String email;

    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
    /* Entity --> ResponseDto 변환기
    엔티티를 그대로 내보내지 않고 한번 포장(?)해서 내기.
    왜냐하면, DB구조가 그대로 노출 될 수 있고, 나중에 필드가 추가 되면 API가 깨짐.

     */
}
