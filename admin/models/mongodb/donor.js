/**
 * Created by anchaturvedi on 28/11/15.
 */

'use strict';
var mongoose = require('mongoose');

function mongo_DonorModel() {
    var schema = mongoose.Schema({
        'name' : String,
        'email' : String
    });

    return mongoose.model('donor', schema);
};

module.exports = mongo_DonorModel();
