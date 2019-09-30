
var gameID;

$(function() {
    gameID = window.location.search.substring(1);
    $.get('/api/games/get/' + gameID, startAnimation);
});

function startAnimation(data) {
    let game = JSON.parse(data);
    console.log(game);
    initializeBoard(game.board.initial_board_pieces);
    animateMove(game, 0);
}

function initializeBoard(board){
    for (var i=0; i<board.length; i++) {
        for (var j=0; j<board[i].length; j++) {
            indecesToJQ([i, j]).append(createPiece(board[i][j]));
        }
    }
}

function createPiece(backendPieceRep) {
    if (backendPieceRep == null || backendPieceRep == undefined){
        return;
    }
    let piece = $("<div>");
    piece.addClass('piece');
    if (backendPieceRep.is_user){
        piece.addClass('player-piece');
        piece.addClass('blue-team');
        piece.text(pieceIntToValue(backendPieceRep.value));
    }
    else {
        piece.addClass('red-team');
    }
    return piece;
}

function animateMove(game, moveIndex) {
    if (moveIndex < game.moves.length){
        setTimeout(function() {
            animatePiece(game.moves[moveIndex], function(){
                animateMove(game, moveIndex+1);
            })
        });
    }
    else{
        appendBanner(game.winner);
    }
}

function appendBanner(playerWon){
    if (document.getElementById('result-banner')){
        return;
    }
    $('#main').append(
        $('<div>').attr('id', 'result-banner')
            .append($('<span>').text(playerWon? 'VICTORY':'DEFEAT'))
            .append($('<a>').attr('id', 'fitst-a').attr('href', '/game?' + gameID).text('WATCH AGAIN'))
            .append($('<a>').attr('id', 'second-a').attr('href', '/games').text('WATCH ANOTHER')));
}