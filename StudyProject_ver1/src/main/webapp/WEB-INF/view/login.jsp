<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>login</title>

</head>
<body>

<h2>로그인 폼</h2>
<form id="fomr1" name="form1"  action="login.do" method="post" >
    <table border="1">
       <tr>
          <td colspan="2">
          <span style="color:red">${msg}</span>
          </td>
       </tr>
    
       <tr>
          <td>ID</td>
          <td><input type="text" id="id" name="userid" required="required"></td>
       </tr>
       <tr>
          <td>PW</td>
          <td><input type="text" id="pw" name="userpw" 
                  required="required"
     pattern="((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{4,7})"></td>
       </tr>
       <tr align="center">
          <td colspan="2">
             <input type="submit" value="로그인" >
             <input type="reset" value="취소">
          </td>
       </tr>
       <tr>
          <td colspan="2">
             <a href="${pageContext.request.contextPath}/user/add.do"> 회원가입</a>
          </td>
       </tr>
    </table>
</form>
    
</body>
</html>