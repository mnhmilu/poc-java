var assert = require('assert');
const expect = require('chai').expect;
const requestValidator = require('../middleware/airtimerequestvalidator');


describe('AutoTopUpProxyAPI', function () {

    before(function () {
        //console.log("Test-> Before started")
    });

    after(function () {
        //console.log("Test-> After Started")
        // runs after all tests in this block
    });

    beforeEach(function () {

        //console.log("Test-> Before Each started")

        // runs before each test in this block
    });

    afterEach(function () {

        //console.log("Test-> After Each started")

        // runs after each test in this block
    });

    context('validate MNO Request', function () {

        it('should pass 9000 when recevie in-valid Authentication', async function () {

            let request = {
                user: "User",
                pass: "WrongPass",
                msisdn: "01733400896"
            }

            expect(await requestValidator.validateAuthentication(request)).to.have.property('responsecode', '9000');

        });

        it('should pass 0000 when recevie valid Authentication', async function () {

            let request = {
                user: "user",
                pass: "pass",
                msisdn: "01733400896"
            }

            expect(await requestValidator.validateAuthentication(request)).to.have.property('responsecode', '0000');

        });


        it('should pass 1001 when recevie Missing Madatory feilds', async function () {

            let request = {
                user: "user",
                pass: "pass",            
            }

            expect(await requestValidator.validateMandatoryFields(request)).to.have.property('responsecode', '1001');

        });
        it('should pass 0000 when recevie all Madatory feilds', async function () {

            let request = {
                user: "user",
                pass: "pass",
                msisdn: "01733400896"
            }

            expect(await requestValidator.validateMandatoryFields(request)).to.have.property('responsecode', '0000');

        });

    });

});