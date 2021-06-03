package board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.entity.BoardEntity;
import board.entity.BoardFileEntity;
import board.repository.JpaBoardRepository;

@Service
public class JpaBoardServiceImpl implements JpaBoardService {
  
  @Autowired
  JpaBoardRepository jpaBoardRepository;

  @Autowired
  private FileUtils fileUtils;

  @Override
  public List<BoardEntity> selectBoardList() throws Exception {
    return jpaBoardRepository.findAllByOrderByBoardIdxDesc();
  }

  @Override
  public void saveBoard(BoardEntity  boardEntity, MultipartHttpServletRequest multipartHttpServletRequest, int hitCnt) throws Exception {
    boardEntity.setCreatorId("admin");
    boardEntity.setHitCnt(hitCnt);
    List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
    if(CollectionUtils.isEmpty(list) == false) {
      boardEntity.setFileList(list);
    }
    jpaBoardRepository.save(boardEntity);
  }

  @Override
  public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
    Optional<BoardEntity> optional = jpaBoardRepository.findById(boardIdx);
    if(optional.isPresent()) {
      BoardEntity boardEntity = optional.get();
      boardEntity.setHitCnt(boardEntity.getHitCnt() + 1);
      jpaBoardRepository.save(boardEntity);

      return boardEntity;
    }
    else {
      throw new NullPointerException();
    }
  }

  @Override
  public void deleteBoard(int boardIdx) throws Exception {
    jpaBoardRepository.deleteById(boardIdx);
  }

  @Override
  public BoardFileEntity selectBoardFileInformation(int idx, int boardIdx) throws Exception {
    BoardFileEntity boardFileEntity = jpaBoardRepository.findBoardFile(idx, boardIdx);
    return boardFileEntity;
  }

  @Override
  public void deleteBoardFile(int idx, int boardIdx) throws Exception {
    jpaBoardRepository.deleteBoardFile(idx, boardIdx);
  }
}
