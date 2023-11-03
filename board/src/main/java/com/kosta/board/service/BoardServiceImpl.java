package com.kosta.board.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dao.BoardDao;
import com.kosta.board.dto.Board;
import com.kosta.board.dto.FileVo;
import com.kosta.board.dto.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDao boardDao;

	// 게시판 목록
	@Override
    public List<Board> boardListByPage(PageInfo pageInfo) throws Exception {
       int boardCount = boardDao.selectBoardCount();
       if(boardCount==0) return null;
       int allPage = (int)Math.ceil((double)boardCount/10);
       int startPage = (pageInfo.getCurPage()-1)/10*10+1;
       int endPage = Math.min(startPage+10-1,allPage);
       
       pageInfo.setAllPage(allPage);
       pageInfo.setStartPage(startPage);
       pageInfo.setEndPage(endPage);
       if(pageInfo.getCurPage()>pageInfo.getAllPage()) pageInfo.setCurPage(allPage);
       
       int row = (pageInfo.getCurPage()-1)*10+1;
       return boardDao.selectBoardList(row-1);
    
    }

	// 게시글 작성
	@Override
	public Board writeBoard(Board board, MultipartFile file) throws Exception {
		// MultipartFile객체에는 파일에 대한 메타데이터들이 있고, 이 데이터들로 FileVo의 필드값을 초기화한다
		
		// 주의: 조건식 작성할때 file!=null이 && 연산자의 앞부분에 와야함
		// cf. NPE: null의 메서드를 호출하거나 null인 변수에 접근할때 발생하므로 file이 null인 경우에는 file.isEmpty()하는 순간 NPE 발생하면서 프로그램이 비정상종료된다
		if(file!=null && !file.isEmpty()) {
			String dir = "C:/hsa/upload/";
			FileVo fileVo = new FileVo(); // 파일정보를 저장할 객체
			// file 객체에서 파일 정보를 추출하여 fileVo 객체의 필드에 초기화
			fileVo.setDirectory(dir);
			fileVo.setName(file.getOriginalFilename());
			fileVo.setSize(file.getSize());
			fileVo.setContenttype(file.getContentType());
			fileVo.setData(file.getBytes());
			
			// 1. FILE테이블에 인서트
			// 파일정보를 담은 FileVo를 데이터베이스에 저장
			boardDao.insertFile(fileVo);
			Integer num = fileVo.getNum(); // 매퍼 insert문에서 useGeneratedKeys="true" keyProperty="num" 사용했기 때문에 테이블에서 AUTO_INCREMENT된 num이 바로 자동으로 DTO의 필드num에 담겨서 사용 가능
			// 이 파일번호를 이후 파일 업로드와 게시글 정보에 사용한다
		
			// 2. 실제 경로에 파일 업로드
			/* 파일 업로드 시작 */
			File uploadFile = new File(dir+num); // 업로드할 파일을 저장할 경로와 파일 이름을 지정한 File 객체를 생성
			file.transferTo(uploadFile); 
			// 업로드된 파일을 실제 디렉토리에 저장
			// file 객체에서 추출한 파일 데이터를 uploadFile로 복사하여 파일이 디렉토리에 저장됨
			/* 파일 업로드 끝 */
			
			board.setFileurl(num+"");
			// URL은 게시글에 첨부된 파일의 고유번호인 num을 문자열로 변환하여 저장하며, 게시글을 조회할 때 해당 파일을 식별하는데 사용됨
		}
		
		// 3. BOARD테이블에 인서트
		boardDao.insertBoard(board);
		
		// 마찬가지로 매퍼 insert문에서 useGeneratedKeys="true" keyProperty="num" 사용했기 때문에 테이블에서 AUTO_INCREMENT된 num이 바로 자동으로 DTO의 필드num에 담겨서 사용 가능
		return boardDao.selectBoard(board.getNum());
	}

	// 업로드된 파일 출력
	@Override
	public void fileView(Integer num, OutputStream out) throws Exception {
		try {
/*
			// (1) DB테이블에서 파일을 읽어와서 출력하는 방식
			FileVo fileVo = boardDao.selectFile(num); // num을 통해 테이블에서 파일을 읽어서
			FileCopyUtils.copy(fileVo.getData(), out); // 파일의 데이터자체를 뽑아서 OutputStream에 복사한다
			out.flush();
*/			
			// (2) 업로드폴더에서 파일을 읽어와서 출력하는 방식
			FileVo fileVo = boardDao.selectFile(num);
			FileInputStream fis = new FileInputStream(fileVo.getDirectory()+num);
			FileCopyUtils.copy(fis, out);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	// 게시글 상세
	@Override
	public Board boardDetail(Integer num) throws Exception {
		return boardDao.selectBoard(num);
	}

	// 게시글 수정 (쌤이랑 다르게 함-이렇게하면 현재는 되지만 테이블설계시 FK관계가 맺어져있다면 on delete cascade 옵션을 주어야할것이다-부모테이블file의 데이터를 참조하는 자식테이블board의 데이터를 삭제하려고하기 때문)
	@Override
	public Board modifyBoard(Board board, MultipartFile file) throws Exception {
		
		// 0. 기존파일정보(Board가 가진 fileUrl)를 조회하여 파일테이블의 컬럼num 삭제
		// 1. 파라미터로 받은 MultipartFile객체로 FileVo객체를 초기화한뒤 파일테이블에 인서트
		// 2. 실제경로에 파일 업로드
		// 3. Board객체의 필드fileurl의 값에 할당한뒤 게시글테이블에 업데이트
		// 4. DB에서 다시 조회한 수정된 Board객체를 컨트롤러에 리턴
		
		if(file!=null && !file.isEmpty()) {
				
			// 0. board조회하여 board테이블의 fileurl에 저장된 값(file테이블의 num과 같은)을 가져온다
			// 그 num을 인자로 전달하여 deletefromfile을 수행
			Board sboard = boardDao.selectBoard(board.getNum());
			if(sboard.getFileurl()!=null && !sboard.getFileurl().trim().equals("")) {
				Integer fileUrlNum = Integer.parseInt(sboard.getFileurl());
				boardDao.deleteFile(fileUrlNum);
			}
			
			// 1.
			String dir = "C:/hsa/upload/";
			FileVo fileVo = new FileVo();
			fileVo.setDirectory(dir);
			fileVo.setName(file.getOriginalFilename());
			fileVo.setSize(file.getSize());
			fileVo.setContenttype(file.getContentType());
			fileVo.setData(file.getBytes());
			
			boardDao.insertFile(fileVo);
			Integer num = fileVo.getNum();
			
			// 2. 
			File uploadFile = new File(dir+fileVo.getNum());
			file.transferTo(uploadFile);
			
			//기존 파일번호 삭제 위해 받아놓기
			Integer deleteFileNum = null;
			if(board.getFileurl()!=null && ! board.getFileurl().trim().equals("")) {
				deleteFileNum = Integer.parseInt(board.getFileurl());
			}
			
			
			// //4. 파일번호를 board fileUrl에 복사 & board update
			board.setFileurl(num+"");
			boardDao.updateBoard(board);
			
			
			
				
		
		
		
		}
		
		// 3-2. 파일번호를 board fileUrl에 복사 & board update
		boardDao.updateBoard(board);
		
		// 4.
		return boardDao.selectBoard(board.getNum());
	}

	@Override
	public void removeBoard(Integer num) throws Exception {
		Board board = boardDao.selectBoard(num);
		  // 게시글삭제시 해당게시글num을 참조하는 좋아요테이블의 행을 삭제하는 처리를 먼저 해주어야함
	      boardDao.deleteBoardLikeByBoardNum(board.getNum());
		if(board!=null) {
			if(board.getFileurl()!=null) {
				boardDao.deleteFile(Integer.parseInt(board.getFileurl()));
			}
			boardDao.deleteBoard(num);
		}
	}

	@Override
	public Boolean isBoardLike(String userId, Integer boardNum) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("id", userId);
		param.put("num", boardNum);
		Integer likeNum = boardDao.selectBoardLike(param);
		return likeNum==null? false:true;
	}

	@Override
	public Boolean selectBoardLike(String userId, Integer boardNum) throws Exception {
		Map<String, Object> param = new HashMap<>();
		param.put("id", userId);
		param.put("num", boardNum);
		Integer likeNum = boardDao.selectBoardLike(param);
		if(likeNum==null) {
			boardDao.insertBoardLike(param);
			boardDao.plusBoardLikeCount(boardNum); //게시글 넘버 쿼리문이 num (boardnum인지, likenum인지 확인하기) mapper에서 
			return true;
		}else {
			boardDao.deleteBoardLike(param);
			boardDao.minusBoardLikeCount(boardNum);
			return false;
		}
	}

	
	
	
/*
	// 게시글 수정 (선생님 코드)
    @Override
    public Board modifyBoard(Board board, MultipartFile file) throws Exception {
          //순서가 
          //1. 파일정보 DB에 추가
          //2. upload폴더에 파일 업로드 
          //3. board fileUrl에 해당하는 파일 번호를 파일 테이블에서 삭제 
          //4. 파일 번호를 board fileUrl에 복사 
          //5. board 테이블에 추가 
       
       if(file!=null && !file.isEmpty()) {
          //file!=null 무조건 먼저 써줘야한다. !!!! 중요 !!! 
          //파일을 테이블로 저장한다. db 테이블 만들어 줘야함.
          String dir = "c:/hsa/upload/";
          FileVo fileVo = new FileVo();
          fileVo.setDirectory(dir);
          fileVo.setName(file.getOriginalFilename());
          fileVo.setSize(file.getSize());
          fileVo.setContenttype(file.getContentType());
          fileVo.setData(file.getBytes());
          
          boardDao.insertFile(fileVo);//파일 테이블에 insert 하고싶어서 파일 객체 완성 시킴
          
          
          File uploadFile = new File(dir+fileVo.getNum());
          file.transferTo(uploadFile);
          //위에 2줄이 파일 업로드 하는 코드, 수정을해도 upload폴더에 계속 사진이 추가되서 올라감. 
          //2. upload 폴더에 파일 업로드 
          
          //파일 번호를 board fileUrl에 복사 & board update
          Integer deleteFileNum = Integer.parseInt(board.getFileurl());
          board.setFileurl(fileVo.getNum()+"");
          // board dto에 fileurl이 string인데, db table에서 integer여서 +""를 해준다. 
          //4. 파일 번호를 board fileUrl에 복사 
          
          boardDao.updateBoard(board);
          boardDao.deleteFile(deleteFileNum);
          //3. board fileUrl에 해당하는 파일 번호를 파일 테이블에서 삭제 
       }else {
          boardDao.updateBoard(board);
       }
       //db에서 다시 조회해가지고 변경사항 update후에,boardController에 리턴해준다.. 리턴해줘야 컨트롤러에서 뷰에 뿌리기 때문에 
       //5. board 테이블에 추가 
       
       return boardDao.selectBoard(board.getNum());
    }
 */


}