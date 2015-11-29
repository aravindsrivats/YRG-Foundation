'use strict';

var mongoose = require('mongoose');

var donation = function() {
    var schema = mongoose.Schema({
        'id': String,
        'donorid': String,
        'institution': String,
        'date': {
            type: Date,
            default: Date.now,
        },
        'slot': [String],
        'category': [String],
        'amount': Number,
        'present': {
            type: Boolean,
            default: false
        }
    });
    return mongoose.model('donations', schema);
};

module.exports = donation();
