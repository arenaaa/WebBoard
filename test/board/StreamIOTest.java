package board;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.fileupload.util.Streams;
// java StreamIOTest 
public class StreamIOTest {
	public static void main(String[] args) throws IOException {
		// Users/jihyun/Documents/workspace/board
		File inputfilePath = new File( "todo.txt");
		FileInputStream in0 = new FileInputStream(inputfilePath); // file path
		String data = "이 내용읖 파일로 써보겠습니다.";
		
		File inputfilePath2 = new File( "todo2.txt");
		FileInputStream in1 = new FileInputStream(inputfilePath2); // file path
		String data1 = "내용쓰";
		
		FileOutputStream out = new FileOutputStream(new File("todo2.txt"), true);
		ByteArrayInputStream in2 = new ByteArrayInputStream(data.getBytes());
		
		Streams.copy(in0, out, false);// 얘를 즐겨쓰게 됩니다.
		Streams.copy(in2, out, true);// 얘를 즐겨쓰게 됩니다.
		
		iterator();
		
		byte [] b = data.getBytes();
		String s2 =  Streams.asString(new ByteArrayInputStream(b));
		
		InputStream iii = new ByteArrayInputStream(b);
//		while ( true ) {
//			int d = iii.read();
//		}
		System.out.println("s2 : " + s2);
		
	}

	private static void iterator() {
		ArrayList<String> names = new ArrayList<>();
		names.add("dkdkd");
		names.add("glekskdfd");
		names.add("glskdkxx");
		
		/*
		 * 1. 중세시대 방식
		 */
		for ( int i = 0 ; i < names.size(); i ++) {
			System.out.println(names.get(i).length());
		}
		
		/*
		 * 2. 근대방식
		 * 
		 * "ddd"
		 * ""xxx" 
		 * "gekdk" 
		 *      <- c
		 * 멀티 스레드 환경에서 이터레이터를 씁니다.
		 * 
		 */
		Iterator<String> itr = names.iterator();
		while ( itr.hasNext() ) {
			String s = itr.next();
			System.out.println(s.length());
		}
		
		
		
	}
}
