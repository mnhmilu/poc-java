CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateBankWiseReportValue`(
 IN bankName varchar(200),
 IN slotNumber varchar(200),
 OUT updatestatus varchar(200)
)
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
        BEGIN
           SET updatestatus=CONCAT('Error occured for bank ',bankName,' slotnumber ',slotNumber);
            ROLLBACK;
            RESIGNAL;
        END;
        START TRANSACTION;
  CASE bankName
     WHEN 'THE CITY BANK LTD.' THEN
         UPDATE process_file_details_stage SET bank_type ='test' where trusted_bank_name=bankName;
         SET updatestatus=CONCAT('update success for bank ',bankName,' slotnumber ',slotNumber);
     WHEN 'DHAKA BANK LTD.' THEN
         UPDATE process_file_details_stage SET account_no =SUBSTRING(account_no, 1, 3) where trusted_bank_name=bankName;
         SET updatestatus=CONCAT('update success for bank ',bankName,' slotnumber ',slotNumber);
      ELSE
         SET updatestatus=CONCAT('No Bank Case Found for bank ',bankName,' slotnumber ',slotNumber);
         END CASE;
         COMMIT;
END
