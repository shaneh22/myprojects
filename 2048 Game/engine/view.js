let View = class {
    constructor(game){
        this.game = game;
        this.gameState = {};
        this.cellViews = [];
        this.div = $('<div></div>');
        this.wonOrLost = $('<div></div>');
        this.div.append(this.wonOrLost);

        this.div.append('<h2>Combine tiles by using the arrow keys to shift them all in that direction, merging two tiles of equal value into one. Try and get the 2048 tile to win!</h2>');

        this.div.append(`<h1 id = "score">Score = 0</h1>`);
        let button = $('<button><strong>New Game</strong></button>')
            .addClass("button")
            .on('click', (e)=>{
                e.preventDefault();
                this.game.setupNewGame();
            });
        this.div.append(button);

        let gameBoard = $('<div id = "gameBoard"></div>')
            .css('position','relative')
            .css('width',`${game.size * 100} px`)
            .css('height',`${game.size * 100} px`);

        this.game.gameBoard.forEach(row => {
            row.forEach((cell)=>{
                let cellView = new CellView(cell);
                this.cellViews.push(cellView);
                gameBoard.append(cellView.div);
            });
        });
        
        this.div.append(gameBoard);

        this.game.addListener((e)=>{
            if(e.event == View.Event.MOVE || e.event == View.Event.START){
                if(e.event == View.Event.START){
                    this.wonOrLost.empty();
                }
                this.gameState = this.game.getGameState();
                document.getElementById('score').innerHTML = `Score = ${this.gameState.score}`;
                for(let i = 0; i< this.cellViews.length; i++) {
                    this.cellViews[i].update(this.gameState.board[i]);
                }
            }
            else if(e.event == View.Event.WIN){
                this.wonOrLost.append('<h1 id = "won">YOU WON</h1>');
            }
            else if(e.event == View.Event.LOSE){
                this.wonOrLost.append('<h1 id = "lost">GAME OVER</h1>');
            }
        });
    }
}

View.Event = {
    START: 0,
    MOVE:  1,
    WIN:   2,
    LOSE:  3,
};

let CellView = class {
    constructor(gameCell){
        let left_px = (100* gameCell.col) + "px";
        let top_px = (100* gameCell.row) + "px";
        this.div = $('<div></div>').addClass("cellview").css('left',left_px).css('top', top_px);
        this.update(gameCell.value);
    }
    update(v){
        this.div.removeClass('inactive').removeClass('active');
        if(v == 0){
            this.div.empty().addClass('inactive').css('background',`lightblue`);;
        }
        else {
            let color = Math.log2(v) + 3;
            let red = '';
            let green = '00';
            let blue = '';
            if(color > 19){
                red = '45';
                blue = '45';
                green = '45';
            }
            else if(color > 10){
                color -= 10;
                red = '' + color + color;
                blue = '' + color + color;
                if(color < 2){
                    green = '9' + color;
                }
                else {
                    green = '' + color + color;
                }
            }
            else if(color == 10){
                red = 'F4';
                blue = '79';
                green = '66';
            }
            else if (color > 6){
                blue = '' + color + color;
                color-= 5;
                red = '' + color + color;
            }
            else {
                color += 2;
                red = ''+ color + color;
                blue = '0' + color;
            }
            this.div.empty().addClass('active').css('background',`#${red}${green}${blue}`);
            this.div.html(v).css('color','yellow').css('font-size','xx-large');
        }
    }
}