document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault(); // Prevent form default submission

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const paymentMethod = document.getElementById('paymentMethod').value;
    const language = document.getElementById('language').value;
    const termsAccepted = document.getElementById('terms').checked;

    if (!termsAccepted) {
        alert('You must agree to the Terms and Conditions.');
        return;
    }

    const payload = {
        email,
        password,
        paymentMethod,
        language: parseInt(language),
    };

    try {
        const response = await fetch('http://localhost:8081/api/users/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        });

        // Debugging the raw response
        console.log('Raw response:', response);

        // Handle non-OK responses
        if (!response.ok) {
            const errorText = await response.text();
            console.error('Error response text:', errorText);
            alert(`Registration failed: ${errorText}`);
            return;
        }

        // Parse and handle JSON response
        const data = await response.json();
        console.log('Registration successful:', data);

        // Show activation link or success message
        alert('Registration successful! Check your email for the activation link.');
        console.log('Activation Link:', data.activationLink);
    } catch (error) {
        console.error('Error occurred during registration:', error);
        alert('Something went wrong. Please try again.');
    }
});
