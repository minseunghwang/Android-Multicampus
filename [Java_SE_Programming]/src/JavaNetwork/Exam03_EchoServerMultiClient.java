package JavaNetwork;
/*
 * Echo program을 작성해 보아요!
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

class Share{

	List<EchoRunnable> list;
	String shareString ="";
	public Share() {
	}
	
	public void setList(List<EchoRunnable> list) {
		this.list = list;
	}
	public void setShareString(String line) {
		this.shareString = line;
	}
	
	public void sendMessage(String id) {
		list.forEach(t->{
			t.out.println(id + " : " + shareString);
			t.out.flush();
		});
	}
}

class EchoRunnable implements Runnable{
	// 가지고 있어야 하는 field
	Socket socket;	// 클라이언트와 연결된 소켓
	BufferedReader br; 	// 입력을 위한 스트림(데이터 수신을 위한)
	PrintWriter out;	// 출력을 위한 스트림(데이터 송신을 위한)
	Share share;
	String id;
	
	public EchoRunnable(Socket socket, Share share, String id) {
		super();
		this.socket = socket;
		this.share = share;
		this.id = id;
		try {
			this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.out = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {		// 쓰레드 동작하는 메소드
		// 클라이언트와 echo처리 구현
		// 클라이언트가 문자열을 보내면 해당 문자열을 받아서 다시 클라이언트에게 전달.
		// 한번하고 종료하는게 아니라 클라이언트가 "/EXIT"라는 문자열을 보낼때까지 지속.
		String line ="";
		try {
			while((line = br.readLine()) != null) {		// 클라이언트가 보내준 데이터를 받아서 null이 아닐때까지 받아
														// 클라이언트가 접속을 끊었을때가 null 이 온다리
				share.setShareString(line);
				if(line.equals("/EXIT/")) {				// 문자열비교는 line =="/EXIT" 이렇게 하는거아니야@@@@ 될수도있고 안될수도 있어
					break;		// 가장 근접한 loop를 탈출!
				} else {
					share.sendMessage(id);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
public class Exam03_EchoServerMultiClient extends Application {

	String id;
	TextArea textarea; // 메세지창 용도로 사용
	Button startbtn, stopbtn; // 서버시작, 서버중지 버튼
	// ThreadPool을 생성.
	ExecutorService executorservice = Executors.newCachedThreadPool(); // unfixed한 형태
	// 클라이언트의 접속을 받아들이는 서버소켓.
	ServerSocket server; // 서버소켓 포터넘버가 이미 존재하는 번호일 경우를 배제하기위해 try-catch문을 작성해야함. 전역에선 (try-catch문이)불가하니
							// 여기에선 일단 선언만
	List<EchoRunnable> list = new ArrayList<EchoRunnable>();
	BufferedReader br;


	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText("서버  : " + msg + "\n");
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception { // primaryStage 요게 실제 윈도우를 지칭하는 reference
		// 화면 구성해서 window 띄우는 코드
		// 화면 기본 layout을 설정 => 화면을 동서남북 중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		// Borderpane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);

		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		Share share = new Share();
		
		startbtn = new Button("Echo 서버 시작"); // 버튼클릭!! 이들어가있는 버튼이 생성됨
		startbtn.setPrefSize(250, 50);
		startbtn.setOnAction(t -> {
			// 서버 프로그램을 시작
			// 클라이언트의 접속을 기다려요! -> 접속이 되면 Thread를 하나 생성
			// -> Thread를 시작해서 클라이언트와 Thread가 통신하도록 만들어요
			// 서버는 다시 다른 클라이언트의 접속을 기다려요
			Runnable runnable = () -> {
				try {
					server = new ServerSocket(7777);		// 서버소켓만들고
					printMsg("Echo 서버 기동!!");
					while (true) {
						printMsg("클라이언트 접속 대기");
						Socket s = server.accept();		// blocking 클라이언트가 들어올때까지 서버+윈도우가 멈춰@ // 클라이언트 접속!!(할때까지 대기)
														// JavaFX와 accept(클라이언트 대기)를 분리해줘야된다@ (윈도우까지 멈춰버리니까)
						br = new BufferedReader(new InputStreamReader(s.getInputStream()));
						String name;
						if(br.readLine() != null) {
							 name = br.readLine();
						} else {
							name = "(익명)";
						}
						id = name;
						printMsg(id + "님이 대화방에 참여하였습니다");
						printMsg("클라이언트 접속 썽공");
						// 클라이언트가 접속했으니 Thread를 만들고 시작해야 해요!
						EchoRunnable r = new EchoRunnable(s,share,id);
						list.add(r);
						printMsg(list.size()+"명있따.");
						share.setList(list);
						executorservice.execute(r);		// Thread 실행
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			};
			executorservice.execute(runnable);
		});

		stopbtn = new Button("Echo 연결 해제"); // 버튼클릭!! 이들어가있는 버튼이 생성됨
		stopbtn.setPrefSize(250, 50);
		stopbtn.setOnAction(t -> {

		});

		FlowPane flowpane = new FlowPane(); // 긴 판자 하나 만든다고 생각하시면 됩니다
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올려요!
		flowpane.getChildren().add(startbtn);
		flowpane.getChildren().add(stopbtn);
		root.setBottom(flowpane); // 바탐쪽에 flowpane 붙이는 작업

		// Scene객체가 필요해요.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene); // 윈도우(primaryStage)에 화면을 설정해 주는 코드
		primaryStage.setTitle("다중 클라이언트 Echo Server");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(); // 내부적으로 바로위에 start method가 실행됩니다.
	}
}