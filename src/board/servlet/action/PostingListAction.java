package board.servlet.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.dao.MsgDao;
import board.service.PostingService;
import board.vo.BoardContext;
import board.vo.PostingVO;
import board.vo.UserVO;
/**
 * 
 *  total : 24개
 *  PS(pageSize) : 5 개씩
 *  MYSQL에서는 아래와 같이 limit 문법으로 읽어올 쿼리의 갯수를 한정합니다.
 *  
 *     LIMIT offset, LEN 
 *     
 * 
 *  pagenum    start    size
 *  --------   -----    ----
 *  1             0       5
 *  2             5       5
 *  
 *  k           (k-1)*PS  PS
 * 
 * @author jihyun
 *
 */
public class PostingListAction {

	public String process(BoardContext ctx, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("게시판 글 출력합니다.");
		/*
		 * 1. offset과 length를 구하기 위해서
		 *  
		 *   - 현재 보여줄페이지 번호(pagenum)와 
		 *   - 한페이지에 보여줄 글의 갯수 정보(pageSize)
		 *   
		 * 가 필요합니다.
		 */
		String pg = request.getParameter("page");
		int pageNum = readPage ( pg );
		int pageSize = ctx.getPageSize();
		int offset = ( pageNum -1 ) * pageSize;
		int length = pageSize;
		
		PostingService postingService = ctx.getPostingService();
		List<PostingVO> postings = postingService.findPostings(offset, length);
		
		request.setAttribute("xxx", postings);
		
		
		/*
		 * 2. 아래의 정보는 페이지 버튼 영역을 출력하기 위해서 필요합니다.
		 * 
		 * 전체 페이지 TP = 24, PS = 5
		 */
		 int TP = postingService.countPostings() ;
		 int totalPage = TP / pageSize;
		 if ( TP % pageSize != 0 ) {
			 totalPage += 1;
		 }
		request.setAttribute("curPage", pageNum);
		request.setAttribute("totalPage", totalPage); // 전체페이지 갯수는?
		
//		/*
//		 * 3.안읽은 쪽지 확인 기능 지원
//		 */
//		MsgDao msgDao = ctx.getMsgDao(); // ?
//		HttpSession session = request.getSession();
//		UserVO loginUser = (UserVO) session.getAttribute("user"); // ?
//		if ( loginUser != null ) {
//			int unreadMemo = msgDao.getCountUnread(loginUser.getSeq());
//			request.setAttribute("unread", unreadMemo);			
//		}
		
		return "/WEB-INF/list-postings.jsp";
	}

	private int readPage(String pg) {
		if ( pg == null ) {
			return 1;
		} else {
			return Integer.parseInt(pg);
		}
	}

}
