'use strict';

var redis = require('redis');

var client;


const createRedisClient = () => {
    client= redis.createClient().on('connect', function () {
         console.log('Redis client connected');
     });
     return this.client;
 }

const getData = (key) => new Promise(resolve => {

   this.client.get(key, function (error, result) {
        if (error) {
            console.log(error);
            throw error;
        }
        console.log('GET result ->' + result);
        return result;
    });

});

exports.createRedisClient = createRedisClient;
exports.getData = getData;
