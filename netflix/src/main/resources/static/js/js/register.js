document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevent default form submission

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    // API endpoint for login
    const apiEndpoint = 'http://localhost:8081/api/users/login';

    fetch(apiEndpoint, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            email,
            password,
        }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Login successful:", data);
            if (data.role === "JUNIOR") {
                window.location.href = "junior.html";
            } else if (data.role === "MEDIOR") {
                window.location.href = "medior.html";
            } else if (data.role === "SENIOR") {
                window.location.href = "senior.html";
            }
        })
        .catch(error => {
            console.error("Error during login:", error);
            alert("Something went wrong during login.");
        });
});
