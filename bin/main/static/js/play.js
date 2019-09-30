function playerPieceMove() {
    let isSelected = $(this).hasClass('selected');
    $('.player-piece').removeClass('selected');
    if (!isSelected) {
        $(this).addClass('selected');
    }
}

function playerPieceSwap() {
    if ($(this).hasClass('selected')) {
        $('.player-piece').removeClass('selected');
        return;
    }

    let selectedPiece = $('.selected');
    $('.piece').removeClass('selected');
    if (selectedPiece.length === 0) {
        $(this).addClass('selected');
    }
    else {
        let thisPiece = $(this);
        let thisX = thisPiece.offset().left;
        let thisY = thisPiece.offset().top;
        let selectedX = selectedPiece.offset().left;
        let selectedY = selectedPiece.offset().top;
        let thisParent = thisPiece.parent();
        let selectedParent = selectedPiece.parent();
        let thisAnimated = thisPiece.clone()
            .appendTo('#gameboard')
            .css('position', 'fixed')
            .css('left', thisX)
            .css('top', thisY)
            .animate({
                'left': selectedX,
                'top': selectedY
            },
                {
                    queue: false,
                    complete: function () {
                        thisPiece.appendTo(selectedParent);
                        thisPiece.show();
                        thisAnimated.remove();
                    }
                });
        let selectedAnimated = selectedPiece.clone()
            .appendTo('#gameboard')
            .css('position', 'fixed')
            .css('left', selectedX)
            .css('top', selectedY)
            .animate({
                'left': thisX,
                'top': thisY
            },
                {
                    queue: false,
                    complete: function () {
                        selectedPiece.appendTo(thisParent);
                        selectedPiece.show();
                        selectedAnimated.remove();
                    }
                });
        thisPiece.hide();
        selectedPiece.hide();
    }
}

function cellClickHandler() {
    let cell = this;
    if ($(cell).children().length > 0 && $(cell).children().first().hasClass('player-piece')) {
        return;
    }
    let selectedPiece = $('.selected');
    if (selectedPiece.length) {
        let fromXIndex = parseInt(selectedPiece.parent().attr('data-col'));
        let fromYIndex = parseInt(selectedPiece.parent().attr('data-row'));
        let toXIndex = parseInt(cell.getAttribute('data-col'));
        let toYIndex = parseInt(cell.getAttribute('data-row'));
        $('.cell').off('click');
        $('.player-piece').off('click');
        $.ajax({
            url: '/api/games/move',
            type: 'POST',
            contentType: 'application/json',
            success: receiveMove,
            data: JSON.stringify({
                'gameId': window.gameId,
                'from': [fromXIndex, fromYIndex],
                'to': [toXIndex, toYIndex]
            }),
            error: function () {
                $('.player-piece').on('click', playerPieceMove);
                $('.cell').on('click', cellClickHandler);
                // TODO: toast player with error message
            }
        });
    }
    else {
        //To Do: Toast user with message "Please Select a Piece"
    }
}

function receiveMove(data, textStatus, xhr) {
    $('.selected').removeClass('selected');
    data = JSON.parse(data);

    animatePiece(data.moves[0], function(){
        animatePiece(data.moves[1], function(){
            $('.player-piece').on('click', playerPieceMove);
            $('.cell').on('click', cellClickHandler);
        });
    });
}

// used in library function animatePiece()
function appendBanner(move){
    $('#main').append(
        $('<div>').attr('id', 'result-banner')
            .append($('<span>').text((move.actor === 'PLAYER')? 'VICTORY':'DEFEAT'))
            .append($('<a>').attr('id', 'first-a').attr('href', '/play').text('PLAY AGAIN'))
            .append($('<a>').attr('id', 'second-a').attr('href', '/game?' + window.gameId).text('WATCH GAME')));
}

function startButtonHandler() {
    $('.player-piece').off('click');
    let JQrows = [$('.G'), $('.H'), $('.I'), $('.J')];
    let postObject = [new Array(10), new Array(10), new Array(10), new Array(10)];
    for (let i = 0; i < 4; i++) {
        for (let j = 0; j < 10; j++) {
            postObject[i][j] = pieceValueToInt($(JQrows[i][j]).children().first().text());
        }
    }
    $.ajax({
        url: '/api/games/startGame',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(postObject),
        success: function (data) {
            window.gameId = data;
            $('.player-piece').on('click', playerPieceMove);
            $('.cell').on('click', cellClickHandler);
            $('#start-btn').remove();
        },
        error: function () {
            // TODO: toast player with error message
        }
    });
}

$(function () {
    $('#start-btn').on('click', startButtonHandler);
    $('.player-piece').on('click', playerPieceSwap);
});