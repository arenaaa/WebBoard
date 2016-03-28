package board.servlet.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.util.WebUtil;
import board.vo.BoardContext;

/**
 * Servlet Filter implementation class LoginCheckFilter
@WebFilter(description = "login을 확인하는 필터입니다.", urlPatterns = { "/*" })
 */
public class LoginCheckFilter implements Filter {

	private List<String> loginUrils = new ArrayList<>();
	{
		loginUrils.add("/myinfo");
		loginUrils.add("/write");
		loginUrils.add("/update");
		loginUrils.add("/doupdate");
		loginUrils.add("/dodelete");
		loginUrils.add("/dorecommend");
	}
	
	
    /**
     * Default constructor. 
     */
    public LoginCheckFilter() {
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
	public void doFilter( ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		// pass the request along the filter chain
		System.out.println("[login check] before");
		// "/update", "/doupdate", "/dodelete", "/write"
		String uri = WebUtil.stripURI ( req );
		if ( loginUrils.contains(uri) && notLogined ( req ) ) {
			System.out.println("[로그인 정보 없음] " + req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/login");
			return ;
		}
		chain.doFilter(request, response);
		System.out.println("[login check] after");
	}

	private boolean notLogined(HttpServletRequest req) {
		
		return req.getSession().getAttribute(BoardContext.ATT_LOGIN_USER) == null;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
