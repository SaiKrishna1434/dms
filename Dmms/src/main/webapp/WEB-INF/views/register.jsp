<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Registration</title>
    <style>
    button[type="submit"],
	button[type="reset"] {
    padding: 10px 20px;
    font-size: 16px;
    border: none;
    border-radius: 6px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

/* Submit button */
button[type="submit"] {
    background-color: #28a745; /* Green */
    color: white;
}

button[type="submit"]:hover {
    background-color: #218838;
}

/* Reset button */
button[type="reset"].reset {
    background-color: #dc3545; /* Red */
    color: white;
    margin-left: 10px;
}

button[type="reset"].reset:hover {
    background-color: #c82333;
}
    
</style>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css' />">

</head>

<body>
<c:if test="${not empty message}">
    <p style="color:green;">${message}</p>
</c:if>

<c:if test="${success}">
    <script>
        // Redirect to /home after 3 seconds
        setTimeout(function () {
            window.location.href = "/home";
        }, 3000);
    </script>
</c:if>

<div class="page-container">
    <h2>Register</h2>
    <form id="registerForm">
        <label for="userId">User ID:</label>
        <input type="text" name="userId" id="userId" required />

        <label for="password">Password:</label>
        <input type="password" name="password" id="password" required
            pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}"
            title="Must contain at least one number, one uppercase, one lowercase letter, and 8+ characters" />

        <label for="dateOfBirth">Date of Birth:</label>
        <input type="date" name="dateOfBirth" id="dateOfBirth" required />

        <label for="emailId">Email:</label>
        <input type="email" name="emailId" id="emailId" required />

        <button type="submit">Register</button>
        <button type="reset" class="reset">Reset</button>
    </form>
    <a class="button-link" href="/home">Back to Home</a>
</div>

<script>
    document.getElementById("registerForm").addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent default form submission

        const data = {
            userId: document.getElementById("userId").value,
            password: document.getElementById("password").value,
            dateOfBirth: document.getElementById("dateOfBirth").value,
            emailId: document.getElementById("emailId").value,
            roles:["CUSTOMER"]
        };

        fetch("/api/members/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
        .then(response => {
            if (response.ok) {
                alert("Registration successful!");
                setTimeout(() => window.location.href = "/home", 3000);
            } else {
                return response.json().then(err => {
                    throw new Error(err.message || "Registration failed");
                });
            }
        })
        .catch(error => {
            alert("Error: " + error.message);
        });
    });
</script>
</body>

</html>
