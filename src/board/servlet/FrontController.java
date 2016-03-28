package board.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import board.action.FileDownloadAction;
import board.action.MessageWritingAction;
import board.action.PageMessageAction;
import board.action.PageMsgbox;
import board.action.PostWritingAction;
import board.action.ViewMsgbox;
import board.service.PostingService;
import board.service.UserService;
import board.servlet.action.PostingListAction;
import board.servlet.action.ViewPostingAction;
import board.util.WebUtil;
import board.vo.BoardContext;
import board.vo.PostingVO;
import board.vo.UserVO;

/**
 * Servlet implementation class FrontController
 */
@WebServlet(urlPatterns = 
	{"/login", "/join", "/doLogin", 
	 "/myinfo", "/logout", 
	 "/postings", "/posting", "/view", 
	 "/write", "/dowrite",
	 "/update", "/doupdate",
	 "/dodelete",
	 "/dorecommend",
	 "/file",
	 "/message", "/msg", "/msgbox", "/msgbox/view" })
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		ServletContext ctx = getServletContext();
//		String ctxpath = ctx.getContextPath();  // "/board"
		BoardContext ctx = (BoardContext) getServletContext().getAttribute(BoardContext.ATT_BOARD_CONTEXT);
		String uri = request.getRequestURI();
		System.out.println("[GET] uri: " + uri); // "/board/login"
		System.out.println("client IP: " + request.getRemoteAddr());

		if ( "/board/login".equals( uri )){
			request.getRequestDispatcher("login.jsp").forward(request, response);			
		} else if ( "/board/join".equals(uri )) {
			request.getRequestDispatcher("join.jsp").forward(request, response);			
		} else if ( "/board/myinfo".equals(uri) ) {
			// 로그인 확인은 LoginCheckFilter로 옮겼습니다.
			request.getRequestDispatcher("myinfo.jsp").forward(request, response);
		} else if ("/board/logout".equals(uri) ) {
			HttpSession session = request.getSession();
			session.invalidate();
			response.sendRedirect(getServletContext().getContextPath()); // "/board"
		}
		
		else if ( "/board/postings".equals(uri) ) {
			String nextPage = new PostingListAction().process ( ctx, request, response);
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
		else if ( "/board/msgbox".equals(uri) ) {
			
			String nextPage = new PageMsgbox().process ( ctx, request, response);
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
		else if ( "/board/msgbox/view".equals(uri) ) {
			
			String nextPage = new ViewMsgbox().process ( ctx, request, response);
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
		else if ("/board/posting".equals(uri)){
			String nextPage = new ViewPostingAction().process ( ctx, request, response);
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
		
		else if ( "/board/view".equals(uri) ) {
			// x x
			System.out.println("게시판 내용 출력합니다.");
			PostingService postingService = ctx.getPostingService();
			List<PostingVO> postings = postingService.findPostings();
			
			request.setAttribute("postings", postings);
			request.getRequestDispatcher("/WEB-INF/view-postings.jsp").forward(request, response);
		}
		else if ( "/board/write".equals(uri) ) {
			//
			System.out.println("글쓰기 페이지로 이동.");
			request.getRequestDispatcher("/WEB-INF/write.jsp").forward(request, response);
		}
		else if ( "/board/file".equals(uri) ) {
			// 파일 다운로드 요청입니다.
			System.out.println("글쓰기 페이지로 이동.");
			String nextPage = new FileDownloadAction().process(ctx, request, response);
			request.getRequestDispatcher("/WEB-INF/write.jsp").forward(request, response);
		}
		else if ( "/board/message".equals(uri) ) {
			System.out.println("글쓰기 페이지로 이동.");
//			String nextPage = "/WEB-INF/msg-page.jsp";
			String nextPage = new PageMessageAction().process ( ctx, request, response);
			request.getRequestDispatcher(nextPage).forward(request, response);
		}
		else if ( "/board/update".equals(uri) ) { 
			/*
			 * 글 수정 페이지로 가려면
			 * 1 수정할 글에 대한 정보를 FrontController한테 알려줘야합니다.
			 * 2. 그래야지 트정 글에 대한 인스턴스를 dao를 통해서 가져옵니다.
			 * 3. 그리고 이 인스턴스를 request 객체에 담아주고, JSP에서는 input 필드의 값(value)을 채웁니다.
			 */
			// 전처리 과정을 거쳐야 합니다.
			
			int postingSeq = Integer.parseInt(request.getParameter("id"));
			PostingService postingService = ctx.getPostingService();
			PostingVO postingToEdit = postingService.findPostingBySeq(postingSeq);
			
			UserVO loginUser = (UserVO) request.getSession().getAttribute(BoardContext.ATT_LOGIN_USER);			
			// STEP 2. 현재 편집하려는 글을 쓴 사람과 편집 페이지로 이동하려는 사람이 동일해야함.
			if ( ! postingToEdit.getWriter().equals( loginUser ) ) {
				response.sendRedirect(request.getContextPath() + "/postings");
				return ;
			}
			
			request.setAttribute("posting", postingToEdit); // ${posting.title}
			request.getRequestDispatcher("/WEB-INF/update.jsp").forward(request, response);				
			
		}
		
	}

//	private String stripContexPath(HttpServletRequest request) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	/**
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardContext ctx = (BoardContext) getServletContext().getAttribute(BoardContext.ATT_BOARD_CONTEXT);
		request.setCharacterEncoding("utf-8");
		String uri = request.getRequestURI();
		if ( "/board/doLogin".equals(uri) ) {
			// 파일전송은 다른 방식으로 읽어들어야 합니다.
			String userId = request.getParameter("uid");
			String password = request.getParameter("pass");
			
			UserService userService = new UserService();
			UserVO user = userService.login(userId, password);
			System.out.println("login user: " + user);
			if ( user == null ) {
				// 로그인 실패 - 계정을 확인하세요.
				HttpSession session = request.getSession();
				session.setAttribute("fail", true);
				response.sendRedirect("/board/login");
						
			} else {
				// 로그인성공 - 
				// redirect to main page
				HttpSession session = request.getSession();
				session.setAttribute(BoardContext.ATT_LOGIN_USER, user); // session.getAttribute("user") != null
				response.sendRedirect( getServletContext().getContextPath() ); // "/board"
			}
		}
		else if ( "/board/dowrite".equals(uri) ) {
			PostWritingAction action = new PostWritingAction();
			String nextUrl = action.process(ctx, request, response);
			
//			String paramWriter = request.getParameter("writer");
//			String paramTitle = request.getParameter("title");
//			String paramContent = request.getParameter("contents");
//			System.out.println("writer: " + paramWriter);
//			System.out.println("writer: " + paramTitle);
//			System.out.println("writer: " + paramContent);
//			
//			HttpSession session = request.getSession();
//			UserVO loginUser = (UserVO) session.getAttribute(BoardContext.ATT_LOGIN_USER);
//			
//			PostingService postingService = ctx.getPostingService();
//			PostingVO posting = postingService.insertPost ( paramTitle, paramContent, loginUser );
			response.sendRedirect( nextUrl );
		}
		else if ( "/board/doupdate".equals(uri) ) { 
			boolean valid = true;
			int writerSeq = WebUtil.paramInt(request, "writer", -1);
			if ( writerSeq == -1 ) {
				valid = false;
			}
			
			int postingSeq = Integer.parseInt(request.getParameter("pid"));
			
			PostingService postingService = ctx.getPostingService();
			PostingVO postingToEdit = postingService.findPostingBySeq(postingSeq);
			postingToEdit.setTitle(request.getParameter("title"));
			postingToEdit.setContents(request.getParameter("contents"));
			postingService.updatePost(postingToEdit);
			
			// request.setAttribute("posting", postingToEdit); // ${posting.title}
			response.sendRedirect( request.getContextPath() + "/postings");
			
		}
		
		else if("/board/dodelete".equals(uri)) {
			System.out.println("글 지우자!");
			
			int postSeq = WebUtil.paramInt(request, "pid", -1); //
			System.out.println("pid : " + postSeq);
			PostingService postingService = ctx.getPostingService();
			postingService.deletePost ( postSeq );
			
			response.sendRedirect(request.getContextPath() + "/postings");
		}
		
		
		else if("/board/msg".equals(uri)) {
			System.out.println("쪽지!");
//			String nextPage = request.getContextPath() + "/postings";
			String nextPage = new MessageWritingAction().process(ctx, request, response);
			response.sendRedirect(nextPage);
		}
		
		else if ("/board/dorecommend".equals(uri)){
			/*
			 * 1. 현재 로그인한 사용자 접근
			 * 2. 추천대상 글 접근
			 */
			HttpSession session = request.getSession();
			String seq = request.getParameter("pid");
			PostingService postingService = ctx.getPostingService();
//			PostingVO posting = postingService.PostingBySeq(Integer.parseInt(seq));
			PostingVO posting = postingService.findPostingBySeq(Integer.parseInt(seq));
			UserVO loginUser = (UserVO) request.getSession().getAttribute(BoardContext.ATT_LOGIN_USER);
			System.out.println(posting);
			
			if ( loginUser.getSeq().equals( posting.getWriter().getSeq()) ) {
				// 
				session.setAttribute("rcmd_error", "RCMD500"); // 자기가 쓴 글을 추천하려고 했음.
				
			}else if ( postingService.existRcmd ( loginUser, posting )) {
				// 
				session.setAttribute("rcmd_error", "RCMD510"); // 24시간 이내에 이미 추천했음.
				
			} else {
				postingService.recommend( loginUser, posting);				
				
			}
			request.setAttribute("p", posting);
//			request.getRequestDispatcher("/WEB-INF/view-postings.jsp").forward(request, response);
			response.sendRedirect(request.getContextPath() + "/posting?id=" + seq );
		}
		
		
		
	}
	
	
	
	private boolean hasLogin(HttpServletRequest req ) {
		// session에  "user"
		return req.getSession().getAttribute(BoardContext.ATT_LOGIN_USER) != null;
	}
}
