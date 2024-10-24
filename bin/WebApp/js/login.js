document.getElementById('loginForm').addEventListener('submit', function (event) {
    event.preventDefault(); // Prevent the form from submitting the default way

    // Get the username and password from the form
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Create the request body
    const requestBody = `username=${encodeURIComponent(username)}&password=${encodeURIComponent(password)}`;

    // Send the login request to the server
    fetch('/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: requestBody,
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById('message').innerText = data; // Show the server's response
    })
    .catch(error => {
        console.error('Error:', error);
        document.getElementById('message').innerText = 'An error occurred while processing your request.';
    });
});
