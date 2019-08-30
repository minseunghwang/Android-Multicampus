package JavaLambda;
/*
 * 함수적 프로그래밍 패턴을 위해 Java는 8 버전부터 Lambda를 지원해요
 * 
 * 람다는 익명함수를 만들기 위한 expression(식)
 * -> 객체지향언어보다는 함수지향적 언어에 잘 사용되요!
 * 
 * 기존 자바 개발자들은 이 Lambda 라는 개념에 익숙해지기가 쉽지 않아요!
 * 그럼에도 불구하고 자바가 Lambda를 도입한 이유는 크게 2가지 정도로 생각할 수 있어요!!
 * 
 * 1. 코드가 간결해져요
 * 2. Java Stream을 이용하기 위해서
 *    Java Stream은 collection(List, Map, Set, Array...)의 처리를 굉장히 효율적으로 할 수  있어요!
 *    (병렬 처리가 가능)
 *    
 *    
 *    
 * 람다식의 기본 형태
 * 
 * (매개변수)   ->   {실행코드}
 * 여기있는 변수 가져다가 이거 실행해~
 * 익명함수를 정의하는 형태로 되어 있지만 실제로는 익명클래스의 인스턴스를 생성
 *  
 * 람다식이 어떤 객체를 생성하느냐는 람다식이 대입되는 interface 변수가
 * 어떤 interface인가에 달려있어요!
 * 
 *  일반적인 interface를 정의해서 람다식으로 표현해 보아요!
 */

interface Exam01_LambdaIF {
	// 추상 method만 올 수 있어요!
	// 추상 method 란 -> 메서드의 정의가 없고 선언만 있는게 추상(abstract method) method

	void myFunc(int k); // 인터페이스기 때문에 객체 못만들어 -> 사용하려면 new한 다음에 객체를 만들어줘야해.
	//void test1(int k);  // 이거 하나 더 있으면? -> 이 인터페이스를 사용하려면 오버라이딩 2번해야해
						//람다식은 1개만 가능-> 그럼 컴파일러가 객체 멀 오버라이딩한거야? 객체 왜 하나ㅏ밖에 오버라이딩 안해썽? 하고 뭐가 먼지 모르게댐
						//결론 : 안에 추성메서드가 여러개 있으면 그 인터페이스는 람다식으로 오버라이딩 할 수 없어요.

}

public class Exam01_LambdaBasic {

	public static void main(String[] args) {
		
		Exam01_LambdaIF sample= 
				(int k) -> {System.out.println("출력되요!!"); }; //얘를 실제로 호출하려면
				sample.myFunc(100);
		}

	
	
}
