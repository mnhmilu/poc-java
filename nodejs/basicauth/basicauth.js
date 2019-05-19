//https://www.npmjs.com/package/express-basic-auth


app.use(basicAuth( { authorizer: myAuthorizer } ))
 
 function myAuthorizer(username, password) {
     const userMatches = basicAuth.safeCompare(username, 'admin')
     const passwordMatches = basicAuth.safeCompare(password, 'supersecret') 
     return userMatches & passwordMatches
 }
