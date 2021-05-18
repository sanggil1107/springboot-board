package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.service.BoardService;

@Controller
public class BoardController {
  @Autowired
  private BoardService boardService;

  @RequestMapping("/board/BoardList.do")
  public ModelAndView BoardList() throws Exception {
    ModelAndView mv = new ModelAndView();

    List<BoardDto> list = boardService.selectBoardList();
    mv.setViewName("board/boardList");
    mv.addObject("list", list);

    return mv;
  }

  @RequestMapping(value="/board/List", method=RequestMethod.GET)
  public String List(Model model) {
	  model.addAttribute("data", "하이");
	  return "board/boardList";
  }

  @RequestMapping("/")
  public String hello() {
    return "hello";
  }
}
