var restify = require('restify');

var server = restify.createServer({});
server.use(restify.CORS());

server.get('/', function (req, res, next) {
    res.send({version: '1.0.0'});
    next()
});

server.listen(3333, function () {
    console.log('%s listening at %s', server.name, server.url);
});