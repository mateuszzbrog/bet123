<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
  <head>
    <title>Bet123</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" type="text/css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/styles.css" type="text/css" rel="stylesheet">
  </head>
 
  <body>
     
    <nav class = "navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <a href="" class="navbar-brand">Bet123</a>
         
        <button class="navbar-toggle" data-toggle="collapse" data-target=".navHeaderCollapse">
          <span class="glyphicon glyphicon-list"></span>
        </button>
         
        <div class="collapse navbar-collapse navHeaderCollapse">
          <ul class="nav navbar-nav navbar-right">
            <li class="active"><a href="">Tabela</a></li>
            <li><a href="#">Dodaj mecze</a></li>
            <li><a href="${pageContext.request.contextPath}/bet">Typuj</a></li>
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <li><a href="logout">Wyloguj się</a></li>
                </c:when>
                <c:otherwise>
                    <li><a href="login">Zaloguj się</a></li>
                </c:otherwise>
            </c:choose>
          </ul>
        </div>
         
      </div>
    </nav>
    
    
    <div class="container">
        <div class="col-md-8 col-md-offset-2">
            <form class="form-signin" method="post" action="home">
                <h2 class="form-signin-heading">Tabela</h2>
                <DIV style="width: 100%; text-align: center">
		<DIV style="text-align: center; margin-left: auto; width:100%; margin-right: auto">
			<table width="200" border="1">
  				<tr>
  					<th>Miejsce</th>
  					<th>Użytkownik</th>
  					<th>Punkty</th>
  				</tr>
  				<c:if test="${not empty requestScope.users}">
        			<c:forEach var="userr" items="${requestScope.users}">
        				<tr>
        					<td>1</td>
        					<td>
        						<c:out value="${userr.username}" />
        					</td>
        					<td>
        						<c:out value="${userr.result}" />
        					</td>
        				</tr>
        			</c:forEach>
        		</c:if>
  				
			</table>
		</DIV>
	</DIV>
            </form>
        </div>
    </div>
    
    <footer class="footer">
      <div class="container">
        <p class="navbar-text">Bet123 - developed by <a href="http://github.com/mateuszzbrog">Karpiu</a></p>
      </div>
    </footer>
     
    <script src="http://code.jquery.com/jquery-1.11.2.min.js"></script>
    <script src="http://code.jquery.com/jquery-migrate-1.2.1.min.js"></script>
    <script src="resources/js/bootstrap.js"></script>
  </body>
</html>