document.getElementById('loginForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevent default form submission

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const apiEndpoint = 'http://localhost:8081/api/users/login';

    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ email, password }),
        });

        const contentType = response.headers.get('Content-Type');
        if (!response.ok || !contentType || !contentType.includes('application/json')) {
            const responseText = await response.text(); // Fallback for non-JSON error
            console.error('Error response text:', responseText);
            alert(`Login failed: ${responseText || 'Unknown error occurred'}`);
            return;
        }

        const data = await response.json();

        if (data.token) {
            localStorage.setItem('token', data.token); // Save the token

            switch (data.role) {
                case 'JUNIOR':
                    window.location.href = 'junior-dashboard.html';
                    break;
                case 'MEDIOR':
                    window.location.href = 'medior-dashboard.html';
                    break;
                case 'SENIOR':
                    window.location.href = 'senior-dashboard.html';
                    break;
                case 'VIEWER':
                    window.location.href = 'viewer-dashboard.html';
                    break;
                default:
                    alert('Unexpected role. Please contact support.');
                    console.error(`Unexpected role: ${data.role}`);
            }
        } else {
            alert('Login failed: Missing authentication token.');
        }
    } catch (error) {
        console.error('Error during login:', error);
        alert('An error occurred while trying to log in. Please try again later.');
    }
});
