<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>Cos Blog</title>

<!-- Bootstrap core CSS -->
<link href="<%=request.getContextPath()%>/css/bootstrap.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="<%=request.getContextPath()%>/css/blog-home.css" rel="stylesheet">

<!-- Bootstrap core JavaScript -->
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.bundle.min.js"></script>

<!-- Smart Editor -->
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/editor/photo_uploader/plugin/hp_SE2M_AttachQuickPhoto.js" charset="utf-8"></script>

</head>

<body>
	<!-- Nav Include -->
	<jsp:include page="/include/header.jsp"/>

  <!-- Page Content -->
  <div class="container">
    <div class="row">
      <!-- Blog Entries Column -->
      <div class="col-md-8">
        <div class="content-section">
        	<form name="w_form" method="POST" action="<%=request.getContextPath()%>/Board?cmd=board_updateProc">
        		<input type="hidden" name="num" value="${board.num}">
        		<input type="hidden" name="id" value="${board.id}">
        		<fieldset class="form-gruop">
        			<legend class="border-bottom mb-4">Modify</legend>
        			<div class="form-gruop"> 
        				<label class="form-control-label">Title</label>
        						<input class="form-control form-control-lg" type="text" name="title" value="${board.title}" required >     					
        			</div>
        			<div class="form-gruop">
        				<label class="form-control-label">Content</label>
        				<textarea id="textAreaContent" rows="15" cols="80" style="width:100%" name="content" autofocus>${board.content}</textarea>
        			</div>
				
        			<button class="btn btn-outline-primary" type="button" onclick="submitContents(this)">Done</button>
        		</fieldset>
        	</form>
        </div>
      </div>

      <!-- Sidebar Include -->
			<jsp:include page="/include/aside.jsp"/>
			
    </div>
    <!-- /.row -->

  </div>
  <!-- /.container -->
  

<!-- Footer -->
<footer class="py-5 bg-dark">
  <div class="container">
    <p class="m-0 text-center text-white">Copyright &copy; Your Website 2018</p>
  </div>
  <!-- /.container -->
</footer>

<!-- Naver Smart Editor 2 -->
<script>
 	var form = document.w_form;
  var oEditors = [];
  nhn.husky.EZCreator.createInIFrame({
      oAppRef: oEditors,
      elPlaceHolder: "textAreaContent",
      sSkinURI: "<%=request.getContextPath()%>/editor/SmartEditor2Skin.html",
      fCreator: "createSEditor2"
  });
   
  //‘저장’ 버튼을 누르는 등 저장을 위한 액션을 했을 때 submitContents가 호출된다고 가정한다.
  function submitContents(elClickedObj) {
      // 에디터의 내용이 textarea에 적용된다.
      oEditors.getById["textAreaContent"].exec("UPDATE_CONTENTS_FIELD", [ ]);
   
      // 에디터의 내용에 대한 값 검증은 이곳에서
      // document.getElementById("textAreaContent").value를 이용해서 처리한다.
    
      try {
          elClickedObj.form.submit();
      } catch(e) {
       
      }
  }
   
  // textArea에 이미지 첨부
  function pasteHTML(filepath){
      var sHTML = '<img src="<%=request.getContextPath()%>/editor/upload/'+filepath+'">';
      oEditors.getById["textAreaContent"].exec("PASTE_HTML", [sHTML]);
  }
</script>

</body>

</html>
