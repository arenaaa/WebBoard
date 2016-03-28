package board.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import board.util.DBUtil;
import board.vo.FileVO;

/**
 * 첨부파일에 대한 CRUD작업을 관리합니다.( sboarddb.files ) 
 * 
 * @author jihyun
 *
 */
public class FileDao {

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
	 * 주어진 글(post seq)에 첨부된 파일의 FileVO 를 반환합니다.
	 * @param postSeq
	 * @return
	 */
	public FileVO findByPosting ( Integer postSeq) {
		String query = "SELECT seq, origin_fname, gen_fname, path, file_length from files WHERE fkposting = ? ";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, postSeq);
			rs = stmt.executeQuery();
			
			if (rs.next()){
				Integer seq = rs.getInt("seq");
				String path = rs.getString("path");
				String originFname = rs.getString("origin_fname");
				String genFname = rs.getString("gen_fname");
				long flength = rs.getLong("file_length");
				// (String path, String originFName, String generatedFName, long fileLength) 
				FileVO filevo = new FileVO(seq, path, originFname, genFname, flength);
				return filevo ;
			} else {
				return null;				
			}
			
		} catch( SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
		
	}
	public FileVO findBySeq(Integer seq) {
		String query = "SELECT seq, origin_fname, gen_fname, path, file_length from files WHERE seq = ? ";
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		con = getConnection("jdbc:mysql://localhost:3306/sboarddb", "root", "");
		
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, seq);
			rs = stmt.executeQuery();
			
			if (rs.next()){
//				seq = rs.getInt("seq");
				String path = rs.getString("path");
				String originFname = rs.getString("origin_fname");
				String genFname = rs.getString("gen_fname");
				long flength = rs.getLong("file_length");
				// (String path, String originFName, String generatedFName, long fileLength) 
				FileVO filevo = new FileVO(seq, path, originFname, genFname, flength);
				return filevo ;
			} else {
				return null;				
			}
			
		} catch( SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			DBUtil.close(con, stmt, rs);
		}
	}
	
	
}
