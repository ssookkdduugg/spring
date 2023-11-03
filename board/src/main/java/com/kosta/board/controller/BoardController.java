package com.kosta.board.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.board.dto.Board;
import com.kosta.board.dto.Member;
import com.kosta.board.dto.PageInfo;
import com.kosta.board.service.BoardService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
	
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private HttpSession session;
	
	
	// 메인
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main() {
		return "main";
	}
	
	// 게시판 목록
	@RequestMapping(value = "/boardlist", method = RequestMethod.GET)
	public ModelAndView boardlist(@RequestParam(value="page", required=false, defaultValue="1") Integer page) {
		/* 
		cf. '&page=' 이런식으로 파라미터의 값이 없는(null)인 것은 문제가 되지 않는다. null값으로 초기화될 수 있기 때문에 int가 아니라 Integer로 매개변수를 선언한것. 
		그러나 만일 핸들러메서드에서 파라미터를 받겠다고 매개변수를 선언해두었는데 요청한곳에서 파라미터 자체를 전달하지 않을때 문제가 된다
		*/
		// @RequestParam("page") 하게되면 required속성값이 디폴트true이기 때문에, page라는 파라미터를 전달받지 못하는 상황에서는 이 핸들러메서드가 호출되지 못함
		
		ModelAndView mav = new ModelAndView();
		try {
			PageInfo pageInfo = new PageInfo();
			pageInfo.setCurPage(page);
			List<Board> boardList = boardService.boardListByPage(pageInfo);
			mav.addObject("pageInfo", pageInfo);
			mav.addObject("boardList", boardList);
			mav.setViewName("boardlist");
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.setViewName("error");
		}
		return mav;
	}
	
	// 게시글 작성
	@RequestMapping(value = "/boardwrite", method = RequestMethod.GET)
	public String boardWrite() {
		return "writeform";
	}
	@RequestMapping(value = "/boardwrite", method = RequestMethod.POST)
	public ModelAndView boardWrite(@ModelAttribute Board board, @RequestParam("file") MultipartFile file) {
		// cf. writeform.jsp의 form태그 <form action="boardwrite" method="post" enctype="multipart/form-data" name="boardform">
		// form의 input태그의 name=writer, subject, content는 String이지만, name=file은 String으로 받아 올 수 없으므로 MultipartFile타입으로 받아오기 위하여 따로 @RequestParam으로 가져온다
		// (@ModelAttribute를 통해서 입력값을 알아서 DTO로 싸서 컨트롤러가 받겠다고 할때, DTO는 input의 파라미터name과 필드명이 같기만 하다면, DTO의 필드 수가 가져와야할 파라미터 수보다 더 많은 것은 상관없다)
	
		
		ModelAndView mav = new ModelAndView();
		
		try {
			Board writedboard = boardService.writeBoard(board, file);
			mav.addObject("board", writedboard);
			mav.setViewName("detailform");
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "게시글 작성 오류");
			mav.setViewName("error");
		}
		return mav;
	}
	
	// 업로드된 파일출력
	// 아래와 같이 url의 모양이 uri 뒤로 ?쿼리스트링이 아니라 /를 통해 붙도록 할 수 있다
	@RequestMapping(value="/image/{num}", method=RequestMethod.GET)
	public void imageView(@PathVariable Integer num, HttpServletResponse response) {
		try {
			// 업로드폴더 혹은 테이블에 저장된 num과 대조하여 파일을 읽도록 한다
			boardService.fileView(num, response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	// 게시글 상세
	   @RequestMapping(value="/boarddetail/{num}", method = RequestMethod.GET)
	   // public ModelAndView boardDetail(@RequestParam("num") Integer num) {
	   public ModelAndView boardDetail(@PathVariable Integer num) {
	      ModelAndView mav = new ModelAndView();
	      try {
	         Board board = boardService.boardDetail(num);
	         mav.addObject("board", board);
	         
	         // 로그인한 유저가 좋아요 눌렀는지 여부를 글상세페이지에서 나타내줄것(빨간색 하트)
	         Member user = (Member) session.getAttribute("user");
	         if(user!=null) {
	            Boolean select = boardService.isBoardLike(user.getId(), num);
	            //그 글번호에 유저 id가 체크했는지 안했는지 userId랑,글번호 num(파라미터값)를  select에 담아주고 mnv에 담아준다. 
	            mav.addObject("select", select);
	         }
	         
	         mav.setViewName("detailform");
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         mav.addObject("err", "글 상세 조회 오류");
	         mav.setViewName("error");
	      }
	      return mav;
	   }
	
	
	// 게시글 수정
	@RequestMapping(value = "/boardmodify", method = RequestMethod.GET)
	public ModelAndView boardModify(@RequestParam("num") Integer num) {
		ModelAndView mav = new ModelAndView();
		try {
			Board board = boardService.boardDetail(num);
			mav.addObject("board", board);
			mav.setViewName("modifyform");
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "글 수정 페이지 진입 오류");
			mav.setViewName("error");
		}
		return mav;
	}
	@RequestMapping(value="/boardmodify", method=RequestMethod.POST)
	public ModelAndView boardModify(@ModelAttribute Board board, @RequestParam("file") MultipartFile file) {
		ModelAndView mav = new ModelAndView();
		try {
			Board modifyedBoard = boardService.modifyBoard(board, file);
			mav.addObject("board", modifyedBoard);
			mav.setViewName("detailform");
			
		} catch (Exception e) {
			e.printStackTrace();
			mav.addObject("err", "글 수정폼 제출 오류");
			mav.setViewName("error");
		}
		return mav;
	}
	
	
	/*
	 boardlist.jsp의 get방식 요청 <a href="boarddelete/${board.num }/${pageInfo.curPage}">삭제</a>
	 cf. @PathVariable을 통해서 쿼리스트링이 아니라 url 자체에 붙여서 요청하는 방식을 쓴다면, get/post 두가지로 하나의 url매핑을 할수는 없다
	 또한 @PathVariable은 defaultValue설정이 불가하다
	 */
	@RequestMapping(value="/boarddelete/{num}/{page}", method=RequestMethod.GET)
	public String boardDelete(@PathVariable Integer num, @PathVariable Integer page) {
		try {
			boardService.removeBoard(num);
			return "redirect:/boardlist?page="+page;
					
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	
	@RequestMapping(value="/like", method=RequestMethod.POST)
	@ResponseBody //리턴해서 주는게 view가 아니라 data다. 
	public String boardLike(@RequestParam("num") Integer num) {
		Member  user = (Member)session.getAttribute("user");
		try {
			if(user==null) throw new Exception("로그인 필요");
			Boolean select = boardService.selectBoardLike(user.getId(), num);
			return String.valueOf(select);
		} catch (Exception e) {
			e.printStackTrace();
			return "false";
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}