package board.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.dao.MsgDao;
import board.vo.BoardContext;
import board.vo.MsgVO;
import board.vo.UserVO;

public class ViewMsgbox {

	public String process(BoardContext ctx, HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute(BoardContext.ATT_LOGIN_USER) ;
		int loginUserSeq = loginUser.getSeq() ;
		MsgDao dao = ctx.getMsgDao();
		
		String msgnum = request.getParameter("msg");
		
		MsgVO msg = dao.findMsg( Integer.parseInt(msgnum) );
		dao.checkAsRead ( msg.getSeq() );
		msg.setReceiver(loginUser);
		
		List<MsgVO> received = dao.findMsgByReceiver(loginUserSeq);
		
		request.setAttribute("msg", msg);
		request.setAttribute("received_msgs", received);
		
		return "/WEB-INF/view-msg.jsp";
	}
}
