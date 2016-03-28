package board.service;

import java.util.List;

import board.dao.PostingDao;
import board.vo.BoardContext;
import board.vo.FileVO;
import board.vo.PostingVO;
import board.vo.UserVO;

public class PostingService {

	private PostingDao postingDao ;
	
	public PostingService ( PostingDao dao ) {
		postingDao = dao;
	}
	/**
	 * @param offst
	 * @param length
	 * @return
	 */
	public List<PostingVO> findPostings( ) {
		List<PostingVO> p0 = postingDao.findPostings();
		return p0;
	}
	
	public List<PostingVO> findPostings( int offset, int length ) {
		List<PostingVO> p0 = postingDao.findPostings(offset, length);
		return p0;
		
	}
	
	public PostingVO findPostingBySeq( Integer postingSeq ) {
		//조회수증가 기능이 필요합니다.
		postingDao.increaseViewcount ( postingSeq );
		return postingDao.findBySeq ( postingSeq);
	}

	public PostingVO PostingBySeq( Integer postingSeq ) {
		// 추천수 증가
		postingDao.increseRecommendcount( postingSeq );
		return postingDao.findBySeq ( postingSeq);
	}
	
	public PostingVO insertPost(String title, String content, UserVO writer) {
//		PostingVO post = new PostingVO (title, content, writer );
//		postingDao.insertPost(post);
//		return post;
		return insertPost(title, content, writer, null);
	}
	
	public PostingVO insertPost(String title, String content, UserVO writer, FileVO attachedFile) {
		PostingVO post = new PostingVO (title, content, writer, attachedFile);
//		post.setAttachedFile(attachedFile);
		postingDao.insertPost(post);
		return post;
	}

	public void updatePost(PostingVO postingToEdit) {
		postingDao.updatePosting(postingToEdit);
		
		
	}

	public void deletePost(int postSeq) {
		postingDao.deletePosting(postSeq);
		
	}

	public void recommend(UserVO user, PostingVO posting) {
		postingDao.recommend(user, posting);
	}

	public boolean existRcmd(UserVO user, PostingVO posting) {
		
		return postingDao.isRecommended(user, posting);
	}
	/**
	 * 게시판 글의 갯수를 반환합니다.
	 * @return
	 */
	public int countPostings() {
		return postingDao.countPostings();
	}
}
