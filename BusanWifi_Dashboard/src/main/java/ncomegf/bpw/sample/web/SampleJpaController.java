package ncomegf.bpw.sample.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ncomegf.bpw.sample.domain.entity.User;
import ncomegf.bpw.sample.domain.repository.UserJpaRepo;

//@RequiredArgsConstructor
@RestController
@RequestMapping("/bpw/sampleJpa")
public class SampleJpaController {
	
	
	
	

	
	@Resource
	private UserJpaRepo userJpaRepo;

//    private final UserJpaRepo userJpaRepo;


    @GetMapping(value = "/user")
    public List<User> findAllUser() {
        return userJpaRepo.findAll();
    }

    @PostMapping(value = "/user")
    public User save(User user ) {
    	
    	
        user = User.builder()
                .uid("yumi@naver.com")
                .name("유미")
                .build();
        
        return userJpaRepo.save(user);
    }
}
