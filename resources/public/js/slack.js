(function() {
  var authToken = 'xoxp-9591936032-37041717591-37076895056-ddb0041d14';
  var slackData;
  var slackSocket;
  var moodItems = [];
  var userScores = {};
  var emojis = {};

  console.log('init slack stuff');
  getSlackData();
  getEmojiData();

  function getSlackData() {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function() {
        if (xmlHttp.readyState == 4 && xmlHttp.status == 200)
            initSlackConnection(xmlHttp.responseText);
    }
    xmlHttp.open('GET', 'https://slack.com/api/rtm.start?token=' + authToken, true);
    xmlHttp.send(null);
  }

  function initSlackConnection(response) {
    console.log('rtm start');
    slackData = JSON.parse(response);
    console.log(slackData);
    slackSocket = new WebSocket(slackData.url);
    slackSocket.onopen = initSocketEvents;
  }

  function initSocketEvents() {
    console.log('socket open, init events');
    slackSocket.onmessage = parseSlackMessage;
  }

  function parseSlackMessage(event) {
    data = JSON.parse(event.data);
    if ((data.type == 'message' || data.type == 'reaction_added') && !data.hidden && !data.upload) evalMessage(data);
  }

  function evalMessage(data) {
    console.log('read msg or reaction', data);
    if (data.type == 'message') {
      // parse message body for emojis
      console.log(data.text);
      var found = parseForEmojis(data.text);
      if (found) _.each(found, function(f) {
        var val = emojis[f.replace(/:/g, '')];
        if (val) createMoodItem(val, data.user, data.ts);
      });
    } else {
      // check reaction type
      console.log(data.reaction);
      var val = emojis[data.reaction];
      if (val) createMoodItem(val, data.user, data.ts);
    }
  }

  function createMoodItem(val, user, timestamp) {
    moodItems.push({
      'user': user,
      'value': val,
      'timestamp': timestamp
    });
    calculateUserScores();
  }

  function calculateUserScores() {
    console.log('calulating user scores', moodItems);
    var redraw = false;
    userScores = _.chain(moodItems)
    .groupBy('user')
    .mapKeys(getUsername)
    .mapValues(calculateScore)
    .value();
    console.log('user scores', userScores);
  }

  function calculateScore(values) {
    console.log('calculating score for', values);
    var sum = 0.0;
    _.each(values, function(v) { sum += v.value });
    return parseFloat(sum / values.length).toFixed(2);
  }

  function getUsername(v, id) {
    console.log('finding user by id', id);
    var user = _.find(slackData.users, {'id': id})
    return user ? user.name : 'Not found'
  }

  function parseForEmojis(text) {
    var TEST_REGEX = /\:([a-z0-9_+-]+)(?:\[((?:[^\]]|\][^:])*\]?)\])?\:/g
    return text.match(TEST_REGEX);
  }

  function getEmojiData() {
    emojis = {
      // Positive
      'simple_smile': 1.0,
      'wink': 1.0,
      'grin': 1.4,
      'grinning': 1.5,
      'joy': 2.0,
      'smiley': 1.5,
      'laughing': 1.7,
      'smile': 1.4,
      'blush': 1.2,
      // Negative or neutral
      'neutral_face': 0,
      'expressionless': 0,
      'pensive': -1.3,
      'rage': -2.0,
      'angry': -1.5,
      'disappointed': -1,
      'worried': -1.1,
      'weary': -1.6,
      'cry': -2,
      'sob': -1.8
    }
  }
})()