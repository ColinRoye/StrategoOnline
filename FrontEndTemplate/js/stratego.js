
function initializePieces() {
    let aiPiece = $('.piece');
    $('.A').append(aiPiece.clone());
    $('.B').append(aiPiece.clone());
    $('.C').append(aiPiece.clone());
}

function pieceClickHandler() {
    let isSelected = $(this).hasClass('selected');
    $('.piece').removeClass('selected');
    if (!isSelected) {
        $(this).addClass('selected');
    }
}

function cellClickHandler() {
    let selectedPiece = $('.selected');
    if ($(this).is(':empty') && selectedPiece.length) {
        let parent = selectedPiece.parent();
        selectedPiece.removeClass('selected');
        let x = selectedPiece.offset().left;
        let y = selectedPiece.offset().top;
        let animatedPiece = selectedPiece.clone()
            .appendTo('#gameboard')
            .css('position', 'fixed')
            .css('left', x)
            .css('top', y);
        selectedPiece.appendTo(this);
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
        parent.children().remove();
    }
}

$(function(){
    initializePieces();
    $('.piece').on('click', pieceClickHandler);
    $('.cell').on('click', cellClickHandler);
});