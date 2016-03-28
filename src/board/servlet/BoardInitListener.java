package board.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import board.dao.FileDao;
import board.dao.MsgDao;
import board.dao.PostingDao;
import board.dao.UserDao;
import board.service.PostingService;
import board.vo.BoardContext;

/**
 * 모든 애플리케이션은 클라이언트 요청을 처리할때 필요한 도구들(xxxService, xxxxDao)
 * 
 * Application Lifecycle Listener implementation class BoardInitListener
 *
 */
@WebListener
public class BoardInitListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public BoardInitListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("[서블릿 종료합니다.]");
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  {
    	System.out.println("[서블릿 컨텍스트가 잘 만들어졌음]");
    	ServletContext ctx = sce.getServletContext(); // servlet context = web.xml
    	
    	BoardContext bctx = new BoardContext( ctx );
    	ctx.setAttribute(BoardContext.ATT_BOARD_CONTEXT, bctx);
    	
    	/*
    	 * 1. dao 등록
    	 */
    	UserDao userDao = new UserDao();
    	bctx.setUserDao ( userDao );
    	
    	FileDao fileDao = new FileDao();
    	bctx.setFileDao ( fileDao );
    	
    	PostingDao postingDao = new PostingDao( bctx );
    	bctx.setPostingDao(postingDao);
    	
    	MsgDao msgDao = new MsgDao( bctx );
    	bctx.setMsgDao ( msgDao );
    	
    	/*
    	 * 2. 서비스 등록
    	 */
    	PostingService postingService = new PostingService( postingDao );
    	bctx.setPostingService(postingService);
    	
    	/*
    	 * 3. pagenation 정보 등록
    	 */
    	String ps = ctx.getInitParameter("page.size");
    	int pageSize = Integer.parseInt(ps);
    	
    	bctx.setPageSize( pageSize );
    	
    }
	
}
