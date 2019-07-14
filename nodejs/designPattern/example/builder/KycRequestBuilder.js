const KycRequest =require('./KycRequest')

class KycRequestBuilder{


    constructor(command) {
        this.command = command;
    }

    withRechargeAmount(amounrechargeAmount) {
        this.rechargeAmount = rechargeAmount;
        return this;
    }

    withRechargeStatus(rechargeStatus) {
        this.rechargeStatus = rechargeStatus;
        return this;
    }

    build() {
        return new KycRequest(this);
    }


}
