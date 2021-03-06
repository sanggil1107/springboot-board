package board.controller;

import java.net.URLEncoder;
import java.util.List;
import java.io.File;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDto;
import board.dto.BoardFileDto;
import board.service.BoardService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class BoardController {
  //private Logger log = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  private BoardService boardService;

  // @RequestMapping("/board/BoardList.do")
  // public ModelAndView BoardList() throws Exception {
  //   ModelAndView mv = new ModelAndView();

  //   List<BoardDto> list = boardService.selectBoardList();
  //   mv.setViewName("board/boardList");
  //   mv.addObject("list", list);

  //   return mv;
  // }

  @RequestMapping(value="/board/List")
  public String BoardList(Model model) throws Exception {

    log.debug("BoardList");
    List<BoardDto> list = boardService.selectBoardList();
	  model.addAttribute("list", list);
	  // for(int i=0; i< list.size(); i++) {
		//   System.out.println("boardIdx :" + list.get(i).getBoardIdx());
	  // }
	  return "board/boardList";
  }

  @RequestMapping(value="/board/Write")
  public String BoardWrite(Model model) throws Exception {
    return "board/boardWrite";
  }

  @RequestMapping(value="/board/Insert")
  public String InsertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
    boardService.insertBoard(boardDto, multipartHttpServletRequest);
    return "redirect:/board/List";
  }

  @RequestMapping(value="/board/Detail")
  public String BoardDetail(Model model, @RequestParam int boardIdx) throws Exception {
    BoardDto boardDto = boardService.selectBoardDetail(boardIdx);
    model.addAttribute("boardDto", boardDto);

    return "board/boardDetail";
  }

  @RequestMapping(value="/board/Update")
  public String UpdateBoard(BoardDto boardDto) throws Exception {
    boardService.updateBoard(boardDto);
    return "redirect:/board/List";
  }

  @RequestMapping(value="/board/Delete")
  public String DeleteBoard(int boardIdx) throws Exception {
    boardService.deleteBoard(boardIdx);
    return "redirect:/board/List";
  }

  @RequestMapping(value="/board/downloadBoardFile")
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

  @RequestMapping(value="/board/deleteBoardFile")
  public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception {
    boardService.deleteBoardFile(idx, boardIdx);

    return "redirect:/board/Detail?boardIdx="+boardIdx;
  }
}
