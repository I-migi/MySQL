package likelion.mysql;

import jakarta.servlet.http.HttpSession;
import likelion.mysql.Member.Member;
import likelion.mysql.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private HttpSession httpSession;
    private final MemberRepository memberRepository;

    @GetMapping("/api/user")
    @ResponseBody
    public String getUserInfo() {
        String loggedInUsername = (String) httpSession.getAttribute("loggedInUser");
        return loggedInUsername != null ? loggedInUsername : "";
    }


}

//        if(loggedInUsername != null){
//            Optional<Member> optionalMember = memberRepository.findByName(loggedInUsername);
//            Member member = optionalMember.orElse(null);
//            if(member != null){
//                return ResponseEntity.ok(member);
//            } else{
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//            }
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
//        }