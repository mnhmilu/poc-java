'use strict';

const amqp = require('amqplib');
var q = 'tasks';

const createClient = (url) => amqp.connect(url)
  .then(conn => {
    console.log('channel created --####################################');   
    return conn.createChannel();
   }
  ).catch(console.warn);

const sendMessage = (ch,msg)  => new Promise(resolve => {
   ch.assertQueue(q).then(ch.sendToQueue(q, Buffer.from(msg)));

});

exports.createClient = createClient;
exports.sendMessage = sendMessage;
