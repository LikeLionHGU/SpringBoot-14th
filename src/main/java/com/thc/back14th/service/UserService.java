package com.thc.back14th.service;

import com.thc.back14th.domain.User;
import com.thc.back14th.dto.UserCreateRequest;
import com.thc.back14th.dto.UserResponse;
import com.thc.back14th.dto.UserUpdateRequest;
import com.thc.back14th.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // 이건 서비스 계층이다!
@RequiredArgsConstructor // final 필드를 받는 생성자를 자동으로 만들어줌
// public UserService(UserRepository userRepository) {
//    this.userRepository = userRepository;
//} 이거 자동으로 만들어줌. 결론적으로 new로 안만들어도 된다~
@Transactional // DB 안전관리. 중간에 실패하면 롤백해서 db 안전하게 유지. C,R에서는 체감 약하지만, Update에서 아주 중요해짐.
public class UserService {

    private final UserRepository userRepository; // Service는 DB작업을 직접 하지 않고 Repository에서 시킨다. service는 규칙, Repository는 DB 역할 분리.


    public Long create(UserCreateRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }//

        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .build();
        // 요청 DTO는 통신용 상자. DB에 넣으려면 Entity(User)가 필요. 그래서 DTO 값을 꺼내서 Entity를 만든다.
        return userRepository.save(user).getId(); // 저장 + id 반환. DB가 id를 만들어
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public List<UserResponse> findAll() {
        return userRepository.findAll() // DB에서 모든 User 엔테리를 가져옴.
                .stream() // List<User>를 stream<User>로 바꾼다. 반복처리 깔끔하게?
                .map(UserResponse::from) // 엔티티를 응답 DTO로 변환. 엔티티에서 필요한 값만 꺼내서 DTO에 담기
                .toList(); // stream으로 가공한 결과를 List로 모으는 것.
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    public UserResponse findOne(Long id) { // id: 찾고 싶은 유저의 pk.
        User user = userRepository.findById(id) // findById는 Optional. 즉, null인 값을 참조해도 NullPointerException이 발생하지 않도록 값을 래퍼로 감싸주는 타입.
                // id로 찾았는데 있을 수도 있고 없을 수도 있음.
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + id));
        // 값이 있으면 그 값을 꺼내서 User에 담고, 값이 없으면 예외를 던져서 바로 종료.
        return UserResponse.from(user); // Entity --> Dto변환.
    } // Repository는 엔티티를 주고, Service는 DTO로 포장해서 Controller에게 준다.

    public void update(Long id, UserUpdateRequest req) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + id));
        user.updateName(req.getName());
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
