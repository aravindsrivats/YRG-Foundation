'use strict';

var IndexModel = require('../models/index');
var request = require('request');

function requester(res, template, key) {
    request('http://localhost:8000/api/' + key, function(err, response, body) {
        var result = {};
        result[key] = err ? [] : JSON.parse(body);
        res.render(template, result);
    });
}

module.exports = function(router) {
    
    router.get('/', function(req, res) {
        requester(res, 'admin', 'institutions');
    });

    router.get('/institutions', function(req, res) {
        requester(res, 'admin', 'institutions');
    });

    router.get('/donors', function(req, res) {
        requester(res, 'donor', 'donors');
    });

    router.get('/meals', function(req, res) {
        requester(res, 'meals', 'meals');
    });

    router.get('/addons', function(req, res) {
        requester(res, 'addons', 'addons');
    });
};
