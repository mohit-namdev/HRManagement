package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.text.*;
import java.util.*;
import java.io.*;
public class EmployeeDAO implements EmployeeDAOInterface
{
private static final String FILE_NAME="employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
String name;
name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("length of employee name is null");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+ designationCode);
DesignationDAOInterface designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(!isDesignationCodeValid) throw new DAOException("designation code: "+designationCode+" not exists");
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)throw new DAOException("date is null");
char gender= employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)throw new DAOException("basicSalary is null");
if(basicSalary.signum()==-1)throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile= new RandomAccessFile(file,"rw");
String lastGeneratedEmployeeIdString="";
String recordCountString="";
int lastGeneratedEmployeeId=10000000;
int recordCount=0;
if(randomAccessFile.length()==0)
{
lastGeneratedEmployeeIdString="10000000";
lastGeneratedEmployeeIdString=String.format("%-10s",lastGeneratedEmployeeIdString);
recordCountString="0";
recordCountString=String.format("%-10s",recordCountString);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
}
else
{
lastGeneratedEmployeeId=Integer.parseInt(randomAccessFile.readLine().trim());
recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
}
String fPanNumber="";
String fAadharCardNumber="";
boolean panNumberExist=false;
boolean aadharCardNumberExist=false;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=0;x<=6;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(panNumberExist==false && fPanNumber.equalsIgnoreCase(panNumber)) panNumberExist=true;
if(aadharCardNumberExist==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) aadharCardNumberExist=true;
if(panNumberExist && aadharCardNumberExist)break;

}
if(panNumberExist && aadharCardNumberExist)
{
randomAccessFile.close();
throw new DAOException("pan number and aadhar card number exists");
}
if(panNumberExist)
{
randomAccessFile.close();
throw new DAOException("pan number exists");
}
if(aadharCardNumberExist)
{
randomAccessFile.close();
throw new DAOException("aadhar card number exists");
}
lastGeneratedEmployeeId++;
employeeId="A"+lastGeneratedEmployeeId;
employeeDTO.setEmployeeId(employeeId);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
SimpleDateFormat simpleDateFormat =new SimpleDateFormat("dd/MM/yyyy");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");
randomAccessFile.seek(0);
lastGeneratedEmployeeIdString=String.format("%-10d",lastGeneratedEmployeeId);
recordCount++;
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.writeBytes(lastGeneratedEmployeeIdString+"\n");
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
System.out.println("Designation name for this employee is: "+designationDAO.getByCode(employeeDTO.getDesignationCode()).getTitle());
}
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO==null) throw new DAOException("Employee is null");
String employeeId;
employeeId=employeeDTO.getEmployeeId();
if(employeeId==null) throw new DAOException("employee id is null");
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
String name;
name=employeeDTO.getName();
if(name==null) throw new DAOException("name is null");
name=name.trim();
if(name.length()==0)throw new DAOException("length of employee name is null");
int designationCode=employeeDTO.getDesignationCode();
if(designationCode<=0) throw new DAOException("Invalid designation code: "+ designationCode);
DesignationDAOInterface designationDAO=new DesignationDAO();
boolean isDesignationCodeValid=designationDAO.codeExists(designationCode);
if(!isDesignationCodeValid) throw new DAOException("designation code: "+designationCode+" not exists");
Date dateOfBirth=employeeDTO.getDateOfBirth();
if(dateOfBirth==null)throw new DAOException("date is null");
char gender= employeeDTO.getGender();
boolean isIndian=employeeDTO.getIsIndian();
BigDecimal basicSalary=employeeDTO.getBasicSalary();
if(basicSalary==null)throw new DAOException("basicSalary is null");
if(basicSalary.signum()==-1)throw new DAOException("basic salary is negative");
String panNumber=employeeDTO.getPANNumber();
if(panNumber==null) throw new DAOException("pan number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0)throw new DAOException("length of pan number is zero");
String aadharCardNumber=employeeDTO.getAadharCardNumber();
if(aadharCardNumber==null) throw new DAOException("Aadhar card number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)throw new DAOException("length of aadhar card number is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("file not found");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("file is empty");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
boolean employeeIdFound=false;
String fName;
int fDesignationCode;
Date fDateOfBirth;
SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber;
boolean panNumberFound=false;
String fAadharCardNumber;
boolean aadharCardNumberFound=false;
String employeeIdAgainstPanNumber="";
String employeeIdAgainstAadharCardNumber="";
int x;
long foundIdPointer=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
if(employeeIdFound==false)foundIdPointer=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=6;x++) randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId)) 
{
employeeIdFound=true;
}
if(panNumberFound==false && fPanNumber.equalsIgnoreCase(panNumber))
{
panNumberFound=true;
employeeIdAgainstPanNumber=fEmployeeId;
}
if(aadharCardNumberFound==false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound=true;
employeeIdAgainstAadharCardNumber=fEmployeeId;
}
if(employeeIdFound && panNumberFound && aadharCardNumberFound) break;
}

if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("employee id not found");
}
boolean panNumberExists=false;
if(panNumberFound && employeeIdAgainstPanNumber.equalsIgnoreCase(employeeId)==false)
{
panNumberExists=true;
}
boolean aadharCardNumberExists=false;
if(aadharCardNumberFound && employeeIdAgainstAadharCardNumber.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists=true;
}
if(panNumberExists && aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number and aadhar card number already exists");
}
if(panNumberExists)
{
randomAccessFile.close();
throw new DAOException("pan number already exists with another employeeId: "+employeeIdAgainstPanNumber);
}
if(aadharCardNumberExists)
{
randomAccessFile.close();
throw new DAOException("aadhar card number already exists with another employeeId: "+employeeIdAgainstAadharCardNumber);
}
randomAccessFile.seek(foundIdPointer);
for(x=1;x<=9;x++)randomAccessFile.readLine();
File tmpFile= new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile= new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundIdPointer);
randomAccessFile.writeBytes(employeeId+"\n");
randomAccessFile.writeBytes(name+"\n");
randomAccessFile.writeBytes(designationCode+"\n");
randomAccessFile.writeBytes(simpleDateFormat.format(dateOfBirth)+"\n");
randomAccessFile.writeBytes(gender+"\n");
randomAccessFile.writeBytes(isIndian+"\n");
randomAccessFile.writeBytes(basicSalary.toPlainString()+"\n");
randomAccessFile.writeBytes(panNumber+"\n");
randomAccessFile.writeBytes(aadharCardNumber+"\n");

tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();

}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(String employeeId) throws DAOException
{
if(employeeId==null) throw new DAOException("employee id is null");
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false)throw new DAOException("file not found");
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("file is empty");
}
randomAccessFile.readLine();
int recordCount= Integer.parseInt(randomAccessFile.readLine().trim());
String fEmployeeId;
boolean employeeIdFound=false;
int x;
long foundIdPointer=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
foundIdPointer=randomAccessFile.getFilePointer();
fEmployeeId=randomAccessFile.readLine();
for(x=1;x<=8;x++) randomAccessFile.readLine();
if(employeeIdFound==false && fEmployeeId.equalsIgnoreCase(employeeId)) 
{
employeeIdFound=true;
break;
}
}

if(employeeIdFound==false)
{
randomAccessFile.close();
throw new DAOException("employee id not found");
}
File tmpFile= new File("tmp.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile;
tmpRandomAccessFile= new RandomAccessFile(tmpFile,"rw");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
}
randomAccessFile.seek(foundIdPointer);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(randomAccessFile.getFilePointer());
recordCount--;
String recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString+"\n");
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();

}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getAll() throws DAOException
{
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return employees;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(randomAccessFile.readLine());
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing
}
char fGender=randomAccessFile.readLine().charAt(0);
GENDER gender;
if(fGender=='M')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false)
{
throw new DAOException("Invalid designation code: "+designationCode);
}
Set<EmployeeDTOInterface> employees=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return employees;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return employees;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId;
String fName;
int fDesignationCode;
int x;
EmployeeDTOInterface employeeDTO;
SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
employeeDTO=new EmployeeDTO();
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode!=designationCode) 
{
for(x=1;x<=6;x++)randomAccessFile.readLine();
continue;
}
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing
}
char fGender=randomAccessFile.readLine().charAt(0);
GENDER gender;
if(fGender=='M')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
employees.add(employeeDTO);
}
return employees;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean isDesignationAlloted(int designationCode) throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false)
{
throw new DAOException("Invalid designation code: "+designationCode);
}
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode) 
{
randomAccessFile.close();
return true;
}
for(x=1;x<=6;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByEmployeeId(String employeeId)throws DAOException
{
if(employeeId==null) throw new DAOException("employee id is null");
if(employeeId.length()==0) throw new DAOException("length of employee id is zero");
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) throw new DAOException("file not exist");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("file is empty");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId=null;
int x;
EmployeeDTOInterface employeeDTO=null;
SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)) 
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(randomAccessFile.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(randomAccessFile.readLine()));
try
{
employeeDTO.setDateOfBirth(simpleDateFormat.parse(randomAccessFile.readLine()));
}catch(ParseException parseException)
{
//do nothing
}
char fGender=randomAccessFile.readLine().charAt(0);
GENDER gender;
if(fGender=='M')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(Boolean.parseBoolean(randomAccessFile.readLine()));
employeeDTO.setBasicSalary(new BigDecimal(randomAccessFile.readLine()));
employeeDTO.setPANNumber(randomAccessFile.readLine());
employeeDTO.setAadharCardNumber(randomAccessFile.readLine());
randomAccessFile.close();
return employeeDTO;
}
for(x=1;x<=8;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
throw new DAOException("Invalid Employee ID");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByPANNumber(String panNumber)throws DAOException
{
if(panNumber==null) throw new DAOException("Pan Number is null");
panNumber=panNumber.trim();
if(panNumber.length()==0) throw new DAOException("length of Pan Number is zero");
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) throw new DAOException("file not exist");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("file is empty");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId=null;
String fName=null;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber=null;
String fAadharCardNumber=null;
int x;
EmployeeDTOInterface employeeDTO=null;
SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try 
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber)) 
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
GENDER gender;
if(fGender=='M')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Pan Number");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public EmployeeDTOInterface getByAadharCardNumber(String aadharCardNumber)throws DAOException
{
if(aadharCardNumber==null) throw new DAOException("Aadhar Card Number is null");
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) throw new DAOException("length of Aadhar Card Number is zero");
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) throw new DAOException("file not exist");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("file is empty");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId=null;
String fName=null;
int fDesignationCode;
Date fDateOfBirth=null;
char fGender;
boolean fIsIndian;
BigDecimal fBasicSalary;
String fPanNumber=null;
String fAadharCardNumber=null;
int x;
EmployeeDTOInterface employeeDTO=null;
SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
try 
{
fDateOfBirth=simpleDateFormat.parse(randomAccessFile.readLine());
}catch(ParseException parseException)
{
//do nothing
}
fGender=randomAccessFile.readLine().charAt(0);
fIsIndian=Boolean.parseBoolean(randomAccessFile.readLine());
fBasicSalary=new BigDecimal(randomAccessFile.readLine());
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) 
{
employeeDTO=new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setName(fName);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setDateOfBirth(fDateOfBirth);
GENDER gender;
if(fGender=='M')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
employeeDTO.setGender(gender);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPanNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
randomAccessFile.close();
return employeeDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid Aadhar Card Number");
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean employeeIdExists(String employeeId)throws DAOException
{
if(employeeId==null) return false;
if(employeeId.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fEmployeeId=null;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fEmployeeId=randomAccessFile.readLine();
if(fEmployeeId.equalsIgnoreCase(employeeId)) 
{
randomAccessFile.close();
return true;
}
for(x=1;x<=8;x++)randomAccessFile.readLine();
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean panNumberExists(String panNumber)throws DAOException
{
if(panNumber==null) return false;
panNumber=panNumber.trim();
if(panNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPanNumber;
String fAadharCardNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++)randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fPanNumber.equalsIgnoreCase(panNumber)) 
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean aadharCardNumberExists(String aadharCardNumber)throws DAOException
{
if(aadharCardNumber==null) return false;
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0) return false;
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return false;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fPanNumber;
String fAadharCardNumber;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
for(x=1;x<=7;x++)randomAccessFile.readLine();
fPanNumber=randomAccessFile.readLine();
fAadharCardNumber=randomAccessFile.readLine();
if(fAadharCardNumber.equalsIgnoreCase(aadharCardNumber)) 
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount= Integer.parseInt(randomAccessFile.readLine().trim());
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public int getCountByDesignation(int designationCode)throws DAOException
{
if(new DesignationDAO().codeExists(designationCode)==false)
{
return 0;
}
try
{
File file=new File(FILE_NAME);
if(!(file.exists())) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fDesignationCode;
int x;
int count=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
randomAccessFile.readLine();
fDesignationCode=Integer.parseInt(randomAccessFile.readLine());
if(fDesignationCode==designationCode) 
{
count++;
}
for(x=1;x<=6;x++)randomAccessFile.readLine();
}
return count;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}
