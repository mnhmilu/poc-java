const dependencyModule = require("./dependencyModule");

function getTheSecret() {
  return `The secret was: ${dependencyModule.getSecretNumber()}`;
}

module.exports = {
  getTheSecret
};