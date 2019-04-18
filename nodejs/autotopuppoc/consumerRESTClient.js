const querystring = require('querystring');
const http = require('http');



const consumerRestClientCreateMandate = async (reqSequence, receiverParty, identifier) => {
    var postData = JSON.stringify({
        "reqSequence": reqSequence,
        "receiverParty": receiverParty,
        "identifier": identifier
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

}

module.exports = {

    consumerRestClientCreateMandate
}
