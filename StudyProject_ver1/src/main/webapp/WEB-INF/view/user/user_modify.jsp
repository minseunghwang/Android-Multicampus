 
<%@page contentType="text/html; charset=utf-8" isELIgnored="false"%>
<html>
<head>
<title>사용자 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="JavaScript">
function userModify() {
	f.action= "update.do";
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
			<td bgcolor="f4f4f4" height="22">&nbsp;&nbsp;<b>사용자 관리 - 사용자 정보  수정</b></td>
		  </tr>
	  </table>  
	  <br>
	  
	  <!-- modify Form  -->
	  <form name="f" method="post" action="">
	 	   <input type="hidden" name="userid" value="${user.userid }">
	 	   <input type="hidden" name="username" value="${user.username }"> 
	 
	  <table border="0" cellpadding="0" cellspacing="1" width="590" bgcolor="BBBBBB">
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">사용자 아이디</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				${user.userid}
			</td>
		  </tr>
		 
		  
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">이름</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<%-- <input type="text" style="width:240" name="username" value="${user.username}"/>  --%>
				 ${user.username}
			</td>
		  </tr>
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">email</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:240" name="email" value="${user.email}">
			</td>
		  </tr>	
		 <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">phone</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:240" name="phone" value="${user.phone}">
			</td>
		  </tr>	
		  <tr>
			<td width=100 align=center bgcolor="E6ECDE" height="22">address</td>
			<td width=490 bgcolor="ffffff" style="padding-left:10">
				<input type="text" style="width:240" name="address" value="${user.address}">
			</td>
		  </tr>	
		  
	  </table>
	  </form>
	  <br>
	  
	  <table width=590 border=0 cellpadding=0 cellspacing=0>
		  <tr>
			<td align=center>
			<input type="button" value="수정" onClick="userModify()"> &nbsp;
			<input type="button" value="목록" onClick="userList()">
			</td>
		  </tr>
	  </table>

	  </td>
	</tr>
</table>  

</body>
</html>