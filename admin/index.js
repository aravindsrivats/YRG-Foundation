'use strict';

var express = require('express'),
    kraken = require('kraken-js'),
    debug = require('debug')('yrg'),
    mongoose = require('mongoose'),
    options, app;

mongoose.connect('mongodb://localhost/yrg');

options = {
    onconfig: function(config, next) {
        next(null, config);
    }
};

app = module.exports = express();
app.use(kraken(options));
app.on('start', function() {
    app.listen(app.kraken.get('port'), function() {
        debug('Admin portal started');
        debug('Environment: %s', app.kraken.get('env:env'));
    });
});
