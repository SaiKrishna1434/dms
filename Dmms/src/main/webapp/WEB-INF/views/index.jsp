<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Diagnostic Medicare Center</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f2f5;
            margin: 0;
            padding: 0;
        }
        .container {
            text-align: center;
            padding-top: 60px;
        }
       h1 {
            color: #2c3e50;
        }
        .nav-buttons {
            margin-top: 30px;
        }
        .nav-buttons a {
            display: inline-block;
            margin: 12px;
            padding: 14px 24px;
            text-decoration: none;
            background-color: #3498db;
            color: white;
            border-radius: 8px;
            font-size: 16px;
        }
        .nav-buttons a:hover {
            background-color: #2980b9;
        }
    </style>
    
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
    
    
</head>
<body>

<button onclick="logout()">Logout</button>

<script>
function logout() {
    sessionStorage.removeItem("jwtToken");
    window.location.href = "login";
}
</script>



<div class="container">
    <h1>Welcome to Diagnostic Medicare Center</h1>
 	<a class="button-link" href="/login">Login</a>
 	<div class="page-container">
    <div class="nav-buttons">
        <p>Please choose a service:</p>
        <br>
        <a href="/register">Create Account</a>
        <a href="/searchService">Search Medicare Services</a>
        <a href="/applyCheckup">Apply for Health Checkup</a>
        <a href="/insuranceAgent">Find Insurance Agent</a>
        <a href="/viewReport">View Medical Reports</a>
    </div>
 	</div>
</div>
</body>
</html>
