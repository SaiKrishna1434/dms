<html>
<head><title>Search Medicare Service</title>
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
    <h2>Search for Medicare Services</h2>
    <form action="SearchServiceServlet" method="get">
	<label for="plan">Select Service</label>
        <select id="service" name="service" required>
            <option value="">--Select--</option>
            <option value="Basic">Service 1</option>
          	<option value="Advanced">S 2</option>
        </select>
 			<button type="submit">Apply</button>
            <button type="reset" class="reset">Reset</button>
    </form>
 <a class="button-link" href="/home">Back to Home</a>
 </div>
</body>
</html>
