package board.service;

import java.util.ArrayList;
import java.util.List;

import board.dao.UserDao;
import board.vo.UserVO;

public class UserService {
	private List<UserVO> users = new ArrayList<>();
	{
		users.add(new UserVO(1000, "mama", "mama@naver.com", "1111"));
		users.add(new UserVO(1001, "tom", "tom@naver.com", "2222"));
		
	}
	private UserDao userDao = new UserDao();
	/**
	 * 누가 얘를 호출해줘야 user service 가 user dao를 사용할 수 있게 됩니다.
	 * 
	 * @param dao
	 */
	public void setUserDao ( UserDao dao) {
		this.userDao = dao;
	}
	
	// UserVO <===> UserService
	public UserVO login ( String userId, String password) {
		/*
		 * userDao.login(userId, password );
		 * userID와 password가 Arraylist인 users에 존재하면 user을 반환한다. (반환타입 또한 userVO)
		 * users에 존재하지 않으면 null을 반환한다.
		 */
//		int [] arr = null;
//		arr.length;
//		for ( UserVO user : users ) {
//			if ( user.getUserId().equals(userId) && user.getPassword().equals(password)){
//				return user;
//			}
//		}
		UserVO loginUser = userDao.login(userId, password);
		
		if ( loginUser != null ) {
			sendMail( loginUser);
		}
		return loginUser;
	}

	/**
	 * 로그인한 사용자에게 확인 메일을 전송합니다.
	 * @param loginUser
	 */
	private void sendMail(UserVO loginUser) {
		
	}
}