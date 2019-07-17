const assert = require("assert");
const sinon = require("sinon");
const userUtils = require("./userUtils");
const userApi = require("./userApi");

function aUser(id) {
  return {
    id,
    email: `someemail@user${id}.com`,
    first_name: `firstName${id}`,
    last_name: `lastName${id}`,
    avatar: `https://www.somepage${id}.com`
  };
}

describe("userUtils", function() {
  let getPageOfUsersStub;

  beforeEach(function() {
    getPageOfUsersStub = sinon.stub(userApi, "getPageOfUsers");
  });

  afterEach(function() {
    getPageOfUsersStub.restore();
  });

  describe("when a single page of users exists", function() {
    it("should return users from that page", async function() {
      // Arrange
      const pageOfUsers = {
        page: 1,
        total_pages: 1,
        data: [aUser(1), aUser(2), aUser(3)]
      };

      getPageOfUsersStub.returns(Promise.resolve(pageOfUsers));

      // Act
      const result = await userUtils.getAllUsers();

      // Assert
      assert.equal(result.length, 3);
      assert.equal(getPageOfUsersStub.calledOnce, true);
    });
  });

  describe("when multiple pages of users exists", function() {
    it("should return a combined list of all users", async function() {
      // Arrange
      const pageOfUsers1 = {
        page: 1,
        total_pages: 2,
        data: [aUser(1), aUser(2), aUser(3)]
      };
      const pageOfUsers2 = {
        page: 2,
        total_pages: 2,
        data: [aUser(4), aUser(5)]
      };

      getPageOfUsersStub.withArgs(1).returns(Promise.resolve(pageOfUsers1));
      getPageOfUsersStub.withArgs(2).returns(Promise.resolve(pageOfUsers2));

      // Act
      const result = await userUtils.getAllUsers();

      // Assert
      assert.equal(result.length, 5);
      assert.equal(getPageOfUsersStub.callCount, 2);
    });
  });
});