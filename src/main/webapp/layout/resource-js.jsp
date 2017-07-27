<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="path" value="${pageContext.request.contextPath}" />

<!-- 全局JS -->
<script src="assets/hplus/js/jquery.min.js"></script>
<script src="assets/hplus/js/bootstrap.min.js"></script>
<script src="assets/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="assets/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<!-- 自定义js -->
<script src="assets/hplus/js/hplus.js"></script>
<script src="assets/hplus/js/content.js"></script>
<!-- 第三方JS -->
<script src="assets/hplus/js/plugins/pace/pace.min.js"></script>
<script src="assets/hplus/js/plugins/iCheck/icheck.min.js"></script>
<script src="assets/hplus/js/plugins/toastr/toastr.min.js"></script>
<script src="assets/lib/layer/3.0.3/layer.min.js"></script>
<script src="assets/lib/jquery/jquery.datatables/1.10.15/js/jquery.dataTables.min.js"></script>
<script src="assets/lib/jquery/jquery.datatables/1.10.15/js/dataTables.bootstrap.min.js"></script>
<script src="assets/lib/jquery/jquery.datatables/FixedHeader-3.1.2/js/dataTables.fixedHeader.min.js"></script>
<script src="assets/lib/jquery/jquery.validate/1.16.0/jquery.validate.min.js"></script>
<script src="assets/lib/jquery/jquery.validate/1.16.0/localization/messages_zh.min.js"></script>
<script src="assets/lib/jquery/jquery.serialize/2.5.0/jquery.serialize-object.min.js"></script>
<script src="assets/lib/zTree/3.5.29/js/jquery.ztree.all.js"></script>
<!-- 我的JS -->
<script src="${path}${URLProvider.getForLookupPath('/assets/mrathena.js')}"></script>
<script src="${path}${URLProvider.getForLookupPath('/assets/custom.js')}"></script>