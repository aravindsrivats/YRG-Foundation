'use strict';

var AddonsModel = require('../../../models').Addons;
var shortid = require('shortid');

module.exports = function(router) {
    router.get('/', function(req, res) {
        AddonsModel.find(function(err, data) {
            res.json(err ? {
                'error': 'API Error',
                'message': 'Error occurred'
            } : data);
        });
    });

    router.post('/', function(req, res){
        if ( !(req.body.id && req.body.name && req.body.price) )
            return res.status(400).json({'message' : 'Invalid request format'});
        if( typeof req.body.id == 'undefined' || req.body.id == '' )
            req.body.id = shortid.generate();

        var addon = new AddonsModel({
            'id' : req.body.id,
            'name' : req.body.name,
            'price' : Number(req.body.price)
        });

        addon.save(function(err){
            if(err)
                res.status(500).json({'message':'DB insertion error'});
            else
                res.status(200).json({'message':'Success'});
        });
    });

    router.delete('/:id',function(req, res){
        var id = req.params.id;

        AddonsModel.find({'id' : id}).remove(function(err){
            if(err)
                res.status(500).json({ 'error' : 'API Error', 'message': 'Could not delete addon'});
            else
                res.status(200).json({'message':'Success'});
        });
    });

    router.patch('/:id', function(req, res){
        var id = req.params.id;
        console.log("patching + " + id);
        AddonsModel.update({'id': id}, req.body, function(err, numUpdated){
            console.log(req.body);
            if(err)
                res.status(500).json({'error': 'API Error', 'message' : 'Could not update records'});
            else
                res.json({'message' : 'Success', 'response' : numUpdated });
        });
    });
};
