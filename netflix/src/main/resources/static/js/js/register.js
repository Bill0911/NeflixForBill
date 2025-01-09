document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();

    if (!email || !password) {
        alert('Please enter both email and password.');
        return;
    }

    const apiEndpoint = '/api/users/login';
    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });

        if (response.ok) {
            const data = await response.json();
            const { role, token } = data;

            localStorage.setItem('authToken', token);

            if (role === 'JUNIOR') {
                window.location.href = '/junior-dashboard.html';
            } else if (role === 'MEDIOR') {
                window.location.href = '/medior-dashboard.html';
            } else if (role === 'SENIOR') {
                window.location.href = '/senior-dashboard.html';
            } else {
                alert('Unknown role. Please contact support.');
            }
        } else {
            const error = await response.json();
            alert(`Login failed: ${error.error || 'Invalid credentials'}`);
        }
    } catch (err) {
        console.error('Error occurred during login:', err);
        alert('Something went wrong. Please try again later.');
    }
});
