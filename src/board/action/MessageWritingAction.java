package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.vo.BoardContext;
import board.vo.UserVO;

public class MessageWritingAction {

	public String process(BoardContext ctx, HttpServletRequest request, HttpServletResponse response) {
		/*
		 * 1. 전송하는 userVO longUser
		 */
//		HttpSession session = request.getSession();
		int senderSeq = Integer.parseInt(request.getParameter("sender"));
		UserVO sender = ctx.getUserDao().findBySeq(senderSeq);
		
		/*
		 * 2. 수신하는 사람 
		 */
		int receiverSeq = Integer.parseInt(request.getParameter("receiver"));
		UserVO receiver = ctx.getUserDao().findBySeq(receiverSeq);
		/*
		 * 3. 메세지
		 */
		String msg = request.getParameter("msg");
		
		ctx.getMsgDao().sendMessage(sender, receiver, msg);
		
		return request.getContextPath() + "/postings";
	}

}
