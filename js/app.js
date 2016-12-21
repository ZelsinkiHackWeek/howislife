var favMovies = new Firebase('https://question-of-the-day-96172.firebaseio.com/questions');

function refreshUI(questionText, oneStar, twoStars, threeStars, fourStars) {
    var lis ='';
//    var lis = '';
//    for (var i = 0; i < list.length; i++) {
//        lis += '<li data-key="' + list[i].key + '">' + list[i].name + ' [' + genLinks(list[i].key, list[i].name) + ']</li>';
//    };
//    document.getElementById('favMovies').innerHTML = lis;
};

// this will get fired on inital load as well as when ever there is a change in the data
favMovies.on("value", function(snapshot) {

    var data = snapshot.val();
    var list = [];
    var text;
    var oneStar;
    var twoStars;
    var threeStars;
    var fourStars;
    for (var key in data) {
        if (data.hasOwnProperty(key)) {
            text = data[key].text ? data[key].text : '';
            oneStar = data[key].stars[0]
            twoStars = data[key].stars[1]
            threeStars = data[key].stars[2]
            fourStars = data[key].stars[3]
        }
    }
    refreshUI(text, oneStar, twoStars, threeStars);
});
