package board.vo;
/**
 * 쪽지를 나타냅니다.
 * @author jihyun
 *
 */
public class MsgVO {
	private Integer seq;
	private String message;
	private String time;
	private String readTime;

	private UserVO sender ;
	private UserVO receiver ;
	
	public MsgVO(Integer seq, String message, String time, String rtime, UserVO sender, UserVO receiver) {
		super();
		this.seq = seq;
		this.message = message;
		this.time = time;
		this.readTime = rtime;
		this.sender = sender;
		this.receiver = receiver;
	}
	
	public Integer getSeq() {
		return seq;
	}
	
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	public String getRtime() {
		return readTime;
	}
	
	public void setRtime(String time) {
		this.readTime = time;
	}
	
	public UserVO getSender() {
		return sender;
	}
	
	public void setSender(UserVO sender) {
		this.sender = sender;
	}
	
	public UserVO getReceiver() {
		return receiver;
	}
	
	public void setReceiver(UserVO receiver) {
		this.receiver = receiver;
	}
	/**
	 * 수신자가 이 쪽지를 읽었는지 나타냅니다.
	 * @return 읽었으면 true, 안읽었으면 false 반환합니다.
	 */
	public boolean isRead() {
		return readTime != null;
	}
}
