document.addEventListener('DOMContentLoaded', function() {
    const baseUrl = `${window.location.protocol}//${window.location.hostname}:${window.location.port}`;

    document.getElementById('registerForm').addEventListener('submit', async function(event) {
        event.preventDefault();
        const user = {
            name: document.getElementById('registerName').value,
            email: document.getElementById('registerEmail').value,
            password: document.getElementById('registerPassword').value
        };

        const response = await fetch(`${baseUrl}/api/users/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(user)
        });

        const result = await response.text();
        document.getElementById('registerResult').innerText = result;
    });

    document.getElementById('loginForm').addEventListener('submit', async function(event) {
        event.preventDefault();
        const loginRequest = {
            email: document.getElementById('loginEmail').value,
            password: document.getElementById('loginPassword').value
        };

        const response = await fetch(`${baseUrl}/api/users/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginRequest)
        });

        const result = await response.text();
        document.getElementById('loginResult').innerText = result;
    });
});