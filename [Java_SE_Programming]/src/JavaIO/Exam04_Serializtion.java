package JavaIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

class MyClass implements Serializable{
	// 직렬화와 역 직렬화를 할 떄 같은 타입인지를 비교하기 위해서 내부적으로 사용
	private static final long serialVersionUID = 1L;	
	
	String name;	// 직렬화가 가능한 형태이어야 해요!
	int kor;		// 직렬화가 가능해요!
	// transient는 직렬화 대상에서 제외시키는 키워드(나머지 데이터만 스트림을 통해서 이동)
	transient Socket socket;	// 네트워크 접속을 위해서 필요한녀석인데, 직렬화가 안되는 놈이에요!
	
	public MyClass(String name, int kor) {
		super();
		this.name = name;
		this.kor = kor;
	}
}

public class Exam04_Serializtion{

	public static void main(String[] args) {
		
		// ObjectOutStream을 이용해서 File에 내가 만든 class의
		// instance를 저장.
		
		// 1. 저장할 객체를 생성
		MyClass obj = new MyClass("홍길동",70);
		// 2. 객체를 저장할 파일 객체를 생성
		File file = new File("asset/student.txt");

		try {
			// 3. 파일 객체를 이용해서 ObjectOutputStream을 생성
			FileOutputStream fos = new FileOutputStream(file);		// 파일 열 FileOutputStream 만들기
			ObjectOutputStream oos = new ObjectOutputStream(fos);	// 위에꺼 가지고 ObjectOutputStream 만들기
			// 4. ObjectOutputStream을 이용해서 객체를 파일에 저장
			//		저장될 객체는 반드시 직렬화가 가능한 객체이어야 해요!
			//	 	우리가 생성한 객체는 직렬화가 가능한 객체가 아니에요!!
			//		객체 직렬화가 가능하려면 어떻게 해야하나요??
			// 		Serializable을 구현하면 돼요!
			//		클래스의 필드가 모두 직렬화가 가능한 형태여야 함
			oos.writeObject(obj);
			// 5. 저장이 끝나면 Stream을 close해 줘야 해요!
			oos.close();
			fos.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
