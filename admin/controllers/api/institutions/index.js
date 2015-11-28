/**
 * Created by anchaturvedi on 28/11/15.
 */
'use strict';

var InstitutionModel = require('../../../models/mongodb/institutions.js');

module.exports = function(router) {
    router.get('/', function(req, res) {
        InstitutionModel.find(function(err, data){
            if(err)
                res.send({"message":"error occurred"});
            else
                res.send(data);
        });
    });
};
