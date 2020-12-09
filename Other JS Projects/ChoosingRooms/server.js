const path = require('path');
const http = require('http');
const express = require('express');
const socketio = require('socket.io');

const app = express();
const server = http.createServer(app);
const io = socketio(server);

app.use(express.static(path.join(__dirname)));

io.on('connection', socket => {
    socket.on('roomEntered', roomID =>{
        io.emit('roomEntered', roomID);
        socket.emit('buttonClicked');
    });
});

const PORT = 3000;

server.listen(PORT, () => console.log("Running on port 3000..."));