package JavaIO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Exam01_KeyboardInput {
	public static void main(String[] args) {
		
		System.out.println("키보드로 한줄을 입력해요!");
		
		// 표준입력(keyboard)로부터 입력을 받기위해 keyborad와
		// 연결된 stream객체가 필요.
		// Java는 표준입력에 대한 Stream을 기본적으로 제공
		// System.in(InputStream)	// 하면 객체가 딱 튀어나온다 ?
		// Stream은 이렇게 inputstream과 outputstream으로 구분
		// byteStream(기본데티어형)과 reader, writer계열(문자열) stream으로 구분
		InputStreamReader isr = new InputStreamReader(System.in);	// 일반스트림을 reader로 만들어주는거	(일반 스트림 예: System.in)
																	// 버퍼개념이 없어서 불편하고 느리다
		BufferedReader br = new BufferedReader(isr);				// System.in을 가지고 바로 BufferedReader를 못만들기때문에 중간과정을 거친다
		
		try {												// 사용자가 입력한거의 한 라인을 읽어 스트링으로 떨궈주는거 , IO쪽 대부분의 코드는 exception을 발생할 우려가 있따
			String input = br.readLine();		// blocking
			System.out.println("입력받은 데이터  :" + input);
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
}