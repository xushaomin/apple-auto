				
	   		<div class="pagination pull-right">
	     	
	        	<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
				<input type="hidden" id="pageNumber" name="pageNo" value="${page.pageNo}" />
		        <!-- start of 分页 -->
		        <ul>
				<#if (totalPages > 1)>
						
						<#if hasPrevious>
							<li><a href="javascript:displaytagform('filterForm',[{f:'pageNo',v:'${previousPageNumber}'}])">&laquo;</a></li>
						<#else>
							<li class="previous"><a href="javascript:void(0);">&laquo;</a></li>
						</#if>
						
						<#list segment as segmentPageNumber>
							<#if segmentPageNumber != pageNumber>
								<li><a href="javascript:displaytagform('filterForm',[{f:'pageNo',v:'${segmentPageNumber}'}])">${segmentPageNumber}</a></li>
							<#else>
								<li class="active"><a href="javascript:displaytagform('filterForm',[{f:'pageNo',v:'${segmentPageNumber}'}])">${segmentPageNumber}</a></li>
							</#if>
						</#list>

						<#if isLast>
							<li class="next"><a href="javascript:void(0);">&raquo;</a></li>
						<#else>
							<li class="next"><a href="javascript:displaytagform('filterForm',[{f:'pageNo',v:'${lastPageNumber}'}])">&raquo;</a></li>
						</#if>
				</#if>
				</ul>
	        	<!-- end of 分页 -->
		</div>