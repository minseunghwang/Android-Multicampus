package JavaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam01_DataClient extends Application{
	
	TextArea textarea;
	Button btn;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	@Override
	public void start(Stage primaryStage) throws Exception {	// primaryStage 요게 실제 윈도우를 지칭하는 reference
		// 화면 구성해서 window 띄우는 코드
		// 화면 기본 layout을 설정 => 화면을 동서남북 중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		// Borderpane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);		
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("Date 서버 접속");	// 버튼클릭!! 이들어가있는 버튼이 생성됨
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			try {
				Socket socket = new Socket("127.0.0.1",5554);	// url과 포트번호(접속연결의 조건 2가지)
															// 127.0.0.1 : 같은컴의미
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(isr);
				String msg = br.readLine();
				printMsg(msg);
				
				br.close();				// 네트워크연결된 소켓들은 항상 잘 다들 닫아줘야 한다
				isr.close();
				socket.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		});
		FlowPane flowpane = new FlowPane();		// 긴 판자 하나 만든다고 생각하시면 됩니다
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올려요!
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);				// 바탐쪽에 flowpane 붙이는 작업
		
		// Scene객체가 필요해요.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);			// 윈도우(primaryStage)에 화면을 설정해 주는 코드
		primaryStage.setTitle("Thread 예제입니다.!!");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch();		// 내부적으로 바로위에 start method가 실행됩니다.
	}
}
