package board.vo;

import javax.servlet.ServletContext;

import board.dao.FileDao;
import board.dao.MsgDao;
import board.dao.PostingDao;
import board.dao.UserDao;
import board.service.PostingService;
/**
 * 게시판 웹애플리케이션에서 사용하는 xxxService, xxxDao같은 자원들을 하나씩 생성해서 넣어두는 곳입니다.
 * 
 * @author jihyun
 *
 */
public class BoardContext {

	final public static String ATT_LOGIN_USER = "user";
	public static final String ATT_BOARD_CONTEXT = "BOARD_CONTEXT";
	public static final String ATT_POSTING_SERVICE = "POSTING_SERVICE";
	public static final String ATT_POSTING_DAO = "POSTING_DAO";
	
	private ServletContext servletContext;
	
	private UserDao userDao ;
	
	private PostingDao postingDao ;
	
	private FileDao fileDao;
	private MsgDao msgDao;
	/**
	 * 한페이지에 보여줄 글의 갯수
	 */
	private int pageSize;
	
	
	public BoardContext(ServletContext ctx) {
		this.servletContext = ctx;
	}
	
	public PostingService getPostingService() {
		return (PostingService) servletContext.getAttribute(ATT_POSTING_SERVICE);
	}
	public void setPostingService ( PostingService ps ) {
		servletContext.setAttribute(ATT_POSTING_SERVICE, ps);
	}
	
	public PostingDao getPostingDao ( ) {
		return (PostingDao) servletContext.getAttribute(ATT_POSTING_DAO );
	}

	public void setPostingDao ( PostingDao dao ) {
		servletContext.setAttribute(ATT_POSTING_DAO, dao);
	}
	
	/**
	 * file dao getter
	 * @return
	 */
	public FileDao getFileDao() {
		return this.fileDao;
	}
	/**
	 * file dao setter
	 * @param dao
	 */
	public void setFileDao(FileDao dao) {
		this.fileDao = dao;
		
	}

	public int getPosingPerPage () {
		return 5;
	}

	public String getFileRoot() {
		return "/Users/jihyun";
	}

	public UserDao getUserDao() {
		return this.userDao;
	}

	public void setUserDao(UserDao dao) {
		this.userDao = dao;
		
	}

	public void setMsgDao(MsgDao msgDao) {
		this.msgDao = msgDao ;
	}
	public MsgDao getMsgDao () {
		return this.msgDao;
	}

	/**
	 * pagenation 정보 설정(함페이지에 글을 몇개씩 보여줄 것인지?)
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getPageSize() {
		return this.pageSize;
	}

}
