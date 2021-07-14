package com.example.order.config.jwt;

import com.example.order.entity.Member;
import com.example.order.reposiroty.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    /**
     * 토큰에 저장된 유저 정보를 활용해야 하기 때문에 CustomUserDetatilService 라는 이름의 클래스를 만들고
     * UserDetailsService를 상속받아 재정의 하는 과정을 진행합니다.
     */
    private final MemberRepository memberRepository;

    /**
     * SpringSecurity는 UserDetails 객체를 통해 권한 정보를 관리하기 때문에 Account 클래스에 UserDetails 를 구현하고 추가 정보를 재정의 해야 합니다.
     * Account Entity와 UserDetails는 구분할 수도 같은 클래스에서 관리할 수도 있습니다.
     * 여기에서는 같은 클래스에서 관리하는 방법을 사용하도록 하겠습니다.
     * @return
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        return new CustomUserDetails(member);
    }
}
