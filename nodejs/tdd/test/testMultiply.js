var assert = require('assert');
describe('Array', function () {

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



  it('should multily when give two number', function () {

    assert.equal(1, 1);


  });

  it('should test chai expect', function () {
    var expect = require('chai').expect
      , foo = 'bar'
      , beverages = { tea: ['chai', 'matcha', 'oolong'] };

    expect(foo).to.be.a('string');
    expect(foo).to.equal('bar');
    expect(foo).to.have.lengthOf(3);
    expect(beverages).to.have.property('tea').with.lengthOf(3);

  });

  it('should test chai should', function () {

    var should = require('chai').should() //actually call the function
      , foo = 'bar'
      , beverages = { tea: ['chai', 'matcha', 'oolong'] };

    foo.should.be.a('string');
    foo.should.equal('bar');
    foo.should.have.lengthOf(3);
    beverages.should.have.property('tea').with.lengthOf(3);


  });

});