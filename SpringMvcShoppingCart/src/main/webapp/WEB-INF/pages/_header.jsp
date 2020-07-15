<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

<div class="header-container">
 
    <div class="site-name" align="center" style="background-color:lightblue"><font color="blue">BOOKS JUNCTION</font><i class="fa fa-book" style="font-size:40px;color:red"></i>
    </div>
    
    <br>
    <br>
 
    <div class="header-bar">
        <c:if test="${pageContext.request.userPrincipal.name != null}">
        Hello
           <a href="${pageContext.request.contextPath}/accountInfo">
                ${pageContext.request.userPrincipal.name} </a>
         &nbsp;|&nbsp;
           <a href="${pageContext.request.contextPath}/logout">Logout</a><i class="fa fa-sign-out" style="font-size:20px;color:blue"></i>	
 
        </c:if>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            <a href="${pageContext.request.contextPath}/login">Login</a><i class="fa fa-sign-in" style="font-size:20px;color:blue"></i>
        </c:if>
    </div>
</div>