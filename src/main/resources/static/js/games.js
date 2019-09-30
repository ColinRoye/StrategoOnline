var rowTemplate;

$(function() {
    console.log('init');
    $.get('/api/games/', fillTable);
});

function fillTable(data) {
    var games = JSON.parse(data);
    console.log(data);
    for (var i=0; i<games.length; i++) {
        $.ajax({
            url:'/api/games/get/' + games[i],
            type: 'GET',
            dataType: 'json',
            success: function(data){
                if (data == null){
                    $('#games-info').text("Error getting data from server. Please try again later");
                }
                appendRow(data.gameId, data.winner);
            }
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