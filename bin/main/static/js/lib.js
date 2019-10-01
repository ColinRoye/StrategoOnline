const indexToLetter = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'];

function indecesToJQ(indeces) {
    return $('#' + indexToLetter[indeces[0]] + indeces[1]);
}

function pieceIntToValue(int) {
    if (int == 0){
        return 'F';
    }
    else if (int == 1){
        return 'S';
    }
    else if (int == 11){
        return 'B';
    }
    else {
        return '' + int;
    }
}

function pieceValueToInt(value) {
    switch (value) {
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

var explosion = $('<img>').attr('src', '/img/explosion.png').css('position', 'fixed');

function animatePiece(move, callback) {
    let piece = indecesToJQ(move.from).children().first();
    let animatedPiece = piece.clone()
        .appendTo('#gameboard')
        .css('position', 'fixed')
        .css('left', piece.offset().left)
        .css('top', piece.offset().top);
    if (move.result === "MOVED") {
        piece.appendTo(indecesToJQ(move.to));
        animatedPiece.animate({
            'left': piece.offset().left,
            'top': piece.offset().top
        },
            function () {
                piece.show();
                animatedPiece.remove();
                setTimeout(function () {
                    if (callback) {
                        callback();
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
        }, function () {
            piece.text(move.subject);
            animatedPiece.text(pieceIntToValue(move.subject));
            defendingPiece.text(pieceIntToValue(move.target));
            setTimeout(function () {
                if (move.result === 'WON') {
                    x = defendingPiece.offset().left;
                    y = defendingPiece.offset().top;
                    defendingPiece.animate({
                        'left': x + defendingPiece.width() / 2,
                        'top': y + defendingPiece.height() / 2,
                        'width': 0,
                        'height': 0
                    }, function () {
                        animatedPiece.animate({
                            'left': x,
                            'top': y
                        }, function () {
                            piece.show();
                            animatedPiece.remove();
                            setTimeout(function () {
                                if (move.target == 0){
                                    $('.player-piece').off('click');
                                    $('.cell').off('click');
                                    appendBanner(move);
                                    return;
                                }
                                if (callback) {
                                    callback();
                                }
                            });
                        });
                        defendingPiece.remove();
                        piece.appendTo(indecesToJQ(move.to));
                    });
                }
                else if (move.result === 'LOST') {
                    if (move.target == 11) {
                        explosion.css({
                            'width': defendingPiece.width(),
                            'height' : defendingPiece.height()
                        });
                        defendingPiece.parent().append(explosion);
                    }
                    animatedPiece.animate({
                        'left': animatedPiece.offset().left + animatedPiece.width() / 2,
                        'top': animatedPiece.offset().top + animatedPiece.height() / 2,
                        'width': 0,
                        'height': 0
                    }, function () {
                        if (move.target == 11){
                            explosion.remove();
                        }
                        animatedPiece.remove();
                        piece.remove();
                        setTimeout(function () {
                            if (callback) {
                                callback();
                            }
                        });
                    });
                }
                else if (move.result === "DRAW"){
                    animatedPiece.animate({
                        'left': animatedPiece.offset().left + animatedPiece.width() / 2,
                        'top': animatedPiece.offset().top + animatedPiece.height() / 2,
                        'width': 0,
                        'height': 0
                    }, {
                        queue: false,
                        complete: function() {
                            animatedPiece.remove();
                            piece.remove();
                            setTimeout(function () {
                                if (callback) {
                                    callback();
                                }
                            });
                        }
                    });
                    defendingPiece.animate({
                        'left': defendingPiece.offset().left + defendingPiece.width() / 2,
                        'top': defendingPiece.offset().top + defendingPiece.height() / 2,
                        'width': 0,
                        'height': 0
                    }, {
                        queue: false,
                        complete: function() {
                            defendingPiece.remove();
                        }
                    });
                }
                else if (callback) {
                    callback();
                }
            }, 1000);
        });
    }
}