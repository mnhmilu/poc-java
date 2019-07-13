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


  context('with some context.....', function () {
    it('should add 2 number  when two number is given', function () {
      assert.equal(1, 1);
    });

  });
});