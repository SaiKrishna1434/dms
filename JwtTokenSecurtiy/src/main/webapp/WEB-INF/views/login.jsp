<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <head>
 <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css' />">
 
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
</head>
<body>
    
    <div class="page-container">
    <h2>Login</h2>
    <form id="loginForm">
        <label>User Id:</label><input type="text" id="userName" name="userName"><br>
        <label>Password:</label><input type="password" id="password" name="password"><br>
        <button type="submit">Login</button>
    </form>
    <a class="button-link" href="/home">Back to Home</a>
    </div>

    <script>
	document.getElementById("loginForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const credentials = {
        userName: document.getElementById("userName").value,
        password: document.getElementById("password").value
    };

    fetch('http://localhost:8099/user/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error("Login failed: " + text);
            });
        }
        return response.json();
    })
    .then(data => {
        if (data.token) {
            sessionStorage.setItem("jwtToken", data.token); // ✅ Save token
            alert("Login successful!");
            window.location.href = "home"; // ✅ Redirect to home
        }
    })
    .catch(error => {
        console.error("Error during login:", error);
        alert(error.message);
    });
});
</script>

</body>
</html>

