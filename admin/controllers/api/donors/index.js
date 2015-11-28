'use strict';

var DonorModel = require('../../../models/mongodb/donor.js')

module.exports = function(router) {

    router.get('/', function(req, res){
        DonorModel.find(function(err, data){
            if(err)
                res.send({"message":"error occurred"});
            else
                res.send(data);
        });
    });
};
