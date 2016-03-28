package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import board.util.DBUtil;
import board.vo.BoardContext;
import board.vo.MsgVO;
import board.vo.PostingVO;
import board.vo.UserVO;

public class MsgDao {

	private BoardContext ctx  ;
	
	public MsgDao ( BoardContext ctx ) {
		this.ctx = ctx;
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
	
	public void sendMessage ( UserVO sender, UserVO receiver, String msg ) {
		String query = "INSERT INTO messages (msg, sender, receiver) values (?, ?, ?)";
		Connection con = null;
		PreparedStatement stmt = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setString(1, msg);
			stmt.setInt(2, sender.getSeq());
			stmt.setInt(3, receiver.getSeq());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(con, stmt, null);
		}
		
	}
	/**
	 * 주어진 사용자가 전송한 쪽지들을 반환합니다.
	 * @param senderSeq
	 * @return
	 */
	public List<MsgVO> findMsgBySender ( Integer senderSeq ) {
		String query ="select seq, sender, receiver, msg, mdate, rdate from messages where sender = ?" ;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, senderSeq);
			
			rs = stmt.executeQuery();
			
			ArrayList<MsgVO> msgs = new ArrayList<>();
			
			UserDao userDao = ctx.getUserDao();
			while ( rs.next() ) {
				Integer seq = rs.getInt("seq");
				String message = rs.getString("msg");
				String time = rs.getString("mdate");
				String rtime = rs.getString("rdate");
				int receiverSeq = rs.getInt("receiver");
				
				UserVO sender = userDao.findBySeq(senderSeq); // TODO 이걸 또 어떻게 구현할 것인지? ????
				UserVO receiver = userDao.findBySeq ( receiverSeq);
				
				MsgVO msg = new MsgVO(seq, message, time, rtime, sender, receiver);
				msgs.add(msg);
			}
			return msgs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
	}

	public List<MsgVO> findMsgByReceiver(Integer receiverSeq) {
		String query ="select seq, sender, receiver, msg, mdate, rdate from messages where receiver = ?" ;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, receiverSeq);
			
			rs = stmt.executeQuery();
			
			ArrayList<MsgVO> msgs = new ArrayList<>();
			
			UserDao userDao = ctx.getUserDao();
			while ( rs.next() ) {
				Integer seq = rs.getInt("seq");
				String message = rs.getString("msg");
				String time = rs.getString("mdate");
				String rtime = rs.getString("rdate");
				int senderSeq = rs.getInt("sender");
				
				UserVO sender = userDao.findBySeq(senderSeq); // TODO 이걸 또 어떻게 구현할 것인지? ????
				UserVO receiver = userDao.findBySeq ( receiverSeq);
				
				MsgVO msg = new MsgVO(seq, message, time, rtime, sender, receiver);
				msgs.add(msg);
			}
			return msgs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
		
	}
	/**
	 * 주어진 글을 반환합니다.
	 * @param msgSeq
	 * @return
	 */
	public MsgVO findMsg(Integer msgSeq ) {
		
		String query ="select seq, sender, receiver, msg, mdate, rdate from messages where seq = ?" ;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, msgSeq);
			
			rs = stmt.executeQuery();
			
			MsgVO msg = null;// new ArrayList<>();
			
			UserDao userDao = ctx.getUserDao();
			if ( rs.next() ) {
				Integer seq = rs.getInt("seq");
				String message = rs.getString("msg");
				String time = rs.getString("mdate");
				String rtime = rs.getString("rdate");
				int senderSeq = rs.getInt("sender");
				
				UserVO sender = userDao.findBySeq(senderSeq); // TODO 이걸 또 어떻게 구현할 것인지? ????
//				UserVO receiver = userDao.findBySeq ( receiverSeq);
				
				msg = new MsgVO(seq, message, time, rtime, sender, null);
			}
			return msg;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
	}

	public void checkAsRead(Integer seq) {
		String query = "update messages set rdate =  now() where seq = ?";
		Connection con = null;
		PreparedStatement stmt = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, seq); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
				
			int nUpdated = stmt.executeUpdate();
			System.out.println("수신확인 : " + nUpdated);
				
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
	
	public int getCountUnread(Integer receiverSeq) {
		int count = 0;
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<String> list = new ArrayList<String>();
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			String query = "select count(seq) from messages where rdate is null and receiver = ?";
			stmt = con.prepareStatement(query);
			stmt.setInt(1, receiverSeq); //서버쪽에서 첫번째 물음표를 이 값으로 바꿔서 해석해라.
			
			rs = stmt.executeQuery();
			rs.next();
			
			return rs.getInt(1);
			
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
}


