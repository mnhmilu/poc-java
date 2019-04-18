var redis = require('redis');
var client = redis.createClient();// add ip here

client.on('connect', function() {
    console.log('Redis client connected');
});

client.on('error', function (err) {
    console.log('Something went wrong ' + err);
});
console.log("Setting sample value---->");
client.set('01714119922', '1', redis.print);
client.get('01714119922', function (error, result) {
    if (error) {
        console.log(error);
        throw error;
    }
    console.log('GET result ->' + result);
    
});

