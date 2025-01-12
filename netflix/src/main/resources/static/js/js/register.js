document.getElementById('registerForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const subscription = document.getElementById('subscription').value;
    const paymentMethod = document.getElementById('paymentMethod').value;

    try {
        const response = await fetch('http://localhost:8081/api/users/register', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password, subscription, paymentMethod }),
        });

        if (!response.ok) {
            const errorData = await response.json();
            console.error("Error response:", errorData);
            alert(`Error: ${errorData.error}`);
            return;
        }

        const data = await response.json();
        console.log("Registration successful:", data);
        alert(`Registration successful! Activation link: ${data.activationLink}`);
    } catch (error) {
        console.error("Error during registration:", error);
        alert("An unexpected error occurred. Please try again.");
    }
});
