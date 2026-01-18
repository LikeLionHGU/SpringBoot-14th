package com.thc.back14th.repository;
// Repository는 service에서만 쓰기!
import com.thc.back14th.domain.User;
import org.springframework.data.jpa.repository.JpaRepository; // JPA가 제공하는 CRUD 기본 도구 상자(?)

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByGoogleSub(String googleSub);
}
// extends JpaRepository<User, Long>: User는 어떤 엔티티를 다룰지, Long은 User 엔티티의 @Id와 일치해야함!

/* JpaRepository가 기본으로 제공하는 것들!
save(entity)
findById(id)
findAll()
deleteById(id)
count()
existsById(id)
 */

/*
existsBy : 존재여부 확인
findBy : 조회
countBy : 개수
deleteBy : 조건 삭제
 */


/* 왜 Class가 아니라 interface로 구현할까?
구현을 우리가 안하기 때문!
스프링 데이터 JPA가 이 인터페이스를 보고 실제 구현체를 런타임에 자동으로 생성함.
그래서 우리는 '요구사항'만 적고 구현은 스프링이 한다~
 */