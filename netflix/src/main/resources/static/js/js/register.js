document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevent form default submission

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const paymentMethod = document.getElementById('paymentMethod').value;
    const subscription = document.getElementById('subscription').value;
    const language = parseInt(document.getElementById('language').value, 10);
    const termsAccepted = document.getElementById('terms').checked;

    if (!termsAccepted) {
        alert('You must agree to the Terms and Conditions.');
        return;
    }

    // Ensure the subscription type is valid
    const validSubscriptions = ['SD', 'HD', 'UHD'];
    if (!validSubscriptions.includes(subscription)) {
        alert('Invalid subscription type.');
        return;
    }

    const payload = {
        email,
        password,
        paymentMethod,
        subscription,
        language
    };

    console.log('Payload:', payload); // Log the payload for debugging

    try {
        const response = await fetch('http://localhost:8081/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        });

        const responseData = await response.json(); // Parse JSON response

        if (!response.ok) {
            throw new Error(responseData.error || 'Failed to register');
        }

        alert('Registration successful! Please check your email to activate your account.');
        console.log('Activation Link:', responseData.activationLink);
    } catch (error) {
        console.error('Error occurred during registration:', error);
        alert(error.message);
    }
});
