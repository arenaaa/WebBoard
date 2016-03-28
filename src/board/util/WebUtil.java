package board.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {

	public static int paramInt ( HttpServletRequest req, String paramName, int defaultValue) {
		try {
			return Integer.parseInt(req.getParameter(paramName));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	/**
	 * context path를 벗겨낸 요청 uri만 반환합니다.
	 * @param req
	 * @return
	 */
	public static String stripURI(HttpServletRequest req) {
		String ctxpath = req.getContextPath();
		return req.getRequestURI().substring(ctxpath.length());
		
	}
}
