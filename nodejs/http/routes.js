var appRouter = function (app) {

    //var AutoTopUpModel =require('../models/AutoTopupModels');

    app.get("/", function (req, res) {
        console.log("Calling rest api");
        res.status(200).send("Welcome to our restful API");
        process.on('exit', function (code) {
            return console.log(`About to exit with code ${code}`);
        });

    });

    app.get("/autotopup", function (req, res) {
        console.log("Calling rest api");
        res.status(200).send("Welcome to our restful API-Another api");
        process.on('exit', function (code) {
            return console.log(`About to exit with code ${code}`);
        });

    });


    app.post("/createmandate", function (req, res) {
  
        console.log("Test Model is:", req.body.msisdn);
        console.log ("Date Time-------Create Mandate Started-------->",new Date());
        var q = 'tasks';

        var open = require('amqplib').connect('amqp://mqadmin:mqadminpassword@localhost');

        // Publisher
        open.then(function (conn) {
            return conn.createChannel();
        }).then(function (ch) {
            return ch.assertQueue(q).then(function (ok) {
                return ch.sendToQueue(q, Buffer.from(JSON.stringify(req.body)));
            });
        }).catch(console.warn);

        res.send("ok-createmandate");
    });

    app.post("/autotopuppost", function (req, res) {
        console.log("Test Model is:", req.body.msisdn);

        /////

        var q = 'tasks';

        var open = require('amqplib').connect('amqp://mqadmin:mqadminpassword@localhost');

        // Publisher
        open.then(function (conn) {
            return conn.createChannel();
        }).then(function (ch) {
            return ch.assertQueue(q).then(function (ok) {
                return ch.sendToQueue(q, Buffer.from(JSON.stringify(req.body)));
            });
        }).catch(console.warn);

        /////
        res.send("ok");
    });

}

module.exports = appRouter;
