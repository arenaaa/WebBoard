package board.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.util.Streams;

import board.service.PostingService;
import board.vo.BoardContext;
import board.vo.FileVO;
import board.vo.PostingVO;
import board.vo.UserVO;
/**
 * 글쓰기(첨부한 파일 처리) 로직 구현
 * 

------WebKitFormBoundaryx0eepagMBpYlEjhh
Content-Disposition: form-data; name="writer"

2
------WebKitFormBoundaryx0eepagMBpYlEjhh
Content-Disposition: form-data; name="title"

ssss
------WebKitFormBoundaryx0eepagMBpYlEjhh
Content-Disposition: form-data; name="contents"

xxxxxx
------WebKitFormBoundaryx0eepagMBpYlEjhh--


 * @author jihyun
 *
 */
public class PostWritingAction {

	public String process ( BoardContext ctx, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		
		String rootDir = ctx.getFileRoot(); // /Users/jihyun
		HttpSession session = request.getSession();
		UserVO loginUser = (UserVO) session.getAttribute(BoardContext.ATT_LOGIN_USER);
		/*
		 * 1. 현재 요청이 "multipart/form-data" 인지 확인해야 합니다.
		 *    ServletFileUpload.isMultipartContent 
		 */
		if ( ! ServletFileUpload.isMultipartContent(request) ) {
			System.out.println("파일 업로드 요청이 아닙니다.");
			return request.getServletContext().getContextPath()+"/postings";
		}
		
		
		String paramWriter = request.getParameter("writer");
		String paramTitle = request.getParameter("title");
		String paramContent = request.getParameter("contents");
		
		FileVO attachedFile = null;
		
		ServletFileUpload upload = new ServletFileUpload();
		
		try {
			FileItemIterator itr = upload.getItemIterator(request);
			while ( itr.hasNext() ) {
				FileItemStream stream = itr.next();
				/*
				 * 2. isFormField - true를 반환하면 이건 파일업로드 파트가 아니라 일반 폼필드
				 */
				if ( stream.isFormField() ) {
					InputStream in = stream.openStream();
					String fielddName = stream.getFieldName();
					String value = Streams.asString( in );
					System.out.println("filedName : " + fielddName + " and value: " + value);
					if ( "writer".equals(fielddName)) {
						paramWriter = value;
					} else if ( "title".equals(fielddName)) {
						paramTitle = value;
					} else if ( "contents".equals(fielddName)) {
						paramContent = value;
					}
					
				} else {
					// 이 파트는 파일업로드 관련 파트입니다.
					/*
					 * ontent-Disposition: form-data; name="attached_file"; filename="board.war"
					 * Content-Type: application/octet-stream
					 * 
					 * dasfdasfasdfd
					 * ------WebKitFormBoundaryl4z744lLAB0x9mLp--
					 */
					String fieldName = stream.getFieldName();
					String originFName = stream.getName(); // 파일 이름 가져올때!
					String generatedFName = getGeneratedName ( loginUser,  originFName );
					System.out.println("file input : " + fieldName + " and filename: " + originFName);
					
					/*
					 * 3. 파일을 디스크에 저장합니다.
					 */
					InputStream in = stream.openStream();
					FileOutputStream fos = new FileOutputStream(new File(rootDir, generatedFName));
					long fileLenth = Streams.copy(in, fos, true);
					
					attachedFile = new FileVO( rootDir, originFName, generatedFName, fileLenth  );
				}
				
			}
			
			
			System.out.println("writer : " + paramWriter);
			System.out.println("title  : " + paramTitle);
			System.out.println("content: " + paramContent);
			
			PostingService postingService = ctx.getPostingService();
			PostingVO posting = postingService.insertPost ( paramTitle, paramContent, loginUser, attachedFile );				
//			posting.setAttachedFile ( attachedFile );
//			if ( attachedFile != null) {
//				PostingVO posting = postingService.insertPost ( paramTitle, paramContent, loginUser, attachedFile );				
//			} else {
//				PostingVO posting = postingService.insertPost ( paramTitle, paramContent, loginUser );				
//				
//			}
			
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 다음에 이동할 페이지 
		return request.getServletContext().getContextPath()+"/postings";
		
	}

	private String getGeneratedName(UserVO loginUser, String originFName) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		String newFName = loginUser.getUserId() + "-" + df.format(new Date()) + "-" + originFName;
		return newFName;
	}
}
