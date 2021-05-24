package board.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardFileDto {
  private int idx;
  private int boardIdx;
  private String originalFileName;
  private String storedFilePath;
  private long fileSize;
}
