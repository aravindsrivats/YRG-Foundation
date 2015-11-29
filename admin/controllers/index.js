'use strict';

var request = require('request');

function requester(res, template, key) {
    request('http://localhost:8000/api/' + key, function(err, response, body) {
        var result = {};
        result[key] = err ? [] : JSON.parse(body);
        res.render(template, result);
    });
}

module.exports = function(router) {

    function authenticator(req, res, next) {
        var auth = undefined;
        var sessionExists = (req.session.authStatus && req.session.authStatus == 'loggedIn');
        console.log(sessionExists);
        console.log(req.session.authStatus);
        if(sessionExists)
            next();
        else{
            if (req.headers.authorization) {
                auth = new Buffer(req.headers.authorization.substring(6), 'base64').toString().split(':');
            }
            console.log(auth);
            if( auth && (auth[0] === 'admin' && auth[1] === 'admin')) {
                console.log('setting session');
                req.session.authStatus = 'loggedIn';
                next();
            }
            else {
                console.log('entering for authentication');
                res.statusCode = 401;
                res.setHeader('WWW-Authenticate', 'Basic realm="YRG foundation"');
                res.end('Unauthorized');
                console.log("response end");
            }
        }
    }


    router.get('/', authenticator, function(req, res) {
        requester(res, 'institution', 'institutions');
    });

    router.get('/institutions', authenticator, function(req, res) {
        requester(res, 'institution', 'institutions');
    });

    router.get('/donors', authenticator, function(req, res) {
        requester(res, 'donor', 'donors');
    });

    router.get('/meals', authenticator, function(req, res) {
        requester(res, 'meals', 'meals');
    });

    router.get('/addons', authenticator, function(req, res) {
        requester(res, 'addons', 'addons');
    });

    router.get('/donations', authenticator, function(req, res) {
        requester(res, 'donations', 'donations');
    });

    router.get('/logout', function (req, res) {
        delete req.session.authStatus;
        res.statusCode = 401;
        res.setHeader('WWW-Authenticate', 'Basic realm="YRG foundation"');
        console.log(req.session.authStatus);
        res.send("logged out");
    });
};
