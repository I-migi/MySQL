package likelion.mysql.Board;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;

    @Autowired
    private HttpSession httpSession;

    private String getLoggedInUsername(){
        return (String) httpSession.getAttribute("loggedInUser");
    }



    @GetMapping("boards/addBoardForm")
    public String addBoardForm(){
        return "/addBoardForm";
    }

    @PostMapping("boards/addBoardForm")
    public String addBoard(@ModelAttribute("board")Board board, Model model) {
        String loggedInUsername = getLoggedInUsername();
        if(loggedInUsername != null){
            board.setName(loggedInUsername);
            boardRepository.save(board);
        }
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "redirect:/boards";
    }
    @GetMapping("/boards")
    public String boards(Model model){
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "boards";
    }
    @GetMapping("/boards/{boardId}/edit")
    public String editBoardForm(@ModelAttribute("board")Board board
            , Model model,@PathVariable("boardId")Long boardId){
        Optional<Board> editBoardOp = boardRepository.findById(boardId);
        if(editBoardOp.isPresent()) {
            Board editBoard = editBoardOp.get();
            if(httpSession.getAttribute("loggedInUser").equals(editBoard.getName())){
                model.addAttribute("editBoard",editBoard);
                return "/editBoardForm";
            }else {return"redirect:/boards?error=true";}

        } else {
            return "/editBoardForm";
        }
    }

    @PostMapping("/boards/{boardId}/edit")
    public String editBoard(@PathVariable("boardId")long boardId
            ,@RequestParam("title") String title
            ,@RequestParam("name") String name
            ,@RequestParam("content")String content
            ,@ModelAttribute("board")Board board
    ){
        Optional<Board> boardToupdateOp = boardRepository.findById(boardId);
        if(boardToupdateOp.isPresent()){
            Board boardToUpdate = boardToupdateOp.get();
            boardToUpdate.setTitle(title);
            boardToUpdate.setContent(content);
            boardToUpdate.setName(name);
            boardRepository.save(boardToUpdate);
            return "redirect:/boards";
        }

        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}/delete")
    public String deleteBoard(@PathVariable("boardId") long boardId,Model model){
        boardRepository.deleteById(boardId);
        List<Board> boards = boardRepository.findAll();
        model.addAttribute("boards",boards);
        return "redirect:/boards";
    }
    }

