package web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import spring.biz.user.vo.UserVO;

public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserVO vo = (UserVO) target;
		if(vo.getUserid() == null || vo.getUserid().trim().isEmpty()) {
			errors.rejectValue("userid", "required");
		}
		if(vo.getUsername() == null || vo.getUsername().trim().isEmpty()) {
			errors.rejectValue("username", "required",new Object[] {"username"},"input phone");
		}
		if(vo.getUserpw() == null || vo.getUserpw().trim().isEmpty()) {
			errors.rejectValue("userpw", "required");
		}
		if(vo.getEmail() == null || vo.getEmail().trim().isEmpty()) {
			errors.rejectValue("email", "required",
					new Object[] {"email"},"input email");
		}
		if(vo.getPhone() == null || vo.getPhone().trim().isEmpty()) {
			errors.rejectValue("phone", "required",
					new Object[] {"phone"},"input phone");
		}
		if(vo.getAddress() == null || vo.getAddress().trim().isEmpty()) {
			errors.rejectValue("address", "required",
					new Object[] {"address"},"input address");
		}
	}
}
