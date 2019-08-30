package javaStream;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * java.util.stream package 안에 우리가 사용할 수 있는 stream 이 존재해요!
 * BaseStream 으로부터 상속받아서 몇몇개의 Stream 이 존재
 * Stream => 해당 Stream안에 ㅐㄱ체 들어가 있는 경우
 * IntStream => 해당 Stream 안에 int 값이 들어가 있는 경우 사용.
 * LongStream
 * DoubleStream 도 존재해요!
 * 
 * 여러가지 형태의 다양한 source에서 Stream 을 얻어낼 수 있어요!
 * 
 */
public class Exam02_StreamSource {
	
	private  static List<String> names=
			Arrays.asList("홍길동", "김길동", "최길동");
	private static int myArr[] = {10,20,30,40,50};
	
	public static void main(String[] args) {
				
		//List로부터 Stream 을 생성할 수 있어요!
		Stream<String> stream1 = names.parallelStream();
		//배열로 부터 Stream을 생성할 수 있어요!
		IntStream stream2 = Arrays.stream(myArr);
		System.out.println(stream2.sum());
		// 정수형 숫자 영역을 이용해서 Stream 을 생성할 수 있어요!
		IntStream stream3 = IntStream.rangeClosed(1, 10); //둘다 Inclusive 1도 포함 10도 포함 (1~10)
		// 파일로 부터 Stream을 생성 할 수 있어요!
		Path path = Paths.get("asset/readme.txt");
		//File객체 ( java.io )와 유사한 java.nio  에 포함된 class
		//Path
		try {
			Stream<String> stream4 = Files.lines(path,Charset.forName("UTF-8"));
			stream4.forEach(t -> System.out.println(t)); //컨슈머 : 인자하나 들어가서 바로 소비하는 코드.
			stream4.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}
