package board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import board.dto.BoardDto;
import board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {
  List<BoardDto> selectBoardList() throws Exception;
  void insertBoard(BoardDto boardDto) throws Exception;
  void updateHitCount(int boardIdx) throws Exception;
  BoardDto selectBoardDetail(int boardIdx) throws Exception;
  void updateBoard(BoardDto boardDto) throws Exception;
  void deleteBoard(int boardIdx) throws Exception;
  void insertBoardFileList(List<BoardFileDto> list) throws Exception;
  List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
}
