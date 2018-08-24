<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
//cookie
	String cookieID = null;
	Cookie[] cookies = request.getCookies();
	for(Cookie c : cookies){
		if(c.getName().equals("cookieID")){
			//find element out
			cookieID = c.getValue();
			System.out.println(cookieID);
		}
	}
%>
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
<script src="<%=request.getContextPath()%>/js/validation.js"></script>

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
        	<form method="POST" action="<%=request.getContextPath()%>/Member?cmd=member_login" onsubmit="return hangeulCheck(this)">
        		<fieldset class="form-gruop">
        			<legend class="border-bottom mb-4"></legend>
        			<div class="form-gruop"> 
        				<label class="form-control-label">ID</label>
        				<c:choose>
        					<c:when test="${empty cookie.cookieID.value}">
        						<input class="form-control form-control-lg" type="text" name="id" maxlength="20" required autofocus>
        					</c:when>
        					<c:otherwise>
        						<input class="form-control form-control-lg" type="text" name="id" maxlength="20" value="<%=cookieID %>" required autofocus>
        					</c:otherwise>
        				</c:choose>
        			</div>
        			<div class="form-gruop">
        				<label class="form-control-label">Password</label>
        				<input class="form-control form-control-lg" type="password" name="password" maxlength="20" required>
        			</div>
        			<div class="border-top pt-3">
        				<small class="text-muted">
        					Remember ID <input type="checkbox" name="idsave" value="on">
        				</small>
        			</div>
        			<button class="btn btn-outline-primary" type="submit">Log in</button>
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

</body>

</html>
