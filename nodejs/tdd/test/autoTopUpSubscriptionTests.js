var assert = require('assert');
describe('Array', function () {

  before(function () {
    // console.log("Test-> Before started")
  });

  after(function () {
    //  console.log("Test-> After Started")
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

  //It should create mandate
  //It should throw system exception if failed to create mandate
  //It should post mandate information via MNO API
  //It should update kyc information if MNO accept the mandate information
  //It should create system valid state for next airtime request 
  //It should notify customer for successful subscription
  //It should not create mandate if mno api is unreachable 3 times

//   context('with some context.....', function () {
//     it('should add 2 number  when two number is given', function () {
//       assert.equal(1, 1);
//     });

//   });
});