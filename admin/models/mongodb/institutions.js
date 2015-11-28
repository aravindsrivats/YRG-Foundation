/**
 * Created by anchaturvedi on 28/11/15.
 */

'use strict';
var mongoose = require('mongoose');

function mongo_InstitutionModel() {
    var schema = mongoose.Schema({
        'id' : Number,
        'name' : String,
        'address' : String,
        'category' : [String],
        'capacity' : Number
    });

    return mongoose.model('institutions', schema);
};

module.exports = mongo_InstitutionModel();
