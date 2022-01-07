<%--
  Created by IntelliJ IDEA.
  User: lonelyzhou
  Date: 2021/6/8
  Time: 下午 05:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <jsp:include page="/WEB-INF/jsp/head.jsp"/>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/login.css"/>
    <script type="text/javascript" src="js/login.js"></script>
    <title>登录</title>
</head>
<body>
<div class="box">
    <div>
        <div class="d1">${msg}</div>
        <div id="login">
            <h1>Login</h1>
            <%--        <form method="post" action="${pageContext.request.contextPath}/login.do">--%>
            <form method="post" action="" name="login">
                <label>
                    <input type="text" required="required" placeholder="用户名" name="username"/>
                    <input type="password" required="required" placeholder="密码" name="password"/>
                </label>
                <button class="mdui-btn mdui-btn-raised mdui-ripple" onclick="login_in()">登录</button>
                <button class="mdui-btn mdui-btn-raised mdui-ripple" onclick="login_re()">注册</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
