class KycRequest {

    constructor(builder) {
        rechargeAmount = builder.rechargeAmount,
            rechargeFrequency = builder.rechargeFrequency,
            rechargeStatus = builder.rechargeStatus,
            rechargeMNO = builder.rechargeMNO,
            command = builder.command
    }

    toString() {
        return JSON.stringify(this);
    }

}


module.exports = KycRequest;