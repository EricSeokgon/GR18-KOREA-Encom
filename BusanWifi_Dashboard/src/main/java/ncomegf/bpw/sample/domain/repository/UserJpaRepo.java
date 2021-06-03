package ncomegf.bpw.sample.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ncomegf.bpw.sample.domain.entity.User;


public interface UserJpaRepo extends JpaRepository<User, Long> {
}