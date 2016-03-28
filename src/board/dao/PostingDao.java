package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mariadb.jdbc.Driver;

import board.util.DBUtil;
import board.vo.BoardContext;
import board.vo.FileVO;
import board.vo.PostingVO;
import board.vo.UserVO;

public class PostingDao {
	static {
		new Driver();
	}

	private BoardContext ctx  ;
	
	private PostingVO g;

	public PostingDao ( BoardContext ctx ) {
		this.ctx = ctx;
	}
//	private String title;
//	private String contents;
//	private int writer;
//	private int nCreated;
	
	private Connection getConnection( String url, String user, String password) {
		Connection con;
		try {
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public String pasing(String data) {
		try {
			data = new String(data.getBytes("8859_1"), "euc-kr");
		}catch (Exception e){ }
		return data;
	}
	
	public List<PostingVO> findPostings(int offset, int length) {
		String query = "select seq, title, contents, ctime, viewcount, recocount, writer from postings order by seq desc limit ?, ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, offset);
			stmt.setInt(2, length);
			
			rs = stmt.executeQuery();
			
//			UserDao userDao = new UserDao(); // FIXME  이렇게 매번 만들면 안좋습니다.
			UserDao userDao = ctx.getUserDao(); // FIXME  이렇게 매번 만들면 안좋습니다.
			ArrayList<PostingVO> postings = new ArrayList<>();
			while ( rs.next() ) {
				Integer seq = rs.getInt("seq");
				String title = rs.getString("title");
				String content = rs.getString("contents");
				String ctime = rs.getString("ctime");
				Integer viewCount = rs.getInt("viewcount");
				Integer recoCount = rs.getInt("recocount");
				Integer writerSeq = rs.getInt("writer");
				UserVO writer = userDao.findBySeq(writerSeq); // TODO 이걸 또 어떻게 구현할 것인지? ????
				
				PostingVO vo = new PostingVO(seq, title, content, ctime, viewCount,recoCount, writer);
//				PostingVO vo = new PostingVO(seq, title, content, ctime, viewCount, writer);
				postings.add(vo);
			}
			return postings;
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
		
	}
	public List<PostingVO> findPostings() {
		String query = "select seq, title, contents, ctime, viewcount, recocount, writer from postings order by seq desc";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			
			rs = stmt.executeQuery();
			
//			UserDao userDao = new UserDao(); // FIXME  이렇게 매번 만들면 안좋습니다.
			UserDao userDao = ctx.getUserDao(); // FIXME  이렇게 매번 만들면 안좋습니다.
			ArrayList<PostingVO> postings = new ArrayList<>();
			while ( rs.next() ) {
				Integer seq = rs.getInt("seq");
				String title = rs.getString("title");
				String content = rs.getString("contents");
				String ctime = rs.getString("ctime");
				Integer viewCount = rs.getInt("viewcount");
				Integer recoCount = rs.getInt("recocount");
				Integer writerSeq = rs.getInt("writer");
				UserVO writer = userDao.findBySeq(writerSeq); // TODO 이걸 또 어떻게 구현할 것인지? ????
				
				PostingVO vo = new PostingVO(seq, title, content, ctime, viewCount,recoCount, writer);
//				PostingVO vo = new PostingVO(seq, title, content, ctime, viewCount, writer);
				postings.add(vo);
			}
			return postings;
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
		
	}
	/**
	 * 특정 글을 하나 읽어서 반환합니다.
	 * @param seq 글번호
	 * @return seq 에 해당하는 글의 인스턴스
	 */
	public PostingVO findBySeq(Integer seq) {
		String query = "select seq, title, contents, ctime, viewcount, recocount, writer from postings where seq = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, seq); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
			
			rs = stmt.executeQuery();
			
			UserDao userDao = new UserDao(); // FIXME  이렇게 매번 만들면 안좋습니다.
			PostingVO posting = null;
			if ( rs.next() ) {
				seq = rs.getInt("seq");
				String title = rs.getString("title");
				String content = rs.getString("contents");
				String ctime = rs.getString("ctime");
				Integer viewCount = rs.getInt("viewcount");
				Integer recoCount = rs.getInt("recocount");
				Integer writerSeq = rs.getInt("writer");
				UserVO writer = userDao.findBySeq(writerSeq); // TODO 이걸 또 어떻게 구현할 것인지? ????
				
				posting = new PostingVO(seq, title, content, ctime, viewCount, recoCount, writer);
				
				FileVO attachedFile = null; // TODO 파일을 읽어들이는 구현이 필요합니다.
				/*
				 * FileDao에게 어떤 조건(?)에 맞는 첨부파일을 하나읽어달라는 요청을 보내야 합니다.
				 */
				FileDao fileDao = ctx.getFileDao(); //이렇게 로컬에서 매번 dao를 만들것인지? 비효율적인 코드입니다.
				
				attachedFile = fileDao.findByPosting(seq);
				
				posting.setAttachedFile(attachedFile);
				
			}
			// TODO files 테이블에서 현재글에 첨부된 파일을 읽어들여서 posting.setAttachedFile ( ... ) 을 호출해주면 됩니다.
			
			return posting;
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
	}

	/**
	 * 주어진 글의 조회수를 증가시킵니다.
	 * @param seq
	 */
	public void updatePosting(PostingVO posting) {
		String query = "update postings set title = ?, contents = ? where seq = ?;";
		Connection con = null;
		PreparedStatement stmt = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, posting.getTitle()); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
			stmt.setString(2, posting.getContents()); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
			stmt.setInt(3, posting.getSeq()); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
			
			int nUpdated = stmt.executeUpdate();
			System.out.println("조회수 갱신 : " + nUpdated);
			
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, null);
		}
	}
		public void increseRecommendcount(Integer seq) {
			String query = "update postings set recocount = recocount + 1 where seq = ?";
			Connection con = null;
			PreparedStatement stmt = null;
			
			con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
			
			try {
				stmt = con.prepareStatement(query);
				stmt.setInt(1, seq); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
				
				int nUpdated = stmt.executeUpdate();
				System.out.println("조회수 갱신 : " + nUpdated);
				
			} catch (SQLException e) {
				// 갑자기 디비 서버랑 연결이 끊김
				// 서버로 보낸 쿼리가 문법이 잘못됐음.
				// 서버 내부에 무슨 문제가 생김.
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				DBUtil.close(con, stmt, null);
			}
		
		
	}
	private void insertRcmd ( Integer userSeq, Integer posting){
		String query = "INSERT INTO RCMD (recommender, posting) values ( ?, ? )";
		Connection con = null;
		PreparedStatement stmt = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, userSeq);
			stmt.setInt(2, posting);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, stmt, null);
		}
		
	}
	public void increaseViewcount(Integer seq) {
		String query = "update postings set viewcount = viewcount + 1 where seq = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, seq); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
			
			int nUpdated = stmt.executeUpdate();
			System.out.println("조회수 갱신 : " + nUpdated);
			
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, null);
		}
		
		
	}
	public void insertPost(PostingVO post) {
		// TODO Auto-generated method stub
		String query = "insert postings(title, contents, writer) values (?, ?, ?)";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query,  Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, post.getTitle()); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
			stmt.setString(2, post.getContents());
			stmt.setInt(3, post.getWriter().getSeq());
			stmt.executeUpdate();
			
			rs = stmt.getGeneratedKeys(); // insert로 삽입된 글의 PK값을 얻어옵니다.
			rs.next(); // 커서를 이동시킵니다.
			int pk = rs.getInt(1); // 1의 의미 - 
			post.setSeq(pk);
			
//			System.out.println("글쓰기 : " + nCreated);
			
			FileVO file = post.getAttachedFile();
			if ( file != null ) {
				// files 테이블로 써넣습니다.
				insertFile(con, post, file);
			}
			con.commit();
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, null);
		}
//		
		
	}
	
	/**
	 * files 테이블에 파일 정보를 써넣습니다.
	 * @param con
	 * @param post
	 * @param file
	 */
	private void insertFile(Connection con, PostingVO post, FileVO file) throws SQLException {
		String query = "insert into files "
				+ " (origin_fname, gen_fname, path, file_length, fkposting )"
				+ " values (?,?, ?, ?, ? )";
		PreparedStatement stmt = null;
		
//		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, file.getOriginFileName());
			stmt.setString(2, file.getGeneratedFileName());
			stmt.setString(3, file.getFilePath());
			stmt.setLong(4, file.getFileLength());
			stmt.setInt(5, post.getSeq());
			
			stmt.executeUpdate();
			
//		} catch(SQLException e) {
//			throw e;
//		} finally {
//			
//		}
		
	}

	@SuppressWarnings("null")
	public void deletePosting(int postSeq) {
		System.out.println("[TODO] 주어진 글을 DB 에서 지웁니다.");
		
		String query = "delete from postings where seq = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, postSeq);
			stmt.executeUpdate();
//			System.out.println("글쓰기 : " + nCreated);
		
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, null);
		}
//		
		
	}
	/**
	 * 주어진 사용작 user가 posting에 추천을 했는지 나타냅니다.
	 * @param user
	 * @param posting
	 * @return
	 */
	public boolean isRecommended ( UserVO user, PostingVO posting) {
		String query = "SELECT count(seq) AS cnt from rcmd WHERE recommender = ? and posting = ? and datediff ( now(), rcmddate) < 1";		
		Connection con = null;
		PreparedStatement stmt = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, user.getSeq());
			stmt.setInt(2, posting.getSeq());
			ResultSet rs = stmt.executeQuery();
			rs.next();
			int rcmd = rs.getInt("cnt");
			return rcmd > 0 ;
//			if ( rcmd > 0 ) {
//				return true;
//			} else {
//				return false;
//			}
		} catch (SQLException e) {
			// 갑자기 디비 서버랑 연결이 끊김
			// 서버로 보낸 쿼리가 문법이 잘못됐음.
			// 서버 내부에 무슨 문제가 생김.
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, null);
		}
//		
	}
	/**
	 * 주어진 사용자가 posting에 추천을 했음.
	 * @param user
	 * @param posting
	 */
	public void recommend ( UserVO user, PostingVO posting) {
		/*
		 * 1. 글쓴 사람과 추천인이 동일하면 throws new RcmdYourPostingException();
		 * 
		 * 
		 * 2. 24시간 내에 추천 기록이 rcmd 테이블에 있으면 throws new RcmdExistException();
		 * 
		 * 3. posting 에서 추천값을 1 증가심
		 * 
		 * 4. rcmd에 추천을 기록함.
		 */
		if (user.getSeq() == posting.getWriter().getSeq()) {
			throw new RcmdYourPostingException("자기 자신의 글에 추천 시도 user : " + user.getUserId() + ", posting : " + posting.getSeq());
		}
		
		if ( isRecommended ( user, posting )){
			throw new ExistingRcmdException("24시간 이내 추천 기록 존재함.");
		}
		
		increseRecommendcount( posting.getSeq() );
		insertRcmd(user.getSeq(), posting.getSeq());
		
	}
	/**
	 * 게시판 글의 갯수
	 * @return
	 */
	public int countPostings() {
		String query = "select count(seq) from postings";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			rs = stmt.executeQuery();
			if ( rs.next() ) {
				return rs.getInt(1);
			} else {
				return 0;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
	}
}

