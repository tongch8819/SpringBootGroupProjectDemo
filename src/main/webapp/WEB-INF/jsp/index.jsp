<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%-- Jakarta EE 10 JSTL Core Tag Library --%>
        <%@ taglib prefix="c" uri="jakarta.tags.core" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <title>Course Portal - Index</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
            </head>

            <body class="bg-light">

                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                    <div class="container">
                        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Course Portal</a>
                        <div class="d-flex align-items-center">
                            <%-- If the user is NOT logged in (userPrincipal is null) --%>
                                <c:if test="${pageContext.request.userPrincipal == null}">
                                    <a href="${pageContext.request.contextPath}/login"
                                        class="btn btn-outline-light me-2">Login</a>
                                    <a href="${pageContext.request.contextPath}/register"
                                        class="btn btn-primary">Register</a>
                                </c:if>

                                <%-- If the user IS logged in (userPrincipal is not null) --%>
                                    <c:if test="${pageContext.request.userPrincipal != null}">
                                        <span class="text-light me-3">
                                            Welcome, ${pageContext.request.userPrincipal.name}!
                                        </span>
                                        <form action="${pageContext.request.contextPath}/logout" method="post"
                                            class="m-0">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                            <button type="submit" class="btn btn-danger">Logout</button>
                                        </form>
                                    </c:if>
                        </div>
                    </div>
                </nav>

                <div class="container mt-5">
                    <div class="p-5 mb-4 bg-white rounded-3 shadow-sm border">
                        <h1 class="display-5 fw-bold">${courseName}</h1>
                        <p class="col-md-8 fs-4 text-muted">${courseDescription}</p>
                    </div>

                    <div class="row">
                        <div class="col-md-6 mb-4">
                            <div class="card shadow-sm h-100">
                                <div class="card-header bg-primary text-white">
                                    <h3 class="card-title h5 mb-0">Course Materials (Lectures)</h3>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${empty materials}">
                                            <li class="list-group-item text-muted">No lectures uploaded yet.</li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="material" items="${materials}">
                                                <a href="${pageContext.request.contextPath}/material/${material.id}"
                                                    class="list-group-item list-group-item-action">
                                                    <strong>${material.title}</strong>
                                                </a>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>
                        </div>

                        <div class="col-md-6 mb-4">
                            <div class="card shadow-sm h-100">
                                <div class="card-header bg-success text-white">
                                    <h3 class="card-title h5 mb-0">Active Polls</h3>
                                </div>
                                <ul class="list-group list-group-flush">
                                    <c:choose>
                                        <c:when test="${empty polls}">
                                            <li class="list-group-item text-muted">No active polls.</li>
                                        </c:when>
                                        <c:otherwise>
                                            <c:forEach var="poll" items="${polls}">
                                                <a href="${pageContext.request.contextPath}/poll/${poll.id}"
                                                    class="list-group-item list-group-item-action">
                                                    Q: ${poll.question}
                                                </a>
                                            </c:forEach>
                                        </c:otherwise>
                                    </c:choose>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>

            </body>

            </html>