<%--
  Created by IntelliJ IDEA.
  User: lonelyzhou
  Date: 2021/6/9
  Time: 下午 07:44
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<head>
    <title>员工列表</title>
    <jsp:include page="head.jsp"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/empList.js"></script>
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/empList.css">
    <link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
</head>
<body onload="showTime()">
<div class="box">
    <div class="d1">
        <ul class="mdui-list">
            <li class="mdui-list-item mdui-ripple">
                <span class="h3">部门</span>
            </li>
            <c:forEach items="${depts}" var="dept">
                <li class="mdui-list-item mdui-ripple" onclick="send(${dept.id})">
                        ${dept.name}
                </li>
            </c:forEach>
        </ul>
        <div class="d3">
            <button class="mdui-btn mdui-ripple">
                <i class="mdui-icon material-icons">&#xe192;</i>
                <div id="time"></div>
                <div><i class="mdui-icon material-icons">&#xe851;</i>当前用户</div>
                <div>${UserSession.username}</div>
            </button>
            <button class="mdui-btn mdui-ripple"><a
                    href="${pageContext.request.contextPath }/logout.do"><i
                    class="mdui-icon material-icons">&#xe879;</i>退出登录</a></button>
        </div>

    </div>
    <div class="d2">
        <table class="mdui-table mdui-table-hoverable">
            <thead>
            <tr>
                <th>员工id</th>
                <th>姓名</th>
                <th>性别</th>
                <th>信息</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${emps}" var="emp">
                <tr>
                    <td>${emp.id}</td>
                    <td>${emp.name}</td>
                    <td>${emp.sex}</td>
                    <td>${emp.info}</td>
                    <td>
                        <div>
                            <button class="mdui-btn mdui-ripple" onclick="updateInfo(${emp.id},${UserSession.stat})">
                                修改
                            </button>
                            <button class="mdui-btn mdui-ripple"
                                    onclick="deleteEmp(${emp.id},${emp.deptId},${UserSession.stat})">删除
                            </button>
                        </div>
                    </td>
                </tr>
                <form method="post" action="<%=request.getContextPath()%>/updateEmp.do">
                    <tr class="updateInfo${emp.id}" style="display: none">
                        <td>
                            <label>
                                <input type="text" required="required" placeholder="${emp.id}" value="${emp.id}"
                                       readonly="readonly" name="id"/>
                            </label>
                        </td>
                        <td>
                            <label>
                                <input type="text" required="required" placeholder="${emp.name}" value="${emp.name}"
                                       name="name"/>
                            </label>
                        </td>
                        <td>
                            <label>
                                <input type="text" required="required" placeholder="${emp.sex}" value="${emp.sex}"
                                       name="sex"/>
                            </label>
                        </td>
                        <td>
                            <label>
                                <input type="text" placeholder="${emp.info}" value="${emp.info}"
                                       name="info"/>
                            </label>
                        </td>
                        <td>
                            <div>
                                <button class="mdui-btn mdui-ripple" type="submit" onclick="updateInfo(${emp.id})">确认
                                </button>
                            </div>
                        </td>
                    </tr>
                </form>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
