package board.servlet.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.service.PostingService;
import board.vo.BoardContext;
import board.vo.PostingVO;
/**
 * 특정 글을 보여줍니다.
 * 
 * @author jihyun
 *
 */
public class ViewPostingAction {

	public String process(BoardContext ctx, HttpServletRequest request, HttpServletResponse response) {
		
		String seq = request.getParameter("id");
		PostingService postingService = ctx.getPostingService();
		PostingVO posting = postingService.findPostingBySeq(Integer.parseInt(seq));
		System.out.println(posting);
		request.setAttribute("p", posting);
		
		return "/WEB-INF/view-postings.jsp";
	}

}
