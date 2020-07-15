<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<html>  
 	</head>
		<meta charset="UTF-8">
		<title>Books List</title>
		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
	</head>
	<body>  
	    <jsp:include page="_header.jsp" />
   		<jsp:include page="_menu.jsp" />
   
		<fmt:setLocale value="en_US" scope="session"/>
 
  		<div class="page-title">Available Books</div>
  		<c:forEach items="${msg}" var="prodInfo">
  		<div class="product-preview-container">
  		<ul>
            <li><img class="product-image" src="${pageContext.request.contextPath}/productImage?code=${prodInfo.code}" /></li>
            <li>Code: ${prodInfo.code}</li>
            <li>Name: ${prodInfo.name}</li>
            <li>Price: <fmt:formatNumber value="${prodInfo.price}" type="currency"/></li>
            <li> <a href="${pageContext.request.contextPath}/buyProduct?code=${prodInfo.code}">
                    Buy Now
                </a>
            </li>
            <!-- For Manager edit Product -->
            <security:authorize  access="hasRole('ROLE_MANAGER')">
              <li><a style="color:red;"
                  href="${pageContext.request.contextPath}/product?code=${prodInfo.code}">
                    Edit Product</a></li><i class="fa fa-edit" style="font-size:24px;color:blue"></i>	
            </security:authorize>
	    </ul>
	    </div>
   		</c:forEach>   
	   <br/>    
	   <a href="/viewBook/1">1</a>     
	   <a href="/viewBook/10">2</a>     
	   <a href="/viewBook/20">3</a>
	   <a href="/viewBook/30">4</a>  
	   <a href="/viewBook/40">5</a>      

   <br>
   <br>
   <br>
   <br>
   <br>
   <jsp:include page="_footer.jsp" />
	</body>  
</html>  