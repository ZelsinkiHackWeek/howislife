var favMovies = new Firebase('https://question-of-the-day-96172.firebaseio.com/questions');

function refreshUI(questionText, oneStar, twoStars, threeStars, fourStars) {


    //Update pie chart:
    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);
    function drawChart() {

    var data = google.visualization.arrayToDataTable([
      ['Mood', 'Percent'],
      ['Super happy', fourStars],
      ['Happy', threeStars],
      ['Meh', twoStars],
      ['Sad', oneStar]
    ]);

    //Update title:
    document.getElementById("question_title").innerHTML = questionText;
    document.getElementById("total_votes").innerHTML = "Total votes: " + (oneStar + twoStars + threeStars + fourStars);

    var options = {
      title: questionText,
      pieHole: 0.3,
      slices: {
                  0: { color: '#02e227' },
                  1: { color: '#d7e202' },
                  2: { color: '#ff9400' },
                  3: { color: '#ff0000' }
                }
    };

    var chart = new google.visualization.PieChart(document.getElementById('piechart'));

    chart.draw(data, options);
    }
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

    var biggestId = Number.MIN_VALUE;
    for (var key in data) {
        if (key < biggestId) {
            continue;
        } else {
            biggestId = key;
        }
        if (data.hasOwnProperty(key)) {
            text = data[key].text ? data[key].text : '';
            oneStar = data[key].stars[0]
            twoStars = data[key].stars[1]
            threeStars = data[key].stars[2]
            fourStars = data[key].stars[3]
        }
    }
    refreshUI(text, oneStar, twoStars, threeStars, fourStars);
});
