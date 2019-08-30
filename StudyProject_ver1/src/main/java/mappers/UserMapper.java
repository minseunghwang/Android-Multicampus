package mappers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Select;

import spring.biz.user.vo.UserVO;

public interface UserMapper {
		
        @Select("select * from userinfo	"
		          + " where userid=#{userid} and userpwd = #{userpwd}")
		UserVO login(UserVO user);
		
        
		@Select("select * from userinfo")
		List<UserVO> list();
				
	    
	    @Select("select * from userinfo where ${condition} like '%'||#{ keyword  }||'%'")
		List<UserVO> search(HashMap<String , String> map);

}




