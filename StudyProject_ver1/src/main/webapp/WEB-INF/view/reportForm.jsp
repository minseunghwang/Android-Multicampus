<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>report Form</title>
</head>
<body>
 <h2>report 제출 </h2>
 
 <form action="report.do" method="post" enctype="multipart/form-data">
     과제 파일 : <input type="file" name="file"><br>
     <input type="submit" value="제출">
 </form>
</body>
</html>