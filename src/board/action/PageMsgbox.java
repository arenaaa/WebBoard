package board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.dao.MsgDao;
import board.vo.BoardContext;
import board.vo.MsgVO;
import board.vo.UserVO;

public class PageMsgbox {

	public String process(BoardContext ctx, HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute(BoardContext.ATT_LOGIN_USER) ;
		int loginUserSeq = loginUser.getSeq() ;
		MsgDao dao = ctx.getMsgDao();
		List<MsgVO> messages = dao.findMsgBySender(loginUserSeq);
		
		// 1. 보낸 쪽지
		request.setAttribute("sent_msgs", messages);
		
		// 2. 받은 쪽지
		List<MsgVO> received = dao.findMsgByReceiver(loginUserSeq);
		request.setAttribute("received_msgs", received);
		
		return "/WEB-INF/msgbox.jsp";
	}

}
