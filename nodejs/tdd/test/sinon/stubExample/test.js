const assert = require("assert");
const sinon = require("sinon");

const dependencyModule = require("./dependencyModule");
const { getTheSecret } = require("./moduleUnderTest");

describe("moduleUnderTest", function() {
  describe("when the secret is 3", function() {
    it("should be returned with a string prefix", function() {
      sinon.stub(dependencyModule, "getSecretNumber").returns(3);
      const result = getTheSecret();
      assert.equal(result, "The secret was: 3");
    });
  });
});