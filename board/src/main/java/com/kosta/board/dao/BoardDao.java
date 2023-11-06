package com.kosta.board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;

public interface BoardDao {
	
	void insertBoard(Board board)throws Exception;
	List<Board> selectBoardList(Integer row)throws Exception;
	Integer selectBoardCount() throws Exception;
	Board selectBoard(Integer num)throws Exception;
	void updateBoard(Board board)throws Exception;
	void deleteBoard(Integer num)throws Exception;
	List<Board> searchBoardList(Map<String, Object>param)throws Exception;
	Integer searchBoardCount(Map<String, Object>param) throws Exception;
	void updateBoardViewCount(Integer num)throws Exception;
	Integer selectLikeCount(Integer num)throws Exception;
	void plusBoardLikeCount(Integer num) throws Exception;
	void minusBoardLikeCount(Integer num)throws Exception;
	void insertFile(FileVo fileVo)throws Exception;
	FileVo selectFile(Integer num)throws Exception;
	void deleteFile(Integer num)throws Exception;
	
	
	
	//boardlike table
	Integer selectBoardLike(Map<String, Object>param) throws Exception;
	void insertBoardLike(Map<String, Object>param) throws Exception;
	void deleteBoardLike(Map<String, Object>param) throws Exception;
	// 게시글삭제시 해당게시글num을 참조하는 좋아요테이블의 행을 삭제하는 처리를 먼저 해주어야함
	   void deleteBoardLikeByBoardNum(Integer boardNum) throws Exception;
	
}
