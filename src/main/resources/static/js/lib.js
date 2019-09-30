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