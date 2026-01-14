Drop table StudentAccountStatus;
Create Table StudentAccountStatus(
AccountNumber Varchar2(10) Primary key,
AccountBalance FLOAT,
StudentMobileNumber  varchar2(15) UNIQUE);

INSERT INTO StudentAccountStatus VALUES ('ACC001', 5000, '01711111111');
INSERT INTO StudentAccountStatus VALUES ('ACC002', 7000, '01722222222');
INSERT INTO StudentAccountStatus VALUES ('ACC003', 6000, '01733333333');
INSERT INTO StudentAccountStatus VALUES ('ACC004', 4500, '01744444444');
INSERT INTO StudentAccountStatus VALUES ('ACC005', 8000, '01755555555');

select *from StudentAccountStatus;


create table StudentEducationalStatus(
StudentID VARCHAR2(10) primary key ,
StudentName VARCHAR2(20) ,
StudentDepartment  VARCHAR2(20));

INSERT INTO StudentEducationalStatus VALUES ('S001', 'Ali', 'CSE');
INSERT INTO StudentEducationalStatus VALUES ('S002', 'Bob', 'EEE');
INSERT INTO StudentEducationalStatus VALUES ('S003', 'Chin', 'BBA');
INSERT INTO StudentEducationalStatus VALUES ('S004', 'Dana', 'CSE');
INSERT INTO StudentEducationalStatus VALUES ('S005', 'Eva', 'EEE');

select *from StudentEducationalStatus;

create table StudentAreaStatus (
ID VARCHAR2(10),
AccountNo Varchar2(10),
District varchar(20),
foreign key (id) REFERENCES StudentEducationalStatus(StudentID),
FOREIGN key (AccountNo) REFERENCES StudentAccountStatus(AccountNumber));

INSERT INTO StudentAreaStatus VALUES ('S001', 'ACC001', 'Dhaka');
INSERT INTO StudentAreaStatus VALUES ('S002', 'ACC002', 'Chittagong');
INSERT INTO StudentAreaStatus VALUES ('S003', 'ACC003', 'Khulna');
INSERT INTO StudentAreaStatus VALUES ('S004', 'ACC004', 'Sylhet');
INSERT INTO StudentAreaStatus VALUES ('S005', 'ACC005', 'Barishal');

select *from StudentAreaStatus;

Create or Replace Procedure FindStudentFullInformation(
Stu_id In VARCHAR2,
stu_name in VARCHAR2,
stu_dept in VARCHAR2,
acc_no   in VARCHAR2,
stu_dis in VARCHAR2,
result_cursor OUT SYS_REFCURSOR 
)
as 
begin
 OPEN result_cursor FOR
select e.StudentID,
       e.StudentName,
       e.StudentDepartment,
       a.AccountNumber,
       a.AccountBalance,
       a.StudentMobileNumber,
       ar.District
from StudentEducationalStatus e Left join StudentAreaStatus ar on e.StudentID = ar.ID
                                Left join StudentAccountStatus a on ar.AccountNo = a.AccountNumber
where (stu_id is null or e.StudentID = stu_id)
and   (stu_name is null or e.StudentName like '%' ||stu_name||'%')
and   (stu_dept is null or e.StudentDepartment =stu_dept)
and   (acc_no is null or a.accountnumber = acc_no )
and   (stu_dis is null or ar.district = stu_dis);

end;



VARIABLE rc REFCURSOR;
begin
 FindStudentFullInformation(NULL, NULL, NULL, 'ACC003', NULL, :rc);
end;
/
PRINT rc;
