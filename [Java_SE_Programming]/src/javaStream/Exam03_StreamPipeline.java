package javaStream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
 * Reduction
- 대량의 데이터를 가공해서 축소하는 개념 
   => sum, average, count, max, min
   
Collection을 사용할 때 Stream을 이용해서 이런 Reduction 작업을 쉽게 할 수 있어요 

만약, Collection 안에 Reduction 하기가 쉽지 않은 형태로 데이터가 들어가 있으면
중간 처리과정을 거쳐서 Reduction 하기 좋은 형태로 변환한다. 

Stream은 Pipline을 지원 (Stream을 연결해서 사용할 수 있어요)

간단한 예제를 통해서 이해해 보아요~
- 직원 객체를 생성해서 ArrayList 안에 여러 명의 직원을 저장.
- 이 직원 중에 IT에 종사하고 남자인 직원을 추려서 해당 직원들의 연봉 평균을 출력
 */

public class Exam03_StreamPipeline {
	private static List<Exam03_Employee> employees =
			Arrays.asList(
					new Exam03_Employee("홍길동",20,"IT","남자",2000),
					new Exam03_Employee("김길동",30,"Sales","여자",3000),
					new Exam03_Employee("최길동",40,"IT","남자",1000),
					new Exam03_Employee("이순신",50,"Sales","남자",3500),
					new Exam03_Employee("유관순",35,"IT","여자",7000),
					new Exam03_Employee("신사임당",60,"IT","여자",4000),
					new Exam03_Employee("강감찬",30,"IT","남자",1000),
					new Exam03_Employee("이황",45,"Sales","남자",5000),
					new Exam03_Employee("홍길동",20,"IT","남자",2000));
	
	public static void main(String[] args) {
//		// 부서가 IT인 사람들 중 남자에 대한 연봉 평균을 구해보세요!
//		Stream<Exam03_Employee> stream = employees.stream();
//		// stream의 중간처리와 최종처리를 이용해서 원하는 작업을 해 보아요!
//		// filter method는 결과값을 가지고 있는 stream을 리턴.
//		double avg = stream.filter(t ->  t.getDept().equals("IT"))		// predicate이니까 저걸 비교해서 true나 false를 리턴. 리턴한 true나 false를 filter에 인자로 줌! // 부서가 IT인거만 빼고 다 버리는 식 
//		.filter(t -> t.getGender().equals("남자"))
//		.mapToInt(t->t.getSalary())							// 스트림안의 객체가 다 정수로 땅땅땅 바뀜		// 여기까지가 중간처리
//		.average().getAsDouble();							// 을 평균내서 double형으로 출력			// average()가 최종처리! 리덕션(?)
//							// 최종처리 (여기선 .average)를 보고, 어? 최종처리하려고 ? 그럼 중간처리(필터 두개) 해줄게! 하고 필터코드 돌리는거
//							// lazy 처리(간단한 데이터면 괜찮지만 무거운데이터일경우 코드상의 데이터낭비가 심하기때문에)
//							
//		System.out.println("IT부서 남자평균 연봉 : "+avg);
//		
//		// 그럼 Stream이 가지고 있는 method는 무엇이 있나요?
//		// 나이거 35살 이상인 직원 중 남자 직원의 이름을 추쳑하세요
//		stream.filter(t -> (t.getAge() >= 35))
//		.filter(t -> t.getGender().equals("남자"))
//		.forEach(t -> System.out.println(t.getName()));
		
//		//중복제거를 위한 중간처리
//		int temp[] = {10,30,20,30,40,50,30,40};
//		IntStream s =Arrays.stream(temp);
//		s.distinct().forEach(t->System.out.println(t));	// distinct 중복처뤼
		
		// 객체에 대한 중복제거를 해 보아요!
		// VO안에서 equals() method를 overriding해서 처리@@@중요@@@
//		employees.stream().distinct().forEach(t->System.out.println(t.getName()));
		
		// mapToInt() => mapXXX()
		
		// 정렬( 부서가 IT인 사람을 연봉순으로 출력 )
//		employees.stream()
//						.filter(t->t.getDept().equals("IT"))
//						.distinct()
//						.sorted( Comparator.reverseOrder() )		// 오름차순 정렬	// 인자를 가지고 있으면 내림차순 정렬
//						.forEach(t->System.out.println(t.getName() + "," + t.getSalary()));
		// 반복
		// forEach()를 이용하면 스트림안의 요소를 반복할 수 있어요!
		// @@forEach()는 최종 처리 함수라서@@ 중간에 사람이름쫙뽑고 아이티사람들만 걸러내고 하고싶은데 고거시 안되는군
		// 중간 처리 함수로 반복처리하는 함수가 하나 더 제공 			@@ peek() @@	최종 처리 함수가 아니라서 마지막으로 오면 안댐
//		employees.stream()
//				.peek(t->System.out.println(t.getName()))		
//				.mapToInt(t -> t.getSalary())
//				.forEach(t ->System.out.println(t));
		
		// 확인용 최종 처리 함수 => predicate를 이용해서 boolean으로 return
		// 50살 이상인 사람만 추출해서 이름 출력
//		boolean result = employees.stream()
//				.filter(t->(t.getAge() >= 50))		// 두명이 남지만
//				.allMatch(t->(t.getAge() > 55));	// 모두 조건을 만족하는지 보는거( true, false 반환) // anyMatch는 하나만 만족해도 true반환
//		System.out.println(result);
		
		// 최종 확인용 함수로 forEach를 많이 사용했는데
		// forEach말고 collect()를 이용해 보아요!
		// 나이가 50살 이상인 사람들의 연봉을 구해서
		// List<Integer>형태의 ArrayList에 저장해 보아요!
		Set<Integer> tmp = employees.stream()
					.filter(t -> (t.getAge() >= 50))
					.map(t -> t.getSalary())
//					.collect(Collectors.toList());		// 리스트 형태로 변환하는거
					.collect(Collectors.toCollection(HashSet :: new));		// set형태로 변환하는거
		System.out.println(tmp);
		// 당연히 Set으로도 저장할 수 있고요... Map으로도 저장할 수 있어요!
	}
}

class Exam03_Employee implements Comparable<Exam03_Employee>{
	private String name;	// 이름
	private int age;		// 나이
	private String dept;	// 부서
	private String gender;	// 성별
	private int salary;		// 연봉
	
	public Exam03_Employee(String name, int age, String dept, String gender, int salary) {
		super();
		this.name = name;
		this.age = age;
		this.dept = dept;
		this.gender = gender;
		this.salary = salary;
	}

	public Exam03_Employee() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getSalary() {
		return salary;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public boolean equals(Object obj) {
		// 만약 overriding을 하지 않으면 주소가지고 비교
		// 내가 원하는 방식으로 overriding을 해서 특정 조건을 만족하면
		// 객체가 같아! 라는 식으로 작성해보자
		boolean result = false;
		Exam03_Employee target = (Exam03_Employee)obj;
		if(this.getName().equals(target.getName())) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((dept == null) ? 0 : dept.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + salary;
		return result;
	}
	
	@Override
	public int compareTo(Exam03_Employee o) {
		// 정수값을 리턴해요
		// 양수가 리턴되면 순서를 바꿔요
		// 0이나 음수가 리턴되면 순서를 바꾸지 않아요
		int result = 0;
		if(this.getSalary() >= o.getSalary()) {
			result = 1;
		} else if(this.getSalary() == o.getSalary()) {
			result = 0;
		} else {
			result = -1;
		}
		return result;
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Exam03_Employee other = (Exam03_Employee) obj;
//		if (age != other.age)
//			return false;
//		if (dept == null) {
//			if (other.dept != null)
//				return false;
//		} else if (!dept.equals(other.dept))
//			return false;
//		if (gender == null) {
//			if (other.gender != null)
//				return false;
//		} else if (!gender.equals(other.gender))
//			return false;
//		if (name == null) {
//			if (other.name != null)
//				return false;
//		} else if (!name.equals(other.name))
//			return false;
//		if (salary != other.salary)
//			return false;
//		return true;
//	}
	
	
	
}