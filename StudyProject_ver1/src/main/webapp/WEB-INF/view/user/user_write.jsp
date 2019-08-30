<%@page contentType="text/html; charset=utf-8" isELIgnored="false"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib  prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib  prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<title>사용자 관리</title>
<meta charset="utf-8">
<script language="JavaScript">
function userCreate() {	
	f.action="./add.do";
	f.method="post";
	f.submit();
}

function userList() {
	f.action = "list.do";
	f.submit();
}

</script>
</head>
<body bgcolor=#FFFFFF text=#000000 leftmargin=0 topmargin=0 marginwidth=0 marginheight=0>
<br>
<table width=780 border=0 cellpadding=0 cellspacing=0>
	<tr>
	  <td width="20"></td>
	  <td>
  <!--contents-->
	  <table width=590 border=0 cellpadding=0 cellspacing=0>
		  <tr>
			<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;<b>사용자 관리 - 회원 가입</b></td>
		  </tr>
	  </table>  
	  <br>
	  <!-- view Form  -->
	  <form name="f" method="post" action="">
	  
	  <table border="0" cellpadding="0" cellspacing="1" width="590" bgcolor="BBBBBB">
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">사용자 아이디</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:150" name="userid" value="${user.userid}">
				<font color="red"><form:errors path="user.userid" /></font>
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">비밀번호</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="password" style="width:150" name="userpw" value="${user.userpw}">
				<font color="red"><form:errors path="user.userpw" /></font>
			
			</td>
		  </tr>
		  
			<td id="passwordCheckText" width="100"></td>
		  </tr>
		  
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">이름</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:240" name="username" value="${user.username}">
				<font color="red"><form:errors path="user.username" /></font>
				
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">이메일</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:240" name="email" value="${user.email}">
				<font color="red"><form:errors path="user.email" /></font>
			</td>
		  </tr>		
		  
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">전화번호</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:240" name="phone" value="${user.phone}">
				<font color="red"><form:errors path="user.phone" /></font>
			</td>
		  </tr>	
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">주소</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:240" name="address" value="${user.address}">
				<font color="red"><form:errors path="user.address" /></font>
			</td>
		  </tr>	
	  </table>

	  <br>
	  
	  <table width=590 border=0 cellpadding=0 cellspacing=0>
		  <tr>
			<td align=center>
			<input type="button" value="회원 가입" onClick="userCreate()"> &nbsp;
			<input type="button" value="목록" onClick="userList()">
			</td>
		  </tr>
	  </table>

	  </td>
	</tr>
</table>  
	  </form>
</body>
</html>