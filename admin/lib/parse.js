'use strict';

var parse;

module.exports = function(api_key, js_key) {
    if (!parse) {
        parse = require('parse/node').Parse;
        parse.initialize(api_key, js_key);
    }
    return parse;
};
