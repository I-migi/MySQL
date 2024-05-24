package likelion.mysql.Member;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    public boolean loginService(String name,String password){
        Member member = memberRepository.findByName(name).orElse(null);
        if(member == null){
            return false;
        }
        return password.equals(member.getPassword());
    }

}
