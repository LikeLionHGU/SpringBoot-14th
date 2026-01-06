package com.thc.back14th.controller;

import com.thc.back14th.dto.UserCreateRequest;
import com.thc.back14th.dto.UserResponse;
import com.thc.back14th.dto.UserUpdateRequest;
import com.thc.back14th.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // HTTP 요청 받는 컨트롤러
@RequiredArgsConstructor // final로 선언된 필드들만 모아서 생성자를 자동생성.
// new UserService() 안 하고, 스프링이 만들어준 걸 생성자로 꽂아줌!!
@RequestMapping("/users") // 공통 URL 앞부분 설정.예를들어 https://likelion.users.
public class UserController {

    private final UserService userService; // 컨트롤러는 요청/응답 처리만 하고 진짜 규칙은 service가 함.
    // controller는 입구고, service가 실제 처리하는곳.

    @PostMapping// 추가하기!!
    public Long create(@Valid @RequestBody UserCreateRequest req) {
        return userService.create(req);
    } // 요청 body(JSON) UserCreateRequest DTO에 매핑!
    // @Valid는 DTO에 검증 어노테이션을 실행!, 아까 DTO에 있던 @NotBlank, @Email등 검증 실패하면 400에러 남.

    @GetMapping
    public List<UserResponse> findAll() {
        return userService.findAll(); // 서비스가 DB에서 전부 조회하고 List<UserResponse>로 변환해서 돌려줌.
    }// Entity를 그대로 내보내면 나중에 구조 바뀔 때 API가 깨짐!! 그래서 안전하게 DTO 사용.

    @GetMapping("/{id}")// 경로에 id라는 변수가 들어갔음. 예를들어 도서관에서 한국사 책 하나를 가져오는 느낌.
    public UserResponse findOne(@PathVariable Long id) {
        return userService.findOne(id);
    }// URL {id} 값을 꺼내서 변수 id에 담아둔다. 그래서 PathVariable은 주소에 박힌 값을 뽑아 오는 것.

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest req) {
        userService.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
