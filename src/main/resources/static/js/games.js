var rowTemplate;

$(function() {
    $.get('/api/games', fillTable);
});

function fillTable(games) {
    console.log(games);
    for (game in games) {
        $.get('/api/games/get/' + game, function(data){
            appendRow(game, data.winner);
        });
    }
}

function appendRow(gameID, winner) {
    $('#games-info').append(
        $('<tr>').append(
            $('<td>').text(gameID)
        )
        .append(
            $('<td>').text(winner === 'player' ? 'WIN' : 'LOSS').addClass(winner === 'player' ? 'blue' : 'red')
        )
        .append(
            $('<td>').append(
                $('<a>').attr('href', '/game?' + gameID).text('WATCH')
            )
        )
    );
}