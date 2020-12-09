export default class Game {
    constructor(size) {
        this.size = size;
        this.state = Game.State.IN_PROGRESS;
        this.listeners = [];
        this.gameBoard = [];
        this.score = 0;

        for(let row = 0; row < size; row++){
            let cellrow = [];
            for(let col = 0; col < size; col++){
                cellrow.push(new GameCell(this, row, col));
            }
            this.gameBoard.push(cellrow);
        }

        this.addNewCell();
        this.addNewCell();

        this.start();
    }

    setupNewGame() {
        this.state = Game.State.INITIALIZED;
        this.gameBoard = [];
        this.score = 0;

        for(let row = 0; row < this.size; row++){
            let cellrow = [];
            for(let col = 0; col < this.size; col++){
                cellrow.push(new GameCell(this, row, col));
            }
            this.gameBoard.push(cellrow);
        }

        this.addNewCell();
        this.addNewCell();

        this.start();
    }

    start(){
        if(this.state != Game.State.INITIALIZED){
            return;
        }
        this.state = Game.State.IN_PROGRESS;
        this.updateListeners({
            game: this,
            event: Game.Event.START
        });
    }

    loadGame(gameState){
        if(gameState.board.length == this.size * this.size){
            this.forAllCells((cell)=>{
                cell.value = gameState.board.shift();
                if(cell.value == 0){
                    cell.state = GameCell.State.INACTIVE;
                }
                else {
                    cell.state = GameCell.State.ACTIVE;
                }
            });
        }
        else{
            console.log("Error: not able to load board.");
        }
        this.score = gameState.score;
        if(gameState.won){
            if(gameState.over){
                this.state = Game.State.WON_OVER;
                this.updateListeners({
                   game: this,
                   event: Game.Event.WIN 
                });
                this.updateListeners({
                    game: this,
                    event: Game.Event.LOSE 
                });
            }
            else{
                this.state = Game.State.WON;
                this.updateListeners({
                    game: this,
                    event: Game.Event.WIN 
                 });
            }
        }
        else if (gameState.over){
            this.state = Game.State.LOST;
            this.updateListeners({
                game: this,
                event: Game.Event.LOSE 
            });
        }
        else{
            this.state = Game.State.IN_PROGRESS;
            this.updateListeners({
                game: this,
                event: Game.Event.IN_PROGRESS 
            });
        }
    }

    move(direction){
        let savedGameBoard = this.getGameState();
        switch(direction){
            case 'right': 
                this.moveCellsRight();
                break;
            case 'left': 
                this.moveCellsLeft();
                break;
            case 'up':
                this.transposeGameBoard();
                this.moveCellsLeft();
                this.transposeGameBoard();
                break;
            case 'down':
                this.transposeGameBoard();
                this.moveCellsRight();
                this.transposeGameBoard();
        }
        this.forAllCells((cell)=>{
            if(cell.state == GameCell.State.COMBINED){
                cell.state = GameCell.State.ACTIVE;
            }
        });
        let newGameState = this.getGameState();
        let legalMove = false;
        savedGameBoard.board.reduce((acc, cell)=>{
            if(cell != newGameState.board[acc]){
                legalMove = true;
            }
            return acc += 1;
        },0);
        if(legalMove){
            this.addNewCell();
        }
    }

    forAllCells(func){
        this.gameBoard.forEach((row)=>{
            row.forEach((cell)=>{
                func(cell);
            });
        });
    }

    moveCellsRight(){
        this.gameBoard.map((row)=>{
            row.reverse();
            row.forEach((cell)=>{
                cell.col = (this.size - 1) - cell.col;
                cell.moveLeft();
            });
        });
        this.gameBoard.map((row)=>{
            row.reverse();
            row.forEach((cell)=>{
                cell.col = (this.size - 1) - cell.col;
            });
        });
    }

    moveCellsLeft(){
        this.gameBoard.forEach((row)=>{
            row.forEach((cell)=>{
                cell.moveLeft();
            })
        });
    }

    transposeGameBoard(){
        let transpose = this.gameBoard[0].map((_, col)=> {
            return this.gameBoard.map(row => {
                let temp = row[col].row;
                row[col].row = row[col].col;
                row[col].col = temp;
                return row[col];
            });
        });
        this.gameBoard = transpose;
    }

    onMove(callback){
        this.addListener((e)=>{
            if(e.event == Game.Event.MOVE){
                callback(this.getGameState());
            }
        });
    }

    onWin(callback){
        this.addListener((e)=>{
            if(e.event == Game.Event.WIN){
                callback(this.getGameState());
            }
        });
    }

    onLose(callback){
        this.addListener((e)=>{
            if(e.event == Game.Event.LOSE){
                callback(this.getGameState());
            }
        });
    }

    addNewCell(){
        let inactiveTiles = [];
        this.forAllCells((cell)=>{
            if(cell.state == GameCell.State.INACTIVE){
                inactiveTiles.push(cell);
            }
        });
        let randomIndex = Math.floor(Math.random() * inactiveTiles.length);
        inactiveTiles[randomIndex].activate();
        if(this.state != Game.State.INITIALIZED){
            this.updateListeners({
                game: this, 
                event: Game.Event.MOVE
            });
        }
        if(inactiveTiles.length - 1 == 0){
            this.checkIfGameOver();
        }
    }

    checkIfGameOver(){
        let hasValidMove = false;
        this.forAllCells((cell)=>{
            if(cell.hasValidMove()){
                hasValidMove = true;
            }
        });
        if(!hasValidMove){
            this.lose();
        }
    }

    checkIfWin(points){
        if(points == 2048){
            this.win();
        }
    }

    win(){
        if(this.state != Game.State.IN_PROGRESS){
            return;
        }
        this.state = Game.State.WON;
        this.updateListeners({
            game: this,
            event: Game.Event.WIN 
        });
    }

    lose(){
        if(this.state != Game.State.IN_PROGRESS){
            if(this.state == Game.State.WON){
                this.state = Game.State.WON_OVER;
                this.updateListeners({
                    game: this,
                    event: Game.Event.LOSE 
                });
            }
            return;
        }
        this.state = Game.State.LOST;
        this.updateListeners({
            game: this,
            event: Game.Event.LOSE 
        });
    }

    toString(){
        let s = "";
        this.gameBoard.map((row)=>{
            s+= "  ";
            row.map((cell)=>{
                if(cell.state == GameCell.State.INACTIVE){
                    s += "[ ]";
                }
                else {
                    s += `[${cell.value}]`;
                }
                s+= " ";
            });
            s+="\n";
        });
        return s;
    }

    getGameState(){
        let gameState = {
            "board": [], 
            "score": this.score, 
            "won": (this.state == Game.State.WON || this.state == Game.State.WON_OVER) ? true : false, 
            "over": (this.state == Game.State.LOST || this.state == Game.State.WON_OVER) ? true : false
        };
        this.forAllCells((cell)=>{
            gameState.board.push(cell.value);
        });
        return gameState;
    }

    addListener(listener) {
        let idx = this.listeners.findIndex((l) => l == listener);
        if (idx == -1) {
            this.listeners.push(listener);
        }
    }

    removeListener(listener) {
        let idx = this.listeners.findIndex((l) => l == listener);
        if (idx != -1) {
            this.listeners.splice(idx, 1);
        }
    }

    updateListeners(event) {
        this.listeners.forEach((l) => l(event));
    }
}

Game.State = {
    INITIALIZED: 0,
    IN_PROGRESS: 1,
    WON:         2,
    LOST:        3,
    WON_OVER:    4
};

Game.Event = {
    START: 0,
    MOVE:  1,
    WIN:   2,
    LOSE:  3,
};

let GameCell = class {
    constructor(model, row, col){
        this.model = model;
        this.row = row;
        this.col = col;
        this.value = 0;
        this.state = GameCell.State.INACTIVE;
    }

    leftNeighbor(){
        if(this.col > 0){
            return this.model.gameBoard[this.row][this.col - 1];
        }
        else{
            return null;
        }
    }

    moveLeft(){
        let leftNeighbor = this.leftNeighbor();
        if(leftNeighbor == null || this.state == GameCell.State.INACTIVE){
            return;
        }
        if(leftNeighbor.state == GameCell.State.INACTIVE){
            let tempCol = this.col;
            this.col = leftNeighbor.col;
            leftNeighbor.col = tempCol;
            this.model.gameBoard[this.row][this.col] = this;
            this.model.gameBoard[this.row][leftNeighbor.col] = leftNeighbor;
            this.moveLeft();
            return;
        }
        if(this.state == GameCell.State.COMBINED || leftNeighbor.state == GameCell.State.COMBINED || leftNeighbor.value != this.value){
            return;
        }
        else if(this.value == leftNeighbor.value){
            leftNeighbor.value += this.value;
            this.model.score += leftNeighbor.value;
            this.model.checkIfWin(leftNeighbor.value);
            leftNeighbor.state = GameCell.State.COMBINED;
            this.resetCell();
        }
    }

    resetCell() {
        this.value = 0;
        this.state = GameCell.State.INACTIVE;
    }
 
    activate() {
        this.state = GameCell.State.ACTIVE;
        let random = Math.random();
        if(random >= .9){
            this.value = 4;
        }
        else {
            this.value = 2;
        }
    }

    hasValidMove() {
        let board = this.model.gameBoard;
        if(this.row > 0){
            if(board[this.row-1][this.col].value == this.value){
                return true;
            }
        }
        if(this.row < this.model.size - 1){
            if(board[this.row+1][this.col].value == this.value){
                return true;
            }
        }
        if(this.col > 0){
            if(board[this.row][this.col-1].value == this.value){
                return true;
            }
        }
        if(this.col < this.model.size - 1){
            if(board[this.row][this.col+1].value == this.value){
                return true;
            }
        }
        return false;
    }
}

GameCell.State = {
    ACTIVE: 0,
    INACTIVE: 1,
    COMBINED: 2
}