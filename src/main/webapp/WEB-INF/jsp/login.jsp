<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Login - Course Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 500px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h2 class="text-center mb-4">Login</h2>

            <%-- Error & Success Messages --%>
            <c:if test="${param.error != null}">
                <div class="alert alert-danger">Invalid username and password.</div>
            </c:if>
            <c:if test="${param.registered != null}">
                <div class="alert alert-success">Registration successful! Please log in.</div>
            </c:if>

            <%-- Action goes to Spring Security's default processing URL --%>
            <form action="${pageContext.request.contextPath}/login" method="post">
                <%-- CSRF Token required by Spring Security --%>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"${pageContext.request.contextPath}/>
                
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" name="username" class="form-control" required autofocus>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Sign In</button>
            </form>
            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/register">Don't have an account? Register here.</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>