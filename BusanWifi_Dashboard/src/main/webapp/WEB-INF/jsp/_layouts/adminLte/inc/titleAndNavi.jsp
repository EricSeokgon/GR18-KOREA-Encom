<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
     <script>
      document.title = '${menu_nm }';
    </script>
    <section class="content-header">
      <h1>
        ${menu_nm }

        <c:if test="${data.sub_menu != '' and data.sub_menu !=null }">
     		<  ${data.sub_menu}
        </c:if>
<%--          <c:import url="/base/pi/PIStatus.do?menu_no=${data._idxn}" /> --%>
      </h1>
      <ol class="breadcrumb">
      
		<c:choose>
		    <c:when test="${data.sub_menu != '' and data.sub_menu !=null }">
<%-- 				<a href="<c:url value='${menu_worked_url }'/>"><i class="fa fa-arrow-left"></i>  <b>Back</b></a> --%>
				<a href="javascript:history.back();"><i class="fa fa-arrow-left"></i>  <b>Back</b></a>
		    </c:when>
		    <c:otherwise>
		      	     <li><a href="<c:url value='/'/>"><i class="fa fa-home"></i> </a></li>
				 	<c:forEach var="list" items="${nvaiList}"    varStatus="i"   >
				 		<c:if test="${i.index>'0'}">
					    <li><a href="<c:url value='${list.worked_url }'/>">${list.menu_nm }</a></li>
					    </c:if>
					</c:forEach>
		    </c:otherwise>
		</c:choose>      
               
      </ol>
    </section>
    
 

 