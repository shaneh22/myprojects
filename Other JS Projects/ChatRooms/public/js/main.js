const chatForm = document.getElementById('chat-form');
const chatMessages = document.querySelector('.chat-messages');
const roomName = document.getElementById('room-name');
const userList = document.getElementById('users');

// Get username and room from URL
const { username, room } = Qs.parse(location.search, {
  ignoreQueryPrefix: true
});

const socket = io();

// Join chatroom
socket.emit('joinRoom', { username, room });

// Get room and users
socket.on('roomUsers', ({ room, users }) => {
  outputRoomName(room);
  outputUsers(users);
});

// Message from server
socket.on('message', message => {
  outputMessage(message);
  // Scroll down
  chatMessages.scrollTop = chatMessages.scrollHeight;
});

//server annoucement
socket.on('annoucement', annoucement =>{
  outputAnnoucement(annoucement);
  // Scroll down
  chatMessages.scrollTop = chatMessages.scrollHeight;
});

// Message submit
chatForm.addEventListener('submit', e => {
  e.preventDefault();

  // Get message text
  let chatMessage = e.target.elements.msg.value;
  
  chatMessage = chatMessage.trim();
  
  if (!chatMessage){
    return false;
  }

  // Emit message to server
  socket.emit('chatMessage', chatMessage);

  // Clear input
  e.target.elements.msg.value = '';
  e.target.elements.msg.focus();
});

// Output message to DOM
function outputMessage(message) {
  const div = document.createElement('div');
  div.classList.add('message');
  const p = document.createElement('p');
  p.classList.add('meta');
  p.innerText = message.username;
  div.appendChild(p);
  const messageText = document.createElement('p');
  messageText.classList.add('text');
  messageText.innerText = message.text;
  div.appendChild(messageText);
  document.querySelector('.chat-messages').appendChild(div);
}

// Output annoucement to DOM
function outputAnnoucement(annoucement) {
  const div = document.createElement('div');
  div.classList.add('message');
  const messageText = document.createElement('p');
  messageText.classList.add('text');
  messageText.innerText = annoucement.text;
  div.appendChild(messageText);
  document.querySelector('.chat-messages').appendChild(div);
}

// Add room name to DOM
function outputRoomName(room) {
  roomName.innerText = room;
}

// Add users to DOM
function outputUsers(users) {
  userList.innerHTML = '';
  users.forEach(user=>{
    const usernameBullet = document.createElement('li');
    usernameBullet.innerText = user.username;
    userList.appendChild(usernameBullet);
  });
 }
