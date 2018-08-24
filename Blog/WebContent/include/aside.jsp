<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Sidebar Widgets Column -->
      <div class="col-md-4">
        <!-- Search Widget -->
        <div class="card my-4">
          <h5 class="card-header">Search</h5>
          <div class="card-body">
            <div class="input-group">
              <input type="text" class="form-control" placeholder="Search for...">
              <span class="input-group-btn">
                <button class="btn btn-secondary" type="button">Go!</button>
              </span>
            </div>
          </div>
        </div>

        <!-- Categories Widget -->
        <div class="card my-4">
          <h5 class="card-header">Our Sidebar</h5>
          <div class="card-body">
            <div class="list-group">
            <c:if test="${!empty hotpost}">
            	<c:forEach var="hotpost" items="${hotpost}">
	              <a href="<%=request.getContextPath()%>/Board?cmd=board_view&num=${hotpost.num}" class="list-group-item list-group-item-action">조회${hotpost.readcount} ${hotpost.title}</a>
              </c:forEach>
            </c:if>
            </div>
          </div>
        </div>
      </div>