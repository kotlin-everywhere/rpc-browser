var restify = require('restify');

var server = restify.createServer({});
server.use(restify.CORS());
server.use(restify.queryParser());
server.use(restify.bodyParser());

server.get('/', function (req, res, next) {
    res.send({version: '1.0.0'});
    next()
});

server.get('/echo', function (req, res, next) {
    var data = JSON.parse(req.params.data);
    res.send({message: data.message});
    next()
});

server.post('/add', function (req, res, next) {
    res.send({result: req.params['value1'] + req.params['value2']});
    next()
});

server.post('/post-only', function (req, res, next) {
    res.send(true);
    next()
});

server.listen(3333, function () {
    console.log('%s listening at %s', server.name, server.url);
});