<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
  #img-input {
    display: none;
  }
  .upload-box {
    width: 150px;
    height: 150px;
    border: 3px dashed orange;
    display: flex;
    justify-content: center;
    align-items: center;
    color: red;
    font-weight: 700;
    cursor: pointer;
  }
</style>

</head>
<body>

  <h1>파일 업로드 예제</h1>

  <div class="upload-box">여기를 눌러 파일을 올려주세요</div>

  <form action="/upload/file" method="post" enctype="multipart/form-data">
    
    <input type="file" name="thumbnail" id="img-input" accept="image/*">
    
    <button type="submit">전송</button>
  </form>


  <script>

    document.querySelector('.upload-box').onclick = e => {
      document.getElementById('img-input').click();
    };
  </script>
</body>
</html>