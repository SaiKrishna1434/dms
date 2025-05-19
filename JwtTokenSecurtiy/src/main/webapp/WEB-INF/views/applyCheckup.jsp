<html>
<head><title>Apply for Health Checkup</title>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script>
document.addEventListener("DOMContentLoaded", function () {
    const token = sessionStorage.getItem("jwtToken");

    if (!token) {
        alert("Please login to access this page.");
        window.location.href = "/login"; // Change this to your actual login page route
    }
});
</script>
</head>
<body>
   <div class="page-container">
    <h2>Apply for Health Checkup</h2>
    <form action="ApplyCheckupServlet" method="post">
        <label for="userId">User ID</label>
        <input type="text" id="userId" name="userId" required />

        <label for="plan">Select Checkup Plan</label>
        <select id="plan" name="plan" required>
            <option value="">--Select--</option>
            <option value="Basic">Basic Health Check</option>
          	<option value="Advanced">Advanced Health Check</option>
        </select>

             <button type="submit">Apply</button>
            <button type="reset" class="reset">Reset</button>
    </form>
    <a class="button-link" href="/home">Back to Home</a>
</div>

</body>
</html>
