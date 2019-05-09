var mongoose = require('mongoose');
var Schema = mongoose.Schema;
var AutotopuphistoryModel = require('../models/AutoTopupModels.js');
const moment = require('moment')




const getAutoTopUpHistory = (msisdn) => {

    mongoose.connect('mongodb://myUserAdmin:abc123@10.32.14.xx:27017/autotopup?authSource=admin', {
    useNewUrlParser: true
    });

    AutotopuphistoryModel.find({
        '_id':msisdn
     }, function (err, result) {
         if (err) throw err;
         if (result) {
            console.log('mongo call back sucess-----');
            console.log(result);
            return result;
         } else {
            return  res.send(JSON.stringify({
                 error: 'Error'
             }))
         }
     });

}

const getAutoTopUpHistoryQueryForToday = (msisdn) => {

    mongoose.connect('mongodb://myUserAdmin:abc123@10.32.14.xx:27017/autotopup?authSource=admin', {
    useNewUrlParser: true
    });

    const today = moment().startOf('day');

    AutotopuphistoryModel.findOne({'msisdn':msisdn, 'Date':{'$gte': today.toDate(),'$lte':moment(today).endOf('day').toDate()} }, 'TotalAirTimeAttempt TotalFailed', function (err, result) {
        if (err) return handleError(err);
        // Prints "Space Ghost is a talk show host".
        console.log("mongoose db callback successfull--------------------->")
        console.log(result);
        return result;
      }).explain();

}

const saveAutoTopUpModel = (msisdn) => {

    mongoose.connect('mongodb://myUserAdmin:abc123@10.32.14.xx:27017/autotopup?authSource=admin', {
    useNewUrlParser: true
    });

    var entity = new AutotopuphistoryModel({
        'msisdn':8801733400896,
        'AirtimeAmount': 1,
        'Date': new Date()-4,
        'TotalAirTimeAttempt': 1,
        'TotalFailed': 1,
        'TotalSuccessful': 1

    });

    entity.save()
    .then(item => {
      console.log("item saved to database");
    })
    .catch(err => {
      console.log("unable to save to database",err);
    }); 

}

const findAndUpdateAutoTopUpModel = (msisdn) => {

    mongoose.connect('mongodb://myUserAdmin:abc123@10.32.14.xx:27017/autotopup?authSource=admin', {
    useNewUrlParser: true
    });
    const today = moment().startOf('day');

    var entitytest = AutotopuphistoryModel({
        'msisdn':8801733400896,
        'TotalAirTimeAttempt': 5,
        'TotalFailed': 1,
        'TotalSuccessful': 1
    });

    var productToUpdate = {};
    productToUpdate = Object.assign(productToUpdate, entitytest._doc);
    delete productToUpdate._id;

    var query = {'msisdn':msisdn, 'Date':{'$gte': today.toDate(),'$lte':moment(today).endOf('day').toDate()}} ;

    AutotopuphistoryModel.findOneAndUpdate(query, productToUpdate, {upsert:true}, function(err, doc){
        if (err)
        {
            console.log(err);
        }
        console.log("succesfully saved");
    });

}


module.exports = {
    getAutoTopUpHistory,getAutoTopUpHistoryQueryForToday,saveAutoTopUpModel,findAndUpdateAutoTopUpModel
}
