package board.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDto;
import board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardServiceImpl implements BoardService {
  @Autowired
  private BoardMapper boardMapper;

  @Override
  public List<BoardDto> selectBoardList() throws Exception {
    return boardMapper.selectBoardList();
  }

  @Override
  public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
    //boardMapper.insertBoard(boardDto);
    if(ObjectUtils.isEmpty(multipartHttpServletRequest) == false) {
      Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
      String name;
      while(iterator.hasNext()) {
        name = iterator.next();
        log.debug("file tag name : " + name);
        List<MultipartFile> list = multipartHttpServletRequest.getFiles(name);
        for(MultipartFile multipartFile : list) {
          log.debug("start file information");
          log.debug("file name : " + multipartFile.getOriginalFilename());
          log.debug("file size : " + multipartFile.getSize());
          log.debug("file context type : " + multipartFile.getContentType());
          log.debug("end file information.\n");
        }
      }

    }
  }

  @Override
  public BoardDto selectBoardDetail(int boardIdx) throws Exception {
    boardMapper.updateHitCount(boardIdx);

    BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx);
    return boardDto;
  }

  @Override
  public void updateBoard(BoardDto boardDto) throws Exception {
    boardMapper.updateBoard(boardDto);
  }

  @Override
  public void deleteBoard(int boardIdx) throws Exception {
    boardMapper.deleteBoard(boardIdx);
  }
}
