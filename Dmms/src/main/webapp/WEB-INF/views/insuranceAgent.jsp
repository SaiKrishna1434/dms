<html>
<head><title>Insurance Agent</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css' />">
<script>
document.addEventListener("DOMContentLoaded", function () {
    const token = sessionStorage.getItem("jwtToken");

    if (!token) {
        alert("Please login to access this page.");
        window.location.href = "/login"; // Change this to your actual login page route
    }
});
</script>

<script>
document.getElementById('insuranceForm').addEventListener('submit', async function(e) {
    e.preventDefault(); // stop normal form submission

    const jwt = localStorage.getItem('jwt');
    if (!jwt) {
        alert("Please login first.");
        window.location.href = "/login";
        return;
    }

    const userId = document.querySelector('[name="userId"]').value;
    const password = document.querySelector('[name="password"]').value;
    const location = document.querySelector('[name="location"]').value;

    const res = await fetch('/InsuranceServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + jwt
        },
        body: JSON.stringify({ userId, password, location })
    });

    if (res.ok) {
        const result = await res.json();
        // Process the result or display data
        alert("Agent Found: " + JSON.stringify(result));
    } else {
        alert("Request failed: " + res.status);
    }
});
</script>

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
    <h2>Find Insurance Agent</h2>
    <form action="InsuranceServlet" method="post">
        User ID: <input type="text" name="userId" /><br/>
        Password: <input type="password" name="password" /><br/>
        Location: <input type="text" name="location" /><br/>
        <button type="submit">Find Agent</button>
        <button type="reset" class="reset">Reset</button>
    </form>
   <a class="button-link" href="/home">Back to Home</a>
    </div>
</body>
</html>
