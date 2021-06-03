package board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import board.entity.BoardEntity;
import board.entity.BoardFileEntity;

public interface JpaBoardRepository extends CrudRepository<BoardEntity, Integer> {
  List<BoardEntity> findAllByOrderByBoardIdxDesc();

  @Query("SELECT file FROM BoardFileEntity file where board_idx = :boardIdx AND idx = :idx")
  BoardFileEntity findBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);

  @Transactional
  @Modifying
  @Query("DELETE FROM BoardFileEntity file where board_idx = :boardIdx AND idx = :idx")
  void deleteBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);
}
