function playerPieceClickHandler() {
    let isSelected = $(this).hasClass('selected');
    $('.piece').removeClass('selected');
    if (!isSelected) {
        $(this).addClass('selected');
    }
}

function cellClickHandler() {
    let cell = this;
    let selectedPiece = $('.selected');
    if (selectedPiece.length){
        //To Do: send post request with the parent of selectedPiece (it's current cell) 
        //       and cell (the destination cell)
        let fromXIndex = parseInt(selectedPiece.parent().attr('data-col'));
        let fromYIndex = parseInt(selectedPiece.parent().attr('data-row'));
        let toXIndex   = parseInt(cell.attr('data-col'));
        let toYIndex   = parseInt(cell.attr('data-row'));
        $.post('/move', {
            from: [fromXIndex, fromYIndex],
            to: [toXIndex, toYIndex]
        }, receiveMove);

    }
    else {
        //To Do: Toast user with message "Please Select a Piece"
    }
}

const indexToLetter = ['A','B','C','D','E','F','G','H','I','J']

function indecesToJQ(indeces) {
    return $('#' + indexToLetter[indeces[1]] + indeces[0]);
}

function receiveMove(data, textStatus, xhr) {
    if (true){ // TODO Change to verify if we moved
        $('.selected').removeClass('selected');
        animateMove(data.player_move, animateMove, data.ai_move);
    }
    else {
        //ToDo Toast user with message "Invalid Move"
    }
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
        piece.hide();
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
    }
    else {
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
        else if (y > distinationY) { //Attacking from below
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
            setTimeout(1000, function() {
                if (move.result === 'WON'){
                    defendingPiece.animate({
                        'width' : 0, 
                        'height' : 0
                    }, function() {
                        defendingPiece.remove();
                        piece.appendTo();
                        piece.show();
                        animatedPiece.animate({
                            'left': piece.offset().left,
                            'top': piece.offset().top
                        }, function() {
                            piece.show();
                            animatedPiece.remove();
                            setTimeout(function() {
                                if (callback && callbackArg){
                                    callback(callbackArg);
                                }
                            });
                        });
                        piece.hide();
                    });
                }
                if (move.result === 'LOST') {
                    animatedPiece.animate({
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
            });
        });
    }
}

$(function() {
    $('.player-piece').on('click', playerPieceClickHandler);
    $('.cell').on('click', cellClickHandler);
});