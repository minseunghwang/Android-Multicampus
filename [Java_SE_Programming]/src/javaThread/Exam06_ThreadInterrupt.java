package javaThread;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam06_ThreadInterrupt extends Application{
	
	TextArea textarea;
	Button startBtn, stopBtn;
	Thread counterThread;
	
	private void printMsg(String msg) {
		// textarea에 문자열 출력하는 method
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
		
		startBtn = new Button("Thread 시작");	// 버튼클릭!! 이들어가있는 버튼이 생성됨
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 떄 호출!	// setOnAction(여기엔 객체가 들어가야되요!?)
			counterThread = new Thread(()->{
				try {
					for(int i=0; i<10; i++) {
						Thread.sleep(1000);			// 요기에서 블락이 딱 걸려요
						printMsg(i + "-" + Thread.currentThread().getName());
					}
				} catch (Exception e) {
					// 만약 interrupt()가 걸려있는 상태에서 block상태로 진입하면
					// Exception을 내면서 catch문으로 이동.
				
					printMsg("Thread가 종료되었어요!");
				}
			});
			
			counterThread.start();
		});
		stopBtn = new Button("Thread 중지");	// 버튼클릭!! 이들어가있는 버튼이 생성됨
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 떄 호출!	// setOnAction(여기엔 객체가 들어가야되요!?)
			counterThread.interrupt();		// method가 실행된다고 바로
											// Thread가 종료되지 않아요!
			// interrupt() method가 호출된 Thread는 sleep과 같이 @@@@@@
			// block상태에 들어가야지 interrupt를 시킬 수 있어요!@@@@@@
		});
		FlowPane flowpane = new FlowPane();		// 긴 판자 하나 만든다고 생각하시면 됩니다
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올려요!
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
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
