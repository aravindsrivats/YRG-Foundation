'use strict';

var DonorModel = require('../../../models').Donor,
    shortid = require('shortid');

module.exports = function(router) {
    router.get('/', function(req, res) {
        DonorModel.find(function(err, data) {
            res.json(err ? {
                'error': 'API Error',
                'message': 'Error occurred'
            } : data);
        });
    });

    router.post('/', function(req, res) {
        var response = {};
        if (typeof req.body.name === 'undefined' || req.body.name === '') {
            response = {
                'error': 'Incorrect params',
                'message': 'Name cannot be empty'
            };
        }
        if (typeof req.body.email === 'undefined' || req.body.email === '') {
            response = {
                'error': 'Incorrect params',
                'message': 'Email cannot be empty'
            };
        }
        if (typeof req.body.phone === 'undefined' || req.body.phone === '') {
            response = {
                'error': 'Incorrect params',
                'message': 'Phone cannot be empty'
            };
        }

        if( typeof req.body.id == 'undefined' || req.body.id == '' )
            req.body.id = shortid.generate();

        if (!Object.keys(response).length) {
            var model = {
                id: req.body.id,
                name: req.body.name,
                email: req.body.email,
                phone: req.body.phone
            };

            DonorModel(model).save(function(err, data) {
                res.json(err ? {
                    'error': 'API Error',
                    'message': 'Could not add donor'
                } : data);
            });
        } else {
            res.json(response);
        }
    });

    router.delete('/:id',function(req, res){
        var id = req.params.id;

        DonorModel.find({'id' : id}).remove(function(err){
            if(err)
                res.status(500).json({ 'error' : 'API Error', 'message': 'Could not delete donor'});
            else
                res.status(200).json({'message':'Success'});
        });
    });

    router.patch('/:id', function(req, res){
        var id = req.params.id;
        DonorModel.update({'id': id}, req.body, function(err, numUpdated){
            if(err)
                res.status(500).json({'error': 'API Error', 'message' : 'Could not update records'});
            else
                res.json({'message' : 'Success', 'response' : numUpdated });
        });
    });
};
