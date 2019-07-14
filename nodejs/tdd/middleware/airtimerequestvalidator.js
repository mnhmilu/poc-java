

const validateAuthentication = async (request) => {

    //console.error(request)


    let responseCode = '9000'

    if (request.user.toString().trim() === "user" && request.pass.toString().trim() === "pass") {
        responseCode = '0000';
    }

    let response = {
        responsecode: responseCode
    }

    return response;
}

const validateMandatoryFields = async (request) => {


    let responseCode = '0000'

    if (!request.msisdn || !request.user || !request.pass) {
        responseCode = '1001'
    }


    let response = {
        responsecode: responseCode
    }

    return response;
}

module.exports = {

    validateAuthentication,
    validateMandatoryFields
}