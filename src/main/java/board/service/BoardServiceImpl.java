package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import board.dto.BoardDto;
import board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
  @Autowired
  private BoardMapper boardMapper;

  @Override
  public List<BoardDto> selectBoardList() throws Exception {
    return boardMapper.selectBoardList();
  }

  @Override
  public void insertBoard(BoardDto boardDto) throws Exception {
    boardMapper.insertBoard(boardDto);
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
