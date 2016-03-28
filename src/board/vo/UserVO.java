package board.vo;

public class UserVO {
	private Integer seq;
	private String userId;
	private String email;
	private String password;
	/*
	 * select seq, userid, email, password from users where seq = ? 
	 * 
	 * 	 * ,	 * */
	public UserVO(Integer seq, String userId, String email, String password) {
//		super();
		this.seq = seq;
		this.userId = userId;
		this.email = email;
		this.password = password;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "UserVO [seq=" + seq + ", userId=" + userId + ", email=" + email + ", password=" + password + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((seq == null) ? 0 : seq.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserVO other = (UserVO) obj;
		if (seq == null) {
			if (other.seq != null)
				return false;
		} else if (!seq.equals(other.seq))
			return false;
		return true;
	}
	
//	@Override
//	public String toString() {
//		/*
//		 * 개발자가 메모리 상태로 있는 인스턴스를 눈으로 알아볼 수 잇게 잘 만들어야 합니다.
//		 * 이것은 디버깅용입니다!!!
//		 */
//		return this.seq + ", " + this.userId + ", " + this.password ;
//	}
	
	
	
	
}
