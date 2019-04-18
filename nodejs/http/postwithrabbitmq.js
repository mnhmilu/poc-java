var q = 'tasks';

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
        console.log("Consumer-1", msg.content.toString());
        console.log("Consumer-1", JSON.parse(msg.content.toString()).receiverParty);
        console.log("Consumer-1", JSON.parse(msg.content.toString()).identifier);
        console.log("Consumer-1", 'I will now call CPS MW API for topup--------------------------------------------->');
        console.log("Date Time-------Before CPS CALL-------->", new Date());
        // using node fetch


        var postData = JSON.stringify({
          "reqSequence": "",
          "receiverParty": "01714119922",
          "identifier": "Banglalink"
        });

        var options = {
          hostname: 'localhost',
          port: 9181,
          path: '/cps-api/cps/api/autotopup/createmandate',
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          }
        };

        var req = http.request(options, (res) => {
          // console.log('mandateID', res.mandateID);
          console.log('headers:', res.headers);

          res.on('data', (d) => {
            process.stdout.write(d);
          });
        });

        req.on('error', (e) => {
          console.error(e);
        });

        req.write(postData);
        req.end();


        console.log("Consumer-1", 'I will now end call CPS MW API for topup--------------------------------------------->');
        console.log("Date Time-------After CPS CALL-------->", new Date());
        //end of node fetch

        ch.ack(msg);
      }
    });
  });
}).catch(console.warn);
