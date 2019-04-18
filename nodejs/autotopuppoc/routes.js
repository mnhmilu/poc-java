
const amqpClient = require('./amqpClient');

let channel;

amqpClient.createClient('amqp://mqadmin:mqadminpassword@localhost')
    .then(ch => {
        console.log('create channel initiated--------------------->') //remember if timeout ! no console log !
        channel = ch;
    }).catch(console.warn);

var appRouter = function (app) {

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

        try {
            console.log("Test Model is:", req.body.msisdn);
            console.log("Create Mandate Started-------->", new Date());
            amqpClient.sendMessage(channel, JSON.stringify(req.body));
            res.sendStatus(200);

        } catch (e) {
            console.log(e.message);       
        }
    });

    app.post("/autotopuppost", function (req, res) {

        res.send("ok");
    });

}

module.exports = appRouter;
