document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    const apiEndpoint = await 'http://localhost:8081/api/users/login';

    fetch(apiEndpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email: email.trim(),
            password: password.trim(),
        }),
    })
        .then(async response => {
            console.log("Raw response:", response);
            const errorText = await response.text();
            console.error("Error response text:", errorText);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return JSON.parse(errorText);
        })
        .then(data => {
            console.log("Login successful:", data);
            if (data.role === "JUNIOR") {
                window.location.href = "junior-dashboard.html";
            } else if (data.role === "MEDIOR") {
                window.location.href = "medior-dashboard.html";
            } else if (data.role === "SENIOR") {
                window.location.href = "senior-dashboard.html";
            }
        })
        .catch(error => {
            console.error("Error during login:", error);
            alert("Login failed. Please check your credentials.");
        });
});
