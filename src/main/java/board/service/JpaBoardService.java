package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.entity.BoardEntity;

public interface JpaBoardService {
  
  List<BoardEntity> selectBoardList() throws Exception;
  void saveBoard(BoardEntity boardEntity, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception;
  BoardEntity selectBoardDetail(int boardIdx) throws Exception;
}
