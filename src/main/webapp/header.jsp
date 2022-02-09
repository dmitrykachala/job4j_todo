<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<div class="row">
    <ul class="nav">
        <c:if test="${user == null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/reg.jsp">Регистрация</a>
            </li>
        </c:if>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/index.do">На главную</a>
        </li>
        <c:if test="${user == null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
            </li>
        </c:if>
        <c:if test="${user != null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"><c:out value="${user.name}"/> | Выйти</a>
            </li>
        </c:if>
    </ul>
</div>