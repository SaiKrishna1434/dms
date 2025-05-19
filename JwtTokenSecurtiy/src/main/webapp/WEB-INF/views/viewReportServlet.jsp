<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>View Report</title>
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

    <script th:inline="javascript">
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
    <h2>View Medical Report</h2>
    <form action="/viewReport" method="post">
        User ID:
        <input type="text" name="userId"
               th:disabled="${!(#authentication.principal.authorities.?[authority == 'ROLE_DOCTOR'] or #authentication.principal.authorities.?[authority == 'ROLE_CUSTOMER'])}"
               required />

        Password: <input type="password" name="password" /><br/>
        Report ID: <input type="text" name="reportId" /><br/>
        <button type="submit">View Report</button>
        <button type="reset" class="reset">Reset</button>
    </form>
    <a class="button-link" href="/home">Back to Home</a>
</div>
</body>
</html>