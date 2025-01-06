document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevent default form submission

    // Collect form data
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const subscription = document.getElementById('subscription').value;
    const paymentMethod = document.getElementById('paymentMethod').value;
    const language = document.getElementById('language').value;
    const termsAccepted = document.getElementById('terms').checked;

    if (!termsAccepted) {
        alert('You must agree to the Terms and Conditions.');
        return;
    }

    // API endpoint for registration
    const apiEndpoint = '/api/users/register';

    try {
        const response = await fetch(apiEndpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email,
                password,
                subscription,
                paymentMethod,
                languageId: language,
            }),
        });

        if (response.ok) {
            const data = await response.json();
            alert('Registration successful! Check your email for the activation link.');
        } else {
            const error = await response.json();
            alert(`Registration failed: ${error.error || 'Unknown error'}`);
        }
    } catch (err) {
        console.error('Error occurred during registration:', err);
        alert('Something went wrong. Please try again.');
    }
});
