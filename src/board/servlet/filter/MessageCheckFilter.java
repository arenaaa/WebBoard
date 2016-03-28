package board.servlet.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import board.dao.MsgDao;
import board.vo.BoardContext;
import board.vo.UserVO;

/**
 * Servlet Filter implementation class MessageCheckFilter
 */
@WebFilter(description = "안읽은 쪽지 확인해줍니다.", urlPatterns = { "/*" })
public class MessageCheckFilter implements Filter {

    /**
     * Default constructor. 
     */
    public MessageCheckFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		HttpServletRequest req = (HttpServletRequest) request;
		/*
		 * 세선을 임의로 만들지 않도록 false를 줘서 호출합니다.
		 */
		HttpSession session = req.getSession(false);
		if( session == null ) {
			chain.doFilter(request, response);
			return ;
		}
		
		UserVO loginUser = (UserVO) session.getAttribute("user"); // ?
		if ( loginUser != null ) {
			/*
			 * 1. msgdao 에 접근하는 과정
			 */
			ServletContext ctx = request.getServletContext();
			BoardContext bctx = (BoardContext) ctx.getAttribute(BoardContext.ATT_BOARD_CONTEXT);
			MsgDao msgDao = bctx.getMsgDao();
			
			int unreadMemo = msgDao.getCountUnread(loginUser.getSeq());
			request.setAttribute("unread", unreadMemo);			
		}
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
