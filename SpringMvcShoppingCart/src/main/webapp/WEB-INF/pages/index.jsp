<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">


<html>
<head>
<meta charset="UTF-8">
 
<title>Books Shop Online</title>
 
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
 <style>
ul#menu li {
  display:inline;
}
</style>
</head>
<body >
   <jsp:include page="_header.jsp" />
   <jsp:include page="_menu.jsp" />
 
   <div class="page-title">Welcome to BOOKS JUNCTION</div>
       
   <div class="demo-container">
   <h3>Shop by Category
      <!-- <a href="//sagar.com" target="_blank">Click to learn more</a> -->
   </h3>   
   <hr>
   <ul id="menu">
      <li><a href="${pageContext.request.contextPath}/ChildrensBooks/Childrens Books">Children's Books &emsp;&emsp;&emsp;&emsp;</a></li>
      <li><a href="${pageContext.request.contextPath}/BiographiesBooks/Biographies">Biographies & Memoirs &emsp;&emsp;&emsp;&emsp;</a></li>
      <li><a href="${pageContext.request.contextPath}/LiteratureBooks/Literature">Literature & Fiction &emsp;&emsp;&emsp;&emsp;</a></li>
      <li><a href="${pageContext.request.contextPath}/SCI_FIBooks/Sci-Fi">Sci-Fi & Fantasy &emsp;&emsp;&emsp;&emsp;</a></li>
   </ul>
   </div>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
   <br>
  
   <jsp:include page="_footer.jsp" />
 
</body>
</html>