package spring.biz.user.dao;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mappers.UserMapper;
import spring.biz.user.vo.UserVO;

@Repository("mybatis")
public class UserDAO_MyBatis implements UserDAO{

	//@Autowired
	//@Inject
	SqlSession sqlSession=null;
	
	UserMapper ui = null;
	
	@Autowired
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		ui = sqlSession.getMapper(UserMapper.class);
	}
	
	public SqlSession getSqlSession() {
		return sqlSession;
	}
	public UserVO login(String id, String pw) {
        System.out.println("UserDAO_MyBatis 연동");
		UserVO vo = new UserVO();
		vo.setUserid(id);
		vo.setUserpw(pw);
		
		return sqlSession.selectOne("user.login",vo);
	}

	public int addUser(UserVO user) {
		return sqlSession.insert("user.add",user);
	}

	public UserVO getUser(String userid) {
		return sqlSession.selectOne("user.getuser",userid);
	}

	public List<UserVO> getUserList() {
        System.out.println("UserDAO_MyBatis 연동");
        return sqlSession.selectList("user.list");
	}

	public int updateUser(UserVO user) {
		
		return sqlSession.update("user.update",user);
	}

	public int removeUser(String userid) {
		return sqlSession.delete("user.delete",userid);
	}

	public List<UserVO> searchUser(String condition, String keyword) {
	    HashMap<String , String> map = new HashMap<String, String>();
	    map.put(condition,keyword);
		return sqlSession.selectList("user.search",map);
	}
}
