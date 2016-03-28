package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.vo.BoardContext;
import board.vo.UserVO;
/**
 * 쪽지 작성 창을 보여줍니다.
 * 
 * @author jihyun
 *
 */
public class PageMessageAction {

	public String process(BoardContext ctx, HttpServletRequest req, HttpServletResponse res) {
		int receiverSeq = Integer.parseInt(req.getParameter("receiver"));// FIXME  없을수도 있음.
		UserVO receiver = ctx.getUserDao().findBySeq(receiverSeq);
		req.setAttribute("r", receiver);
		return "/WEB-INF/msg-page.jsp";
	}

}
