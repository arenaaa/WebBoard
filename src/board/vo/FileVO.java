package board.vo;

/**
 * 
 * @author jihyun
 *
 */
public class FileVO {
	private Integer seq;
	private String originFileName;
	private String generatedFileName;
	private String filePath;
	private long fileLength;
//	private PostingVO posting ; // ?
	/**
	 * INSERT용 생성자(seq가 없습니다.)
	 * @param path
	 * @param originFName
	 * @param generatedFName
	 * @param fileLength
	 */
	public FileVO(String path, String originFName, String generatedFName, long fileLength) {
		this.filePath = path;
		this.originFileName = originFName;
		this.generatedFileName = generatedFName;
		this.fileLength = fileLength;
	}

	public FileVO(Integer seq2, String path, String originFname, String genFname, long flength) {
		this.seq = seq2;
		this.filePath = path;
		this.originFileName = originFname;
		this.generatedFileName = genFname;
		this.fileLength = flength;	
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public void setOriginFileName(String originFileName) {
		this.originFileName = originFileName;
	}

	public String getGeneratedFileName() {
		return generatedFileName;
	}

	public void setGeneratedFileName(String generatedFileName) {
		this.generatedFileName = generatedFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	
	
	
}
