package javaThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam08_ThreadPoolBasic extends Application{
	
	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// startBtn : Thread Pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : Thread Pool을 종료하는 버튼
	
	ExecutorService executorService;
	// Thread Pool : Thread Pool
	
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
		
		initBtn = new Button("Thread Pool 생성");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 떄 호출!	// setOnAction(여기엔 객체가 들어가야되요!?)
			executorService = Executors.newCachedThreadPool();
			int threadNum = ((ThreadPoolExecutor)executorService).getPoolSize();	// Thread Pool안에 몇개의 Thread있는지 찍어볼거에요
			printMsg("현재 Pool안의 Thread갯수 : " + threadNum);
			// 처음에 만들어지는 Thread Pool안에는 Thread가 없어요.
			// 만약 필요하면 내부적으로 Thread를 생성.
			// 만드는 Thread의 수는 제한이 없어요.
			// 60초 동안 Thread가 사용되지 않으면 자동적으로 삭제.
			
//			executorService = Executors.newFixedThreadPool(5);
			// 처음에 만들어지는 Thread Pool안에는 Thread가 없어요.
			// 만약 필요하면 내부적으로 Thread를 생성.
			// 인자로 들어온 int수 만큼의 Thread를 넘지 못해요
			// Thread가 사용되지 않더라도 만들어진 Thread는 계속 유지
		});
		
		stopBtn = new Button("Thread Pool 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			executorService.shutdown();		// 스레드풀만 종료 스레드는 종료안됨(스레드 종료할라면 인터럽트 걸어서 꺼줘야됨)
		});
		
		startBtn = new Button("Thread를 실행!!");	// 버튼클릭!! 이들어가있는 버튼이 생성됨
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			for(int i=0; i<10; i++) {
				final int k = i;
				Runnable runnable =() ->{
					Thread.currentThread().setName("MYThread-" + k);
					String msg = Thread.currentThread().getName() +
							"     Pool의 개수 : " + ((ThreadPoolExecutor)executorService).getPoolSize();
					printMsg(msg);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				};
				executorService.execute(runnable);
			}
		});
		
		FlowPane flowpane = new FlowPane();		// 긴 판자 하나 만든다고 생각하시면 됩니다
		flowpane.setPrefSize(700, 50);
		
		// flowpane에 버튼을 올려요!
		flowpane.getChildren().add(initBtn);
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
