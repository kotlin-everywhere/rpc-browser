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

server.get("/same", function (req, res, next) {
    res.send("GET");
    next()
});

server.post("/same", function (req, res, next) {
    res.send("POST");
    next()
});

server.put("/same", function (req, res, next) {
    res.send("PUT");
    next()
});

server.del("/same", function (req, res, next) {
    res.send("DELETE");
    next()
});


server.listen(3333, function () {
    console.log('%s listening at %s', server.name, server.url);
});