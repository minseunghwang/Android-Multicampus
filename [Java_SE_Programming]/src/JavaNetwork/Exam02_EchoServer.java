package JavaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * Echo program을 작성해 보아요!
 * 클라이언트 프로그램으로부터 문자열을 네트워크를 통해 전달받아서
 * 다시 클라이언트에게 전달하는 echo 프로그램.
 * 현재는 단 1번만 echo가 동작하는데 클라이언트가 접속을 종료할때까지
 * echo작업이 지속적으로 동작하도록 프로그램을 수정!
 * 서버는 클라이언트가 종료되면 같이 종료되도록 수정.
 * 
 */

public class Exam02_EchoServer {
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket socket = null;
		
		try {
			server = new ServerSocket(5557);
			System.out.println("서버 프로그램 기동 - 5557");
			socket = server.accept();
			// stream 생성@@@@@
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			PrintWriter out =
					new PrintWriter(socket.getOutputStream());
			
			while(true) {
				// br로부터 데이터를 읽어서 out을 통해 다시 전달
				String msg = br.readLine();
				if(msg.equals("EXIT") ) {
					// 사용된 리소스를 해제
					out.close();
					br.close();
					socket.close();
					server.close();
					System.out.println("서버 프로그램 종료!!");
				}
				out.println(msg);	// 데이터를 보내주기 ?! 여기가 쏘는건가
				out.flush();		// 명시적으로 내부  buffer를 비우고 데이터를 전달 명령
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}