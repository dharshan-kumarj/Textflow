const socket = new WebSocket('ws://localhost:8080/websocket/editor');

socket.onopen = () => {
    console.log('Connected to server');
};

socket.onmessage = (event) => {
    // Update editor content with received changes
    document.getElementById('editor').value = event.data;
};

// Send changes to server when user types
document.getElementById('editor').addEventListener('input', (e) => {
    socket.send(e.target.value);
});