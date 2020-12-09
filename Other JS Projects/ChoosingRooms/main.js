const socket = io();
const buttons = document.getElementsByClassName('button');

Array.prototype.map.call(buttons, (button)=>{
    button.addEventListener('click', e=>{
        e.preventDefault();
        let roomID = e.target.dataset.buttonId;
        socket.emit('roomEntered', roomID);
    });
});

socket.on('roomEntered', roomID => {
    let roomCount = document.getElementById(roomID);
    let num = parseInt(roomCount.innerText);
    num += 1;
    roomCount.innerText = num;
});

socket.on('buttonClicked', () =>{
    const constantButtons = [];
    Array.prototype.map.call(buttons, (button)=>{
        constantButtons.push(button);
    });
    constantButtons.map((button) =>{
        button.remove();
    });
});