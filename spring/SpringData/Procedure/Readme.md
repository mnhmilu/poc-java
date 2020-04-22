
Important Notes:

1.Becareful about file naming convention 

YourInterfaceName
YourInterfaceNameCustom
YourInterfaceNameImpl

2.Three way to call procedure

Using @NamedStoredProcedureQuery

Creating dynamic stored procedure

Using @Procedure of Spring data jpa


How to call procedure (mysql) from workbench:

CALL `multibank`.UpdateBankWiseReportValue('THE CITY BANK LTD.','dd',@updatestatus)

SELECT @updatestatus



References:

https://github.com/RadouaneRoufid/hibernate-tutorials/blob/master/jpa-stored-procedure-tutorial/src/test/java/com/roufid/tutorial/ApplicationTests.java

https://www.mysqltutorial.org/mysql-stored-procedure-tutorial.aspx

https://codz.me/2016/02/01/spring-propertyreferenceexception-exception/

http://roufid.com/3-ways-to-call-a-stored-procedure-with-hibernate-jpa-2-1/

