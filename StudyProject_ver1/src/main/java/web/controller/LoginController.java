package web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import spring.biz.user.service.UserService;
import spring.biz.user.vo.UserVO;

@Controller
public class LoginController {

	@Autowired
	UserService service;
	
	@RequestMapping(value = "/login.do",method = RequestMethod.GET)
	public String login() {
		return "login";
	}
	@RequestMapping(value = "/login.do",method = RequestMethod.POST)
	public String loginProc(UserVO vo,HttpServletRequest request) {
		UserVO user = service.login(vo.getUserid(), vo.getUserpw());
		if(user != null) {
			request.getSession().setAttribute("User", user);
			request.getSession().setAttribute("login", user);
			return "redirect:user/list.do";
		}else {
   			request.setAttribute("msg", "로그인 정보를 다시입력하세요.");
   			return "login";
		}
				
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		request.setAttribute("msg", "로그아웃되었습니다.");
		//return "login";
		return "redirect:index.jsp";
	}
}





