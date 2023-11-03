package com.kosta.board.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;

//Autowired안쓰면은 sqlSession은 비어있는거임. context-mapper.xml
//에 빈객체로 있는거 주입하는거임.
@Repository  
public class BoardDaoImpl implements BoardDao {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public void insertBoard(Board board) throws Exception {
		sqlSession.insert("mapper.board.insertBoard",board);
	}

	@Override
	public List<Board> selectBoardList(Integer row) throws Exception {
		return sqlSession.selectList("mapper.board.selectBoardList",row);
	}

	@Override
	public Integer selectBoardCount() throws Exception {
		return sqlSession.selectOne("mapper.board.selectBoardCount");
	}

	@Override
	public Board selectBoard(Integer num) throws Exception {
		return sqlSession.selectOne("mapper.board.selectBoard",num);
		}

	@Override
	public void updateBoard(Board board) throws Exception {
		sqlSession.update("mapper.board.updateBoard",board);
	}

	@Override
	public void deleteBoard(Integer num) throws Exception {
		sqlSession.delete("mapper.board.deleteBoard",num);
	}

	@Override
	public List<Board> searchBoardList(Map<String, Object> param) throws Exception {
		return sqlSession.selectList("mapper.board.searchBoardList",param);
	}

	@Override
	public Integer searchBoardCount(Map<String, Object> param) throws Exception {
		return sqlSession.selectOne("mapper.board.searchBoardCount",param);
	}

	@Override
	public void updateBoardViewCount(Integer num) throws Exception {
		sqlSession.update("mapper.board.updateBoardViewCount",num);
	}

	@Override
	public Integer selectLikeCount(Integer num) throws Exception {
		return sqlSession.selectOne("mapper.board.selectLikeCount",num);
	}

	@Override
	public void plusBoardLikeCount(Integer num) throws Exception {
		sqlSession.update("mapper.board.plusBoardLikeCount",num);

	}

	@Override
	public void minusBoardLikeCount(Integer num) throws Exception {
		sqlSession.update("mapper.board.minusBoardLikeCount",num);
	}

	@Override
	public void insertFile(FileVo fileVo) throws Exception {
		sqlSession.insert("mapper.board.insertFile",fileVo);
	}

	@Override
	public FileVo selectFile(Integer num) throws Exception {
		return sqlSession.selectOne("mapper.board.selectFile",num);
	}

	@Override
	public void deleteFile(Integer num) throws Exception {
		sqlSession.delete("mapper.board.deleteFile",num);
	}

	@Override
	public Integer selectBoardLike(Map<String, Object> param) throws Exception {
		return sqlSession.selectOne("mapper.boardlike.selectBoardLike",param);
	}

	@Override
	public void insertBoardLike(Map<String, Object> param) throws Exception {
		sqlSession.insert("mapper.boardlike.insertBoardLike",param);
	}

	@Override
	public void deleteBoardLike(Map<String, Object> param) throws Exception {
	 sqlSession.delete("mapper.boardlike.deleteBoardLike",param);
		
	}

	  @Override
	   public void deleteBoardLikeByBoardNum(Integer boardNum) throws Exception {
	      sqlSession.delete("mapper.boardlike.deleteBoardLikeByBoardNum", boardNum);
	   }

	
	
	
	
	
	
	
	

}
