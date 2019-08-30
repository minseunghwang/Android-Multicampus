package JavaNetwork;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Exam01_DateServer {

	// 여기가 서버쪽
	public static void main(String[] args) {
		// 서버쪽 프로그램은 클라이언트의 소켓 접속을 기다려야 해요!
		// ServerSocket class를 이용해서 기능을 구현
		ServerSocket server = null;
		// 클라이언트와 접속된 후 Socket 객체가 있어야지 클라이언트와
		// 데이터 통신이 가능
		Socket socket = null;
		try {
			// port번호를 가지고 ServerSocket 객체를 생성
			// port번호는 0부~65535 사용가능. 0~1023까지는 예약되어 있어요(사용자는 쓰지 말라)
			server = new ServerSocket(5554);
			System.out.println("클라이언트 접속 대기");		// 서버실행하면 여기서 block 잡힌다@@
			socket = server.accept();	// 클라이언트 접속을 기다려요!(block)
			
			// 만약 클라이언트가 접속해 오면 Socket객체를 하나 리턴해요!
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd");
			out.print(format.format(new Date()));		//실제로 이 시점에 데이터가 전달되는것이 아님. buffer가 꽉 차거나 String이 닫히면
			
			// 일반적으로 Reader와 Writer는 내부 buffer를 가지고 있어요.
			out.flush();	// 명시적으로 내부  buffer를 비우고 데이터를 전달 명령
			out.close();
			socket.close();
			server.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
}
