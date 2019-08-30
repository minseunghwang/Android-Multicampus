package JavaLambda;

/*
 * 람다식을 정의해서 사용할 때 주의해야 할 점이 몇가지 있어요!
 * 클래스의 멤버(필드+메소드)와 로컬변수(지역변수)의 사용에 약간의 제약이  있어요
 * 
 * 특히 this keyword를 사용할 때 주의해야 해요!
 * this : 현재 사용되는 객체의 reference 
 * 람다식은 익명 객체를 만들어내는 코드에요. 람다식의 실행코드 내에서
 * this keyword를 쓰면 익명객체를 지칭하지 않아요!
 * 
 * 
 */
@FunctionalInterface
interface Exam03_LambdaIF {
	public void myFunc();
}

class OuterClass {
//Field (기본적으로 class의 field는 private)
	public int outerField = 100;

	public OuterClass() {
		// default 생성자
		System.out.println(this.getClass().getName()); // 현재 이 시점의 this는 어떤 this냐
	}

//class안에 다른 class 를 정의할거에요( inner class ) 
	class InnerClass {
		int innerField = 200; // Field
		Exam03_LambdaIF fieldLambda = () -> {
			System.out.println("outerField: " + outerField);
			System.out.println("OuterClass의 객체를 찾아요:" + OuterClass.this.outerField);
			System.out.println("innerField: " + innerField);
			System.out.println("this.innerField: " + this.innerField);
		}; // Field
//		Exam03_LamdaIF fieldLambda = new Exam03_LambdaIF() {
//			public void myFunc() {
//			System.out.println(this.getClass().getName()); //현재 이 시점의 this는 어떤 this냐
//			}
//		};

		public InnerClass() {
			System.out.println(this.getClass().getName()); // 현재 이 시점의 this는 어떤 this냐
		} // 생성자

		public void innerMethod() {   //method
			int localVal = 100;       //지역변수(local variable)
									  //지역변수는 stack 영역에 저장이 되고
									  //method가 호출되면 생기고
									  //method가 끝나면 없어져요!
			Exam03_LambdaIF LocalLambda = () -> {
				System.out.println(localVal);
//				localVal=50;              //final로 설정하면 값을 못바꿔요!
										  //값을 바꿀수가 없어요!(readonly)
										  //람다 내 지역변수는 read only에요. 값을 못바꿔요
			};
			fieldLambda.myFunc();
			
		
		};
	}
}

//프로그램 시작을 위한 dummy class로 사용
public class Exam03_LambdaUsingVariable {

	public static void main(String[] args) {
		// 람다식을 사용하려면 InnerClass에 instance가 있어야 해요!
		// 그런데 하필이면 InnerClass가 inner class네???
		// inner class의 instance 를 생성하려면
		// 일단 먼저 OuterClass의 instance부터 생성해 줘야해요.

		OuterClass outer = new OuterClass(); // 외부 클래스의 객체 생성
		OuterClass.InnerClass inner = outer.new InnerClass(); // 내부 클래스의 객체 생성

		inner.fieldLambda.myFunc();
		// 람다식 사용하려면 위쪽의 람다객체가 갖고 있는 추상메서드 호출해야해
	}

}

