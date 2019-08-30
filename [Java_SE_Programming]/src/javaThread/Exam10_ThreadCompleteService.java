package javaThread;
/*
 * 1부터 100까지 숫자의 합을 구하려고 해요
 * 1~10까지 1개의 Thread가 합을 계산해서 결과를 return
 * 11~20까지 1개의 Thread가 합을 계산해서 결과를 return
 * ...
 * 91~100까지 1개의 Thread가 합을 계산해서 결과를 return
 * ==> Thread Pool을 이용해야 하고 Callable을 이용해서 리턴값을 받아야 해요
 * ==> 10개의 Thread로부터 각각 데이터를 받아들이는 Thread를 하나 만들어야 해요
 * 
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam10_ThreadCompleteService extends Application {

	TextArea textarea;
	Button initBtn, startBtn, stopBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// startBtn : Thread Pool을 이용해서 Thread를 실행시키는 버튼
	// stopBtn : Thread Pool을 종료하는 버튼

	ExecutorService executorService;
	// Thread Pool : Thread Pool
	ExecutorCompletionService<Integer> executorCompletionService;
	private int total = 0;

	private void printMsg(String msg) {
		Platform.runLater(() -> {
			textarea.appendText(msg + "\n");
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

		initBtn = new Button("Thread Pool 생성");
		initBtn.setPrefSize(250, 50);
		initBtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 떄 호출!
			executorService = Executors.newCachedThreadPool(); // 이거보다(이걸가지고)
			executorCompletionService = new ExecutorCompletionService<Integer>(executorService); // 좀더 성능이 향상된 쓰레드 풀 만들기
		});

		stopBtn = new Button("Thread Pool 종료");
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			executorService.shutdown(); // 스레드풀만 종료. 스레드는 종료안됨(스레드 종료할라면 인터럽트 걸어서 꺼줘야됨)
		});

		startBtn = new Button("Thread를 실행!!"); // 버튼클릭!! 이들어가있는 버튼이 생성됨
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			for (int i = 1; i < 101; i = i + 10) {
				final int k = i;

				// runnable말고 다른 thread객체 callable
				// 각영역의 합을 구하는 thread 만들어서
				Callable<Integer> callable = new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						IntStream intStream = IntStream.rangeClosed(k, k + 9);
						int sum = intStream.sum();
						return sum;
					}
				};
				executorCompletionService.submit(callable);
			}
			// 결과 추합하는 Thread (값 10개 나온거 다 더하기)
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < 10; i++) {
						try {
							Future<Integer> future = executorCompletionService.take();
							total += future.get();
						} catch (InterruptedException | ExecutionException e) {
							e.printStackTrace();
						}
					}
					printMsg("최종 결과 값은 : " + total);
				}
			};
			executorService.execute(runnable);
		});

		FlowPane flowpane = new FlowPane(); // 긴 판자 하나 만든다고 생각하시면 됩니다
		flowpane.setPrefSize(700, 50);

		// flowpane에 버튼을 올려요!
		flowpane.getChildren().add(initBtn);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);

		root.setBottom(flowpane); // 바탐쪽에 flowpane 붙이는 작업
		// Scene객체가 필요해요.
		Scene scene = new Scene(root);
		primaryStage.setScene(scene); // 윈도우(primaryStage)에 화면을 설정해 주는 코드
		primaryStage.setTitle("Thread 예제입니다.!!");
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(); // 내부적으로 바로위에 start method가 실행됩니다.
	}
}
