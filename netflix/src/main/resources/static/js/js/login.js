document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevent the default form submission behavior

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    const apiEndpoint = 'http://localhost:8081/api/users/login';

    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: email,
                password: password,
            }),
        });

        console.log("Raw response:", response);

        // Parse the response or extract error message
        const responseText = await response.text();
        if (!response.ok) {
            console.error("Error response text:", responseText);
            const errorData = JSON.parse(responseText); // Parse error message
            alert(`Login failed: ${errorData.message || "Unknown error occurred."}`);
            return;
        }

        const data = JSON.parse(responseText);
        console.log("Login successful:", data);

        // Redirect based on the role in the response
        switch (data.role) {
            case "JUNIOR":
                window.location.href = "junior-dashboard.html";
                break;
            case "MEDIOR":
                window.location.href = "medior-dashboard.html";
                break;
            case "SENIOR":
                window.location.href = "senior-dashboard.html";
                break;
            case "VIEWER":
                window.location.href = "viewer-dashboard.html";
                break;
            default:
                alert("Unexpected role. Please contact support.");
                console.error(`Unexpected role: ${data.role}`);
        }
    } catch (error) {
        console.error("Error during login:", error);
        alert("An error occurred while trying to log in. Please try again.");
    }
});
