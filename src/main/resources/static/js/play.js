function playerPieceClickHandler() {
    let isSelected = $(this).hasClass('selected');
    $('.piece').removeClass('selected');
    if (!isSelected) {
        $(this).addClass('selected');
    }
}

class PlayerMove {
    constructor(valid, battle = false, defendingPieceValue = '0', playerLost, defenderLost){
        this.valid = valid;
        this.battle = battle;
        this.defendingPieceValue = defendingPieceValue;
        this.playerLost = playerLost;
        this.defenderLost = defenderLost;
    }
}

function cellClickHandler() {
    let cell = this;
    let selectedPiece = $('.selected');
    if (selectedPiece.length){
        //To Do: send post request with the parent of selectedPiece (it's current cell) 
        //       and cell (the destination cell)
        var move = new PlayerMove(true, false, '0', false, false);
    }
    else {
        //To Do: Toast user with message "Please Select a Piece"
        return;
    }
    if (move.valid){
        selectedPiece.removeClass('selected');
        let x = selectedPiece.offset().left;
        let y = selectedPiece.offset().top;
        let animatedPiece = selectedPiece.clone()
            .appendTo('#gameboard')
            .css('position', 'fixed')
            .css('left', x)
            .css('top', y);
        if (move.battle){
            selectedPiece.hide();
            let defendingPiece = $(cell).children().first();
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
                defendingPiece.text(move.defendingPieceValue);
                setTimeout(1000, function() {
                    setTimeout(400, function() {
                        animatedPiece.remove();
                        if (move.defenderLost) {
                            defendingPiece.remove();
                        }
                        if (move.playerLost){
                            selectedPiece.remove();
                        }
                        else {
                            selectedPiece.appendTo(cell);
                            selectedPiece.show();
                        }
                    });
                    if (move.defenderLost){
                        defendingPiece.animate({
                            'width' : 0, 
                            'height' : 0
                        },{queue: false});
                    }
                    if (move.playerLost) {
                        animatedPiece.animate({
                            'width' : 0, 
                            'height' : 0
                        },{queue: false});
                    }
                });
            });
        }
        else {
            selectedPiece.appendTo(cell);
            x = selectedPiece.offset().left;
            y = selectedPiece.offset().top;
            selectedPiece.hide();
            animatedPiece.animate({
                'left': x,
                'top': y
            },
            function() {
                selectedPiece.show();
                animatedPiece.remove();
            });
        }
    }
    else {
        //ToDo Toast user with message "Invalid Move"
    }
}

$(function() {
    $('.player-piece').on('click', playerPieceClickHandler);
    $('.cell').on('click', cellClickHandler);
});