<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%-- 功能页面 --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<c:set var="basePath" value="${pageContext.request.contextPath}" />
<html>
<head>
    <title>功能页面</title>
</head>
<body>
    <div>欢迎你，${user.username}</div>
    <ul>
        <%-- 当用户有viewProducts这个权限时才显示其中的内容 --%>
        <shiro:hasPermission name="viewProducts">
            <li>
                <a href="${basePath}/product/getAllProducts.action">查看产品信息</a>
            </li>
        </shiro:hasPermission>
    </ul>
</body>
</html>
