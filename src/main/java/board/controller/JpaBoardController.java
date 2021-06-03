package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.net.URLEncoder;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
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
  @RequestMapping(value = "/jpa/board/{boardIdx}", method = RequestMethod.GET)
  public String BoardDetail(Model model, @PathVariable("boardIdx") int boardIdx) throws Exception {
    BoardEntity boardEntity = jpaBoardService.selectBoardDetail(boardIdx);
    model.addAttribute("boardDto", boardEntity);
    return "board/jpaBoardDetail";
  }

  // 게시판 수정
  @RequestMapping(value = "/jpa/board/{boardIdx}", method = RequestMethod.PUT)
  public String UpdateBoard(BoardEntity boardEntity) throws Exception {
    int hitCnt = boardEntity.getHitCnt();
    jpaBoardService.saveBoard(boardEntity, null, hitCnt + 1);
    return "redirect:/jpa/board";
  }

  // 게시판 삭제
  @DeleteMapping(value = "/jpa/board/{boardIdx}")
  public String DeleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
    jpaBoardService.deleteBoard(boardIdx);
    return "redirect:/jpa/board";
  }

  // 파일 다운로드
  @RequestMapping(value = "/jpa/board/file", method = RequestMethod.GET)
  public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception {
    BoardFileEntity boardFileEntity = jpaBoardService.selectBoardFileInformation(idx, boardIdx);
    if(ObjectUtils.isEmpty(boardFileEntity) == false) {
      String fileName = boardFileEntity.getOriginalFileName();
      
      byte[] files = FileUtils.readFileToByteArray(new File(boardFileEntity.getStoredFilePath()));

      response.setContentType("application/octet-stream");
      response.setContentLength(files.length);
      response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");

      response.getOutputStream().write(files);
      response.getOutputStream().flush();
      response.getOutputStream().close();
    }
  }

  // 파일 삭제
  @RequestMapping(value = "/jpa/board/file", method = RequestMethod.DELETE)
  public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
    jpaBoardService.deleteBoardFile(idx, boardIdx);

    return "redirect:/jpa/board/" + boardIdx;
  }
}
