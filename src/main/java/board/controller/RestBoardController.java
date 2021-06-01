package board.controller;

import java.net.URLEncoder;
import java.util.List;
import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;

@Controller
public class RestBoardController {
  @Autowired
  private BoardService boardService;

  // 게시판 목록
  @RequestMapping(value = "/board", method = RequestMethod.GET)
  public String BoardList(Model model) throws Exception {
    List<BoardDto> list = boardService.selectBoardList();
    model.addAttribute("list", list);
    
    return "board/restboardList";
  }

  // 게시판 작성 화면
  @RequestMapping(value = "/board/write", method = RequestMethod.GET)
  public String BoardWrite(Model model) throws Exception {
    return "board/restboardWrite";
  }

  // 게시판 작성
  @RequestMapping(value = "/board/write", method = RequestMethod.POST)
  public String InsertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
    boardService.insertBoard(boardDto, multipartHttpServletRequest);
    return "redirect:/board";
  }

  // 게시판 상세 화면
  @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.GET)
  public String BoardDetail(Model model, @PathVariable("boardIdx") int boardIdx) throws Exception {
    BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
    model.addAttribute("boardDto", boardDto);
    return "board/restboardDetail";
  }

  // 게시판 수정
  @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.PUT)
  public String UpdateBoard(BoardDto boardDto) throws Exception {
    boardService.updateBoard(boardDto);
    return "redirect:/board";
  }

  // 게시판 삭제
  @RequestMapping(value = "/board/{boardIdx}", method = RequestMethod.DELETE)
  public String DeleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
    boardService.deleteBoard(boardIdx);
    return "redirect:/board";
  }

  // 파일 다운로드
  @RequestMapping(value = "/board/file", method = RequestMethod.GET)
  public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
    BoardFileDto boardFileDto = boardService.selectBoardFileInformation(idx, boardIdx);
    if(ObjectUtils.isEmpty(boardFileDto) == false) {
      String fileName = boardFileDto.getOriginalFileName();
      
      byte[] files = FileUtils.readFileToByteArray(new File(boardFileDto.getStoredFilePath()));

      response.setContentType("application/octet-stream");
      response.setContentLength(files.length);
      response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");

      response.getOutputStream().write(files);
      response.getOutputStream().flush();
      response.getOutputStream().close();
    }
  }

  // 파일 삭제
  @RequestMapping(value = "/board/file", method = RequestMethod.DELETE)
  public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
    boardService.deleteBoardFile(idx, boardIdx);

    return "redirect:/board/"+boardIdx;
  }

}
