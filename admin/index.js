'use strict';

var express = require('express'),
    kraken = require('kraken-js'),
    debug = require('debug')('yrg'),
    parse = require('./lib/parse'),
    options, app;

options = {
    onconfig: function(config, next) {
        parse = parse(config.get('parse').app_id, config.get('parse').javascript_key);
        debug('Initialising parse');
        next(null, config);
    }
};

app = module.exports = express();
app.use(kraken(options));

app.use(function(req, res, next) {
    req.parse = parse;
    next();
});

app.on('start', function() {
    app.listen(app.kraken.get('port'), function() {
        debug('Admin portal started');
        debug('Environment: %s', app.kraken.get('env:env'));
    });
});
