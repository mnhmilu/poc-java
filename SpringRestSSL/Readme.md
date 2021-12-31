1.Use Make file to create keystore and trustore (make file is attached with source code)
-------------------------------------------------------------------------

or creating a new keystore with a certificate authority, we can run make as follows:


$> make create-keystore PASSWORD=changeit
Now, we will add a certificate for our development host to this created keystore and sign it by our certificate authority:



$> make add-host HOSTNAME=localhost

To allow client authentication, we also need a keystore called ?truststore?. This truststore has to contain valid certificates of our certificate authority and all of the allowed clients. For reference on using keytool, please look into the Makefile at the following given sections:

$> make create-truststore PASSWORD=changeit
$> make add-client CLIENTNAME=cid

2. Share the truststore with the  client

tuststore must contain CA certificate (ca.crt) and Client key value pair (cid.p12)


For details go to http://www.baeldung.com/x-509-authentication-in-spring-security

* there are two types of mutual authentication 1) Certificate based 2 ) User -Password based

We used certificate based mutual authentication and simple testrestclient as a client where only trust store is used.

See the branch for client