<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${material.title} - Course Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-dark bg-dark mb-4">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">⬅ Back to Home</a>
        <span class="text-light">Logged in as: ${pageContext.request.userPrincipal.name}</span>
    </div>
</nav>

<div class="container" style="max-width: 800px;">
    
    <div class="card shadow-sm mb-4">
        <div class="card-body">
            <h2 class="card-title text-primary">${material.title}</h2>
            <hr>
            <p class="card-text fs-5">${material.summary}</p>
            
            <div class="mt-4 p-3 bg-light border rounded">
                <strong>Attached File:</strong>
                <span class="text-muted ms-2">${material.filePath}</span>
                <button class="btn btn-sm btn-outline-primary float-end" disabled>Download (Coming Soon)</button>
            </div>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-white">
            <h4 class="mb-0">Discussion</h4>
        </div>
        <div class="card-body">
            
            <c:choose>
                <c:when test="${empty material.comments}">
                    <p class="text-muted fst-italic">No comments yet. Be the first to start the discussion!</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="comment" items="${material.comments}">
                        <div class="mb-3 pb-3 border-bottom">
                            <strong>${comment.author.username}</strong>
                            <span class="badge bg-secondary ms-1">${comment.author.role == 'ROLE_TEACHER' ? 'Teacher' : 'Student'}</span>
                            <p class="mt-2 mb-0">${comment.content}</p>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            <form action="${pageContext.request.contextPath}/material/${material.id}/comment" method="post" class="mt-4">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="mb-3">
                    <label for="content" class="form-label fw-bold">Add a Comment</label>
                    <textarea class="form-control" name="content" id="content" rows="3" required placeholder="What are your thoughts on this lecture?"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Post Comment</button>
            </form>
            
        </div>
    </div>
</div>

</body>
</html>