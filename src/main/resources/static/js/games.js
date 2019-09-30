var rowTemplate;

$(function() {
    console.log('init');
    $.get('/api/games/', fillTable);
});

function fillTable(data) {
    var games = JSON.parse(data);
    console.log(data);
    for (var i=0; i<games.length; i++) {
        let row = $('<tr>').prependTo($('#games-info'))
        $.ajax({
            url:'/api/games/get/' + games[i],
            type: 'GET',
            dataType: 'json',
            success: function(data){
                if (data == null){
                    $('#games-info').text("Error getting data from server. Please try again later");
                }
                fillRow(row, data.gameId, data.winner);
            }
        });
    }
}

function fillRow(row, gameID, winner) {
    row.append(
        $('<td>').text(gameID))
    .append(
        $('<td>').text(winner == 'PLAYER'? 'VICTORY':'DEFEAT').addClass(winner === 'PLAYER' ? 'blue' : 'red'))
    .append(
        $('<td>').append(
            $('<a>').attr('href', '/game?' + gameID).text('WATCH')));
}