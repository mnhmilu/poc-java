var q = 'tasks';

var redis = require('redis');
var restClient=require('./consumerRESTClient');

var open = require('amqplib').connect('amqp://mqadmin:mqadminpassword@localhost');
const querystring = require('querystring');
const http = require('http')


// Consumer
open.then(function (conn) {
  return conn.createChannel();
}).then(function (ch) {
  return ch.assertQueue(q).then(function (ok) {
    return ch.consume(q, function (msg) {
      if (msg !== null) {
        //redisclient = redis.createClient();
       var reqSequence="";
       var receiverParty=JSON.parse(msg.content.toString()).receiverParty;
       var identifier=JSON.parse(msg.content.toString()).identifier;
      
        console.log("Consumer-1", msg.content.toString());
        console.log("Consumer-1, receiverParty", receiverParty);
        console.log("Consumer-1, identifier", identifier);
        console.log("Consumer-1", 'I will now call CPS MW API for topup--------------------------------------------->');
        console.log("Date Time-------Before CPS CALL-------->", new Date());
        // using node fetch
        var redisClient = redis.createClient();

        redisClient.get(receiverParty, function (error, result) {
          if (error) {
            console.log(error);
            throw error;
          }
          console.log('GET result from redis->' + result);
          redisClient.quit();// check later 

          if(result==='1')
          {
            restClient.consumerRestClientCreateMandate(reqSequence,receiverParty,identifier);
          }
          else{
            console.log('not eligible to create mandate')
          }

        });      

        ch.ack(msg);
      }
    });
  });
}).catch(console.warn);
