let Controller = class {
    constructor(game){
        this.game = game;
        document.addEventListener("keydown", (e) =>{
            let keyPressed = e.key;
            switch (keyPressed) {
                case 'ArrowRight':
                    this.game.move('right');
                    break;
                case 'ArrowLeft':
                    this.game.move('left');
                    break;
                case 'ArrowDown':
                    this.game.move('down');
                    break;
                case 'ArrowUp':
                    this.game.move('up');
                    break;
            }
        });
    }
}