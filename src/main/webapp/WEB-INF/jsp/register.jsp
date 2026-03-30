<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Register - Course Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container mt-5" style="max-width: 600px;">
    <div class="card shadow-sm">
        <div class="card-body p-4">
            <h2 class="text-center mb-4">Student Registration</h2>
            
            <form action="${pageContext.request.contextPath}/register" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"${pageContext.request.contextPath}/>
                
                <div class="mb-3">
                    <label class="form-label">Username</label>
                    <input type="text" name="username" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <input type="password" name="password" class="form-control" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Full Name</label>
                    <input type="text" name="fullName" class="form-control" required>
                </div>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Email Address</label>
                        <input type="email" name="email" class="form-control" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Phone Number</label>
                        <input type="text" name="phoneNumber" class="form-control" required>
                    </div>
                </div>
                <button type="submit" class="btn btn-success w-100 mt-3">Register</button>
            </form>
            <div class="text-center mt-3">
                <a href="${pageContext.request.contextPath}/login">Already have an account? Log in.</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>