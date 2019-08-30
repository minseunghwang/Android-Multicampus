package JavaLambda;
/*
 *  Supplier라고 불리는 함수적 인터페이스 여러개가 우리에게 제공되는데
 *  이 인터페이스의 특징은 매개변수가 없어요. 대신 리턴값이 존재.
 *  getXXX()라는  method가 추상 메소드 형태로 인터페이스 안에 선언되어 있어요!
 *  
 *  친구 목록을 List<String>형태로 만들어요
 *  
 */

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

public class Exam05_LambdaUsingSupplier {
	
	//로또 번호 (1~45)를 자동으로 생성하고 출력하는 간단한 method를 이용
	public static void generateLotto(IntSupplier supplier, Consumer<Integer> consumer) {
		Set<Integer> set = new HashSet<Integer>();
		
		//어떻게 값을 만들거냐
		while(set.size() != 6) {
			set.add(supplier.getAsInt());
		}		
		//어떻게 값을 소모할거냐
		for(Integer i : set ) {
			consumer.accept(i);
		}
	}

	public static void main(String[] args) {
		final List<String> myBuddy = Arrays.asList("홍길동", "김길동", "이순신", "강감찬");

		// Supplier를 이용해서 랜덤으로 1명의 친구를 출력해 보아요!
		// (Math.random() : 0 이상 1 미만의 실수 -> 0 이상 4 미만의 실수
		Supplier<String> supplier = () -> {
			return myBuddy.get((int) (Math.random() * 4));

		};
		// 자기 친구 목록중에 한명 떙긴다
		System.out.println(supplier.get());

		// -------------------------------------------------------------------------

		// IntSupplier : 정수값을 1개 리턴하는 supplier
		// 로또 번호를 자동으로 생성하고 출력하는 간단한 method를 작성
		// (consumer 1개, supplier 1개의 인터페이스를 사용할거에요)

		// generateLotto(서플라이어,컨슈머);
	
		
		generateLotto(()->{
			return (int)(Math.random()*45+1);
		}, t->{System.out.print(t+" "); });

	}
}
