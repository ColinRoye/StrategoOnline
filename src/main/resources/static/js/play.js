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
    if (selectedPiece.length === 0){
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
            .css('top',  thisY)
            .animate({
                'left': selectedX,
                'top': selectedY
            }, 
            {
                queue: false,
                complete : function() {
                    thisPiece.appendTo(selectedParent);
                    thisPiece.show();
                    thisAnimated.remove();
                }
            });
        let selectedAnimated = selectedPiece.clone()
            .appendTo('#gameboard')
            .css('position', 'fixed')
            .css('left', selectedX)
            .css('top',  selectedY)
            .animate({
                'left': thisX,
                'top': thisY
            }, 
            {
                queue: false,
                complete: function() {
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
    if ($(cell).children().length > 0 && $(cell).children().first().hasClass('player-piece')){
        return;
    }
    let selectedPiece = $('.selected');
    if (selectedPiece.length){
        let fromXIndex = parseInt(selectedPiece.parent().attr('data-col'));
        let fromYIndex = parseInt(selectedPiece.parent().attr('data-row'));
        let toXIndex   = parseInt(cell.getAttribute('data-col'));
        let toYIndex   = parseInt(cell.getAttribute('data-row'));
        $.ajax({
            url: '/api/games/move',
            type: 'POST',
            contentType: 'application/json',
            success: receiveMove,
            data: JSON.stringify({
                'game_id' : window.gameID,
                'from' : [fromXIndex, fromYIndex],
                'to' : [toXIndex, toYIndex]
            }),
            error: function() {
                // TODO: toast player with error message
            }
        });
    }
    else {
        //To Do: Toast user with message "Please Select a Piece"
    }
}

const indexToLetter = ['A','B','C','D','E','F','G','H','I','J'];

function indecesToJQ(indeces) {
    return $('#' + indexToLetter[indeces[1]] + indeces[0]);
}

function receiveMove(data, textStatus, xhr) {
    $('.selected').removeClass('selected');
    animatePiece(data.player_move, animatePiece, data.ai_move);
}

function test(optionA, optionB){
    optionA('A');
    if (optionB)
        optionB('B');
}

function animatePiece(move, callback, callbackArg){
    let piece = indecesToJQ(move.from).children().first();
    let animatedPiece = piece.clone()
        .appendTo('#gameboard')
        .css('position', 'fixed')
        .css('left', piece.offset().left)
        .css('top', piece.offset().top);
    if (move.result === "MOVED"){
        piece.appendTo(indecesToJQ(move.to));
        animatedPiece.animate({
            'left': piece.offset().left,
            'top': piece.offset().top
        },
        function() {
            piece.show();
            animatedPiece.remove();
            setTimeout(function() {
                if (callback && callbackArg){
                    callback(callbackArg);
                }
            });
        });
        piece.hide()
    }
    else {
        let x = piece.offset().left;
        let y = piece.offset().top;
        piece.hide();
        let defendingPiece = indecesToJQ(move.to).children().first();
        let destinationX = defendingPiece.offset().left;
        let destinationY = defendingPiece.offset().top;
        var interumX;
        var interumY;
        if (x < destinationX) { //Attacking from the left
            interumX = destinationX - animatedPiece.width();
            interumY = destinationY;
        }
        else if (x > destinationX) { //Attacking from the right
            interumX = destinationX + defendingPiece.width();
            interumY = destinationY;
        }
        else if (y < destinationY) { //Attacking from above
            interumX = destinationX;
            interumY = destinationY - animatedPiece.height();
        }
        else if (y > destinationY) { //Attacking from below
            interumX = destinationX;
            interumY = destinationY + defendingPiece.height();
        }
        animatedPiece.animate({
            'left': interumX,
            'top': interumY
        }, function() {
            piece.text(move.subject);
            animatedPiece.text(move.subject);
            defendingPiece.text(move.target);
            setTimeout(function() {
                if (move.result === 'WON'){
                    x = defendingPiece.offset().left;
                    y = defendingPiece.offset().top;
                    defendingPiece.animate({
                        'left': defendingPiece.offset().left + defendingPiece.width() / 2,
                        'top': defendingPiece.offset().top + defendingPiece.height() / 2,
                        'width' : 0, 
                        'height' : 0
                    }, function() {
                        animatedPiece.animate({
                            'left': x,
                            'top': y
                        }, function() {
                            piece.show();
                            animatedPiece.remove();
                            setTimeout(function() {
                                if (callback && callbackArg){
                                    callback(callbackArg);
                                }
                            });
                        });
                        defendingPiece.remove();
                        piece.appendTo(indecesToJQ(move.to));
                    });
                }
                if (move.result === 'LOST') {
                    animatedPiece.animate({
                        'left' : animatedPiece.offset().left + animatedPiece.width() / 2,
                        'top' : animatedPiece.offset().top + animatedPiece.height() / 2,
                        'width' : 0, 
                        'height' : 0
                    }, function() {
                        animatedPiece.remove();
                        piece.remove();
                        if (move.target === 'B') {
                            defendingPiece.remove();
                        }
                        setTimeout(function() {
                            if (callback && callbackArg){
                                callback(callbackArg);
                            }
                        });
                    });
                }
            }, 1000);
        });
    }
}

function pieceValueToInt(value){
    switch (value){
        case 'F':
            return 0;
        case 'S':
            return 1;
        case 'B':
            return 11;
        default:
            return parseInt(value);
    }
}

function startButtonHandler() {
    $('.player-piece').off('click');
    let JQrows = [ $('.G'), $('.H'), $('.I'), $('.J') ];
    let postObject = [new Array(10), new Array(10), new Array(10), new Array(10)];
    for (let i=0; i<4; i++){
        for (let j=0; j<10; j++){
            postObject[i][j] = pieceValueToInt($(JQrows[i][j]).children().first().text());
        }
    }
    $.ajax({
        url: '/api/games/startGame',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(postObject),
        success: function(data) {
            window.gameID = data.game_id;
            $('.player-piece').on('click', playerPieceMove);
            $('.cell').on('click', cellClickHandler);
            $('#start-btn').remove();
        },
        error: function() {
            // TODO: toast player with error message
        }
    });
    console.log(postObject);
}

$(function() {
    $('#start-btn').on('click', startButtonHandler);
    $('.player-piece').on('click', playerPieceSwap);
});