package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.service.JpaBoardService;

@Controller
public class JpaBoardController {

  @Autowired
  private JpaBoardService jpaBoardService;

  // 게시판 목록
  @RequestMapping(value = "/jpa/board", method = RequestMethod.GET)
  public String BoardList(Model model) throws Exception {
    List<BoardEntity> list = jpaBoardService.selectBoardList();

    model.addAttribute("list", list);
    return "board/jpaBoardList";
  }

  // 게시판 작성 화면
  @RequestMapping(value = "/jpa/board/write", method = RequestMethod.GET)
  public String BoardWrite() throws Exception {
    return "board/jpaBoardWrite";
  }

  // 게시판 작성
  @RequestMapping(value = "/jpa/board/write", method = RequestMethod.POST)
  public String InsertBoard(BoardEntity boardEntity, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
    int hitCnt = boardEntity.getHitCnt();
    jpaBoardService.saveBoard(boardEntity, multipartHttpServletRequest, hitCnt);
    return "redirect:/jpa/board";
  }

  // 게시판 상세 화면
  @RequestMapping
  public String BoardDetail(Model model, @PathVariable("boardIdx") int boardIdx) throws Exception {
    BoardEntity boardEntity = jpaBoardService.selectBoardDetail(boardIdx);
    model.addAttribute("boardDto", boardEntity);
    return "board/jpaBoardDetail";
  }
}
