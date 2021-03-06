package board.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BoardDto {
	private int boardIdx;
	private String title;
	private String contents;
	private int hitCnt;
	private String creatorId;
	private LocalDateTime createTime;
	private String updaterId;
	private LocalDateTime updateTime;
	private String deletedYn;
	private List<BoardFileDto> fileList;
}
