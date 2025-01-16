document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const paymentMethod = document.getElementById('paymentMethod').value;
    const subscription = parseInt(document.getElementById('subscription').value, 10);
    const language = parseInt(document.getElementById('language').value, 10);
    const termsAccepted = document.getElementById('terms').checked;

    if (!termsAccepted) {
        alert('You must agree to the Terms and Conditions.');
        return;
    }

    const payload = {
        email,
        password,
        paymentMethod,
        subscription,
        language
    };

    try {
        const response = await fetch('http://localhost:8081/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload)
        });

        const responseData = await response.json();

        if (!response.ok) {
            throw new Error(responseData.error || 'Failed to register');
        }

        alert('Registration successful! Please check your email to activate your account.');
        console.log('Activation Link:', responseData.activationLink);
    } catch (error) {
        console.error('Error occurred during registration:', error);
        alert('Registration failed: ' + error.message);
    }
});
