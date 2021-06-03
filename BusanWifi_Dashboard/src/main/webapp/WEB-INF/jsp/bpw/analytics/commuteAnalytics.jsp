<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="ax" uri="http://axisj.com/axu4j"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<ax:layout name="${_layout}">
	<ax:div name="user_css_script">
		<script type="text/javascript">
			$(function() {
				
			});

		</script>
	</ax:div>

	<ax:div name="contents">

		<div class="row">
			<div class="col-md-12">

				<div class="box box-primary">
					<!-- /.box-body -->
					<c:choose>
						<c:when test="${type eq 'real'}">
							<iframe src="${scheme}://${host}/trusted/${ticketId}/views/4/1?:embed=yes&:tabs=yes&:toolbar=yes" style="width:99%; height:900px; padding:0; margin:0; border:1px solid #d2d5d8;"></iframe>	
						</c:when>
						<c:otherwise>
							<iframe src="${scheme}://${host}/trusted/${ticketId}/views/3/1?:embed=yes&:tabs=yes&:toolbar=yes" style="width:99%; height:900px; padding:0; margin:0; border:1px solid #d2d5d8;"></iframe>    	
						</c:otherwise>
					</c:choose>					
				</div>

			</div>
		</div>

	</ax:div>
</ax:layout>