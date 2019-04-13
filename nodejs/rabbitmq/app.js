'use strict';

const express = require('express');
const app = express();

const amqpClient = require('./amqpClient');

let channel;

amqpClient.createClient('amqp://mqadmin:mqadminpassword@192.168.0.122:5672' )
  .then(ch => {
    console.log('create channel initiated--------------------->') //remember if timeout ! no console log !
    channel = ch;
  }).catch(console.warn);

app.get('/test', function(req, res) {
  
  amqpClient.sendMessage(channel);
  console.log('Get----------->')
   return res.end("ok")
});

const server = require('http').createServer(app);
server.listen(3000, function() {
  console.log('App started.');
});

