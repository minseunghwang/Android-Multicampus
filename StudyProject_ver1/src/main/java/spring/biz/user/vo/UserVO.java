package spring.biz.user.vo;

public class UserVO {
	private String userid;
	private String username;
	private String userpw;
	private String email;
	private String address;
	private String phone;
	
	public UserVO() {
		super();
	}
	public UserVO(String userid, String username, String userpw, String email, String address, String phone) {
		super();
		this.userid = userid;
		this.username = username;
		this.userpw = userpw;
		this.email = email;
		this.address = address;
		this.phone = phone;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpw() {
		return userpw;
	}
	public void setUserpw(String userpw) {
		this.userpw = userpw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "UserVO [userid=" + userid + ", username=" + username + ", userpw=" + userpw + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + "]";
	}
}
