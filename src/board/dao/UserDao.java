package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mariadb.jdbc.Driver;

import board.util.DBUtil;
import board.vo.UserVO;

/**
 * User 1----N Posting
 *            
 * users                 <--->    UserDTO
 * -----
 * column0                         field0
 * column1                        field1
 * ...
 * 
 * @author jihyun
 *
 */
public class UserDao {

	static {
		// 이렇게 mysql 드라이버를 등록해 줍니다.
		new Driver();
	}
	public UserDao () {
		
	}
	
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
	
	/**
	 * 주어진 P 에 해당하는 사용자를 반환합니다.
	 * @param userSeq
	 * @return
	 */
	public UserVO findBySeq ( Integer userSeq ) {
		String query = "select seq, userid, email, password from users where seq = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			/*
			 * 1. DB서버로 보낼 쿼리를 구성합니다.
			 */
			stmt = con.prepareStatement(query);
			stmt.setInt(1, userSeq); // 첫번째물음표위치(1)에 id값을 채워넣습니다.
			rs = stmt.executeQuery(); // sql이 디비 서버로 전송됩니다. 그리고 그 결과를 반환합니다.
			
			/*
			 * 3. next() 를 호출해서 커서를 이동시킵니다.
			 *    이동시킨 위치에서 읽어올 데이터가 있으면 next()는 true를 반환합니다.
			 */
			if ( rs.next() ) {
				int seq = rs.getInt("seq"); // 2
				String uid = rs.getString("userid");
				String email = rs.getString("email");
				String pw = rs.getString("password");
				
				UserVO user = new UserVO(seq, uid, email, pw );
				return user;
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
	}
	public UserVO login ( String userId, String password ) {
		String query = "select seq, userid, email, password from users where userid = ? and password = ?";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			/*
			 * 1. DB서버로 보낼 쿼리를 구성합니다.
			 */
			stmt = con.prepareStatement(query);
			stmt.setString(1, userId); // 첫번째물음표위치(1)에 id값을 채워넣습니다.
			stmt.setString(2, password); // 두번째 물음표위치(2)에 password값을 채워넣습니다.
			/* 데이터베이스에서 아래와같이쿼리를 최종 구성해줍니다.
			 * 
			 * select seq, userid, email, password 
			 * from users 
			 * where userid = 'tom' and password = '2222'";
			 */
			
			/*
			 * 2.쿼리 구성 내용을 서버로 전송하고 그 실행 결과를 아래와 같이 받습니다.
			 *   result set은 2차원 평면상의 모양으로생각하면 좋습니다.
			 *   
			 *    
			 *     +-------+-----+--------+----+
			 *   c |   2   | tom | tom@   |2222|
			 *     +-------+-----+--------+----+
			 *     
			 *    
			 *   c
			 *     +-------+-----+--------+----+
			 *     +-------+-----+--------+----+
			 */
			rs = stmt.executeQuery(); // sql이 디비 서버로 전송됩니다. 그리고 그 결과를 반환합니다.
			
			/*
			 * 3. next() 를 호출해서 커서를 이동시킵니다.
			 *    이동시킨 위치에서 읽어올 데이터가 있으면 next()는 true를 반환합니다.
			 */
			if ( rs.next() ) {
				int seq = rs.getInt("seq"); // 2
				String uid = rs.getString("userid");
				String email = rs.getString("email");
				String pw = rs.getString("password");
				
				UserVO user = new UserVO(seq, uid, email, password);
				return user;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, stmt, rs);
		}
		return null;
	}
}
