import Game from "./game.js";

let model = null;
let controller = null;
let view = null;

$(document).ready(() => {
    model = new Game(4);
    view = new View(model);
    controller = new Controller(model);

    $('body').empty().append(view.div);
});