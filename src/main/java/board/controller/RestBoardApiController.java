package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import board.dto.BoardDto;
import board.service.BoardService;

@RestController
public class RestBoardApiController {
  @Autowired
  private BoardService boardService;

  @RequestMapping(value = "/api/board", method = RequestMethod.GET)
  public List<BoardDto> BoardList() throws Exception {
    return boardService.selectBoardList();
  }

  @RequestMapping(value = "/api/board/write", method = RequestMethod.POST)
  public void InsetBoard(@RequestBody BoardDto boardDto) throws Exception {
    boardService.insertBoard(boardDto, null);
  }

  @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.GET)
  public BoardDto BoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
    return boardService.selectBoardDetail(boardIdx);
  }

  @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.PUT)
  public String UpdateBoard(@RequestBody BoardDto boardDto) throws Exception {
    boardService.updateBoard(boardDto);
    return "redirect:/board";
  }
  
  @RequestMapping(value = "/api/board/{boardIdx}", method = RequestMethod.DELETE)
  @DeleteMapping(value = "/board/{boardIdx}")
  public String DeleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
    boardService.deleteBoard(boardIdx);
    return "redirect:/board";
  }

}
