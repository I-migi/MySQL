package likelion.mysql.Member;

import jakarta.servlet.http.HttpSession;
import likelion.mysql.Board.Board;
import likelion.mysql.Board.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final BoardRepository boardRepository;

    @Autowired
    private HttpSession httpSession;

    @GetMapping("/addMemberForm")
    public String addMemberForm(){
        return "/addMemberForm";
    }

    @PostMapping("/addMemberForm")
    public String addMember(@ModelAttribute("member")Member member){
//        try {
//            Member test = memberRepository.findByName(member.getName()).get();
//        }catch (Exception e){
//            System.out.print("값없다");
//        }
        /*
        List<Member> members = memberRepository.findAllByName(member.getName());

        if(members.toArray().length == 0){
*/
        if(memberRepository.findByName(member.getName()).isEmpty()){
            memberRepository.save(member);
            return "redirect:/";
        } else{

            System.out.print(member.getName());
            return "redirect:/addMemberForm?error=true";
        }
    }

//    @PostMapping("/addMemberForm")
//    public String addMember(@ModelAttribute("member")Member member){
//       try {
//           memberRepository.save(member);
//           return "redirect:/";
//       }catch(Exception e){
//           return "redirect:/addMemberForm?error=ture";
//       }
//    }

    @PostMapping("/boards")
    public String login(@ModelAttribute("member")Member member, Model model){
        if(memberService.loginService(member.getName(),member.getPassword())){
            httpSession.setAttribute("loggedInUser",member.getName());
            List<Board> boards = boardRepository.findAll();
            model.addAttribute("boards",boards);
            return "/boards";
        }
        else {
            return "redirect:/login?error=ture";
        }
    }

    @GetMapping("/logout")
    public String logout(){
        httpSession.removeAttribute("loggedInUser");
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/loginForm";
    }

}
