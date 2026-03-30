<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Poll - Course Portal</title>
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
    
    <div class="card shadow-sm mb-4 border-success">
        <div class="card-header bg-success text-white">
            <h4 class="mb-0">Weekly Poll</h4>
        </div>
        <div class="card-body">
            <h3 class="card-title mb-4">${poll.question}</h3>
            
            <form action="${pageContext.request.contextPath}/poll/${poll.id}/vote" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                
                <div class="list-group mb-4">
                    <c:forEach var="option" items="${poll.options}">
                        <label class="list-group-item d-flex justify-content-between align-items-center list-group-item-action cursor-pointer">
                            <div>
                                <input class="form-check-input me-2" type="radio" name="optionId" value="${option.id}" 
                                       ${option.id == userVoteId ? 'checked' : ''} required>
                                ${option.optionText}
                            </div>
                            <span class="badge bg-primary rounded-pill">${option.votes.size()} votes</span>
                        </label>
                    </c:forEach>
                </div>
                
                <button type="submit" class="btn btn-success w-100">
                    ${userVoteId != null ? 'Update My Vote' : 'Submit Vote'}
                </button>
            </form>
        </div>
    </div>

    <div class="card shadow-sm">
        <div class="card-header bg-white">
            <h4 class="mb-0">Poll Discussion</h4>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty poll.comments}">
                    <p class="text-muted fst-italic">No comments yet.</p>
                </c:when>
                <c:otherwise>
                    <c:forEach var="comment" items="${poll.comments}">
                        <div class="mb-3 pb-3 border-bottom">
                            <strong>${comment.author.username}</strong>
                            <span class="badge bg-secondary ms-1">${comment.author.role == 'ROLE_TEACHER' ? 'Teacher' : 'Student'}</span>
                            <p class="mt-2 mb-0">${comment.content}</p>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>

            <form action="${pageContext.request.contextPath}/poll/${poll.id}/comment" method="post" class="mt-4">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <div class="mb-3">
                    <textarea class="form-control" name="content" rows="3" required placeholder="Discuss this poll..."></textarea>
                </div>
                <button type="submit" class="btn btn-outline-success">Post Comment</button>
            </form>
        </div>
    </div>
</div>

</body>
</html>