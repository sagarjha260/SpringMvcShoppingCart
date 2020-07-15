<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  
 
 
<div class="menu-container">
  
   <a href="${pageContext.request.contextPath}/"><i class="fa fa-home" style="font-size:24px;color:blue"></i>
     Home
   </a>
   |
   <a href="${pageContext.request.contextPath}/productList">
      Product List
   </a>
   |
   
   <a href="${pageContext.request.contextPath}/viewBook/1">
      All Books
   </a>
   
   |
   <a href="${pageContext.request.contextPath}/shoppingCart"><i class="fa fa-shopping-cart" style="font-size:24px;color:blue"></i>
      My Cart
   </a>
   |
   <security:authorize  access="hasAnyRole('ROLE_MANAGER','ROLE_EMPLOYEE')">
     <a href="${pageContext.request.contextPath}/orderList">
         Order List
     </a>
     |
   </security:authorize>
   
   <security:authorize  access="hasRole('ROLE_MANAGER')">
      <a href="${pageContext.request.contextPath}/product">
          Create Product
      </a>      |
   </security:authorize>
  
</div>