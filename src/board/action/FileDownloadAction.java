package board.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.util.Streams;

import board.dao.FileDao;
import board.vo.BoardContext;
import board.vo.FileVO;

public class FileDownloadAction {
	public String process ( BoardContext ctx, HttpServletRequest request, HttpServletResponse response ) throws IOException {
		String seq = request.getParameter("seq");
		/*
		 * 1. 첨부 파일 정보를 읽어와야 합니다. - FileDao 가 필요합니다.
		 */
		FileDao fileDao = ctx.getFileDao();
		FileVO fileInfo = fileDao.findBySeq ( Integer.parseInt(seq) );
		
		/*
		 * 2. 첨부파일에서 디렉토리 경로를 얻어와야 합니다.
		 */
		String dir = fileInfo.getFilePath();
		String fname = fileInfo.getGeneratedFileName();
		File file = new File ( dir, fname );
		
		/*
		 * 3. FileInputStream을 생성합니다.
		 */
		FileInputStream fis = new FileInputStream( file );
		
		/*
		 * 4.http 헤더값을 잘!!! 설정해줘야 합니다.
		 */
		response.setContentLength( (int) fileInfo.getFileLength() );
		
		String encoded = URLEncoder.encode(fileInfo.getOriginFileName(), "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encoded + "\"");
		/*
		 * 지금 내가 보내주는 이 파일은 특정한 타입을 알 수 없다(라고 거짓말을 하는 것)
		 */
		response.setContentType("application/octet-stream");
		
		OutputStream out = response.getOutputStream();
//		out.write('h');
		/*
		 * 5. output stream에 씁니다.
		 */
		Streams.copy(fis, out, false);
		
		return null;
	}
}
