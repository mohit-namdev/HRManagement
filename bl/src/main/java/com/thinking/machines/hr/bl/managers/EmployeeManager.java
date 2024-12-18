package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;
public class EmployeeManager implements EmployeeManagerInterface
{
private Map<String,EmployeeInterface> employeeIdWiseEmployeesMap;
private Map<String,EmployeeInterface> panNumberWiseEmployeesMap;
private Map<String,EmployeeInterface> aadharCardNumberWiseEmployeesMap;
private Set<EmployeeInterface> employeesSet;
private Map<Integer,Set<EmployeeInterface>> designationCodeWiseEmployeesMap;

private static EmployeeManager employeeManager=null;
private EmployeeManager() throws BLException
{
populateDataStructures();
}
public void populateDataStructures() throws BLException
{
this.employeeIdWiseEmployeesMap= new HashMap<>();
this.panNumberWiseEmployeesMap=new HashMap<>();
this.aadharCardNumberWiseEmployeesMap=new HashMap<>();
this.employeesSet=new TreeSet<>();
this.designationCodeWiseEmployeesMap=new HashMap<>();
try
{
Set<EmployeeDTOInterface> dlEmployees;
dlEmployees=new EmployeeDAO().getAll();
EmployeeInterface employee;
DesignationManager designationManager=DesignationManager.getDesignationManager();
DesignationInterface designation;
Set<EmployeeInterface> ets;
for(EmployeeDTOInterface dlEmployee:dlEmployees)
{
employee=new Employee();
employee.setEmployeeId(dlEmployee.getEmployeeId());
employee.setName(dlEmployee.getName());
designation=designationManager.getDesignationByCode(dlEmployee.getDesignationCode());
employee.setDesignation(designation);
employee.setDateOfBirth(dlEmployee.getDateOfBirth());
GENDER gender;
if(dlEmployee.getGender()=='M')gender=GENDER.MALE;
else gender=GENDER.FEMALE;
employee.setGender(gender);
employee.setIsIndian(dlEmployee.getIsIndian());
employee.setBasicSalary(dlEmployee.getBasicSalary());
employee.setPANNumber(dlEmployee.getPANNumber());
employee.setAadharCardNumber(dlEmployee.getAadharCardNumber());
this.employeeIdWiseEmployeesMap.put(employee.getEmployeeId().toUpperCase(),employee);
this.panNumberWiseEmployeesMap.put(employee.getPANNumber().toUpperCase(),employee);
this.aadharCardNumberWiseEmployeesMap.put(employee.getAadharCardNumber().toUpperCase(),employee);
this.employeesSet.add(employee);
ets=this.designationCodeWiseEmployeesMap.get(designation.getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(employee);
this.designationCodeWiseEmployeesMap.put(new Integer(designation.getCode()),ets);
}
else
{
ets.add(employee);
}
}
}catch(DAOException daoException)
{
BLException blException =new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static EmployeeManager getEmployeeManager() throws BLException
{
if(employeeManager==null) employeeManager=new EmployeeManager();
return employeeManager;
}
public void addEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)blException.setGenericException("employee required");
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();
if(employeeId!=null) 
{
employeeId=employeeId.trim();
if(employeeId.length()>0)
{
blException.addException("employeeId","employee Id. should be nil/empty");
}
}
if(name==null)
{
blException.addException("name","name required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","name required");
}

DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","designation required");
}
else
{
designationCode=designation.getCode();
if((designationManager.designationCodeExists(designationCode))==false) blException.addException("designation","invalid designation");
}

if(dateOfBirth==null) blException.addException("dateOfBirth","date of birth required");

if(gender==' ') blException.addException("gender","gender required");

if(basicSalary==null)
{
blException.addException("basicSalary","basic salary required");
}
else
{
if(basicSalary.signum()==-1) blException.addException("basicSalary","basic salary cannot be negative");
}

if(panNumber==null) 
{
blException.addException("panNumber","pan number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","pan number required");
}

if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
blException.addException("aadharCardNumber","aadhar card number required");
}

if(panNumber!=null && panNumber.length()>0)
{
if(panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase()))blException.addException("panNumber","pan number "+panNumber+" exists.");
}

if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
if(aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase())) blException.addException("aadharCardNumber","aadhar card number "+aadharCardNumber+ " exists.");
}

if(blException.hasExceptions())
{
throw blException;
}

try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.add(dlEmployee);
employeeId=dlEmployee.getEmployeeId();
employee.setEmployeeId(employeeId);
EmployeeInterface dsEmployee=new Employee();
dsEmployee.setEmployeeId(employeeId);
dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateEmployee(EmployeeInterface employee) throws BLException
{
BLException blException=new BLException();
if(employee==null)blException.setGenericException("employee required");
String employeeId=employee.getEmployeeId();
String name=employee.getName();
DesignationInterface designation=employee.getDesignation();
int designationCode=0;
Date dateOfBirth=employee.getDateOfBirth();
char gender=employee.getGender();
boolean isIndian=employee.getIsIndian();
BigDecimal basicSalary=employee.getBasicSalary();
String panNumber=employee.getPANNumber();
String aadharCardNumber=employee.getAadharCardNumber();

if(employeeId==null)
{
blException.addException("employeeId","employee Id. required");
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
blException.addException("employeeId","employee Id. required");
}
else
{
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
blException.addException("employeeId","Invalid employee Id."+employeeId);
throw blException;
}
}
}

if(name==null)
{
blException.addException("name","name required");
}
else
{
name=name.trim();
if(name.length()==0) blException.addException("name","name required");
}

DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
if(designation==null)
{
blException.addException("designation","designation required");
}
else
{
designationCode=designation.getCode();
if((designationManager.designationCodeExists(designationCode))==false) blException.addException("designation","invalid designation");
}

if(dateOfBirth==null) blException.addException("dateOfBirth","date of birth required");

if(gender==' ') blException.addException("gender","gender required");

if(basicSalary==null)
{
blException.addException("basicSalary","basic salary required");
}
else
{
if(basicSalary.signum()==-1) blException.addException("basicSalary","basic salary cannot be negative");
}

if(panNumber==null) 
{
blException.addException("panNumber","pan number required");
}
else
{
panNumber=panNumber.trim();
if(panNumber.length()==0) blException.addException("panNumber","pan number required");
}

if(aadharCardNumber==null)
{
blException.addException("aadharCardNumber","aadhar card number required");
}
else
{
aadharCardNumber=aadharCardNumber.trim();
if(aadharCardNumber.length()==0)
blException.addException("aadharCardNumber","aadhar card number required");
}

if(panNumber!=null && panNumber.length()>0)
{
EmployeeInterface ee=this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(ee!=null && (ee.getEmployeeId()).equalsIgnoreCase(employeeId)==false)blException.addException("panNumber","pan number "+panNumber+" exists.");
}

if(aadharCardNumber!=null && aadharCardNumber.length()>0)
{
EmployeeInterface ee=this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(ee!=null && (ee.getEmployeeId()).equalsIgnoreCase(employeeId)==false)blException.addException("aadharCardNumber","aadhar card number "+aadharCardNumber+" exists.");
}

if(blException.hasExceptions())
{
throw blException;
}

try
{
EmployeeInterface dsEmployee;
dsEmployee=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
String oldPANNumber=dsEmployee.getPANNumber();
String oldAadharCardNumber=dsEmployee.getAadharCardNumber();
DesignationInterface oldDesignation=dsEmployee.getDesignation();
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface dlEmployee=new EmployeeDTO();
dlEmployee.setEmployeeId(dsEmployee.getEmployeeId());
dlEmployee.setName(name);
dlEmployee.setDesignationCode(designation.getCode());
dlEmployee.setDateOfBirth(dateOfBirth);
dlEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dlEmployee.setIsIndian(isIndian);
dlEmployee.setBasicSalary(basicSalary);
dlEmployee.setPANNumber(panNumber);
dlEmployee.setAadharCardNumber(aadharCardNumber);
employeeDAO.update(dlEmployee);

dsEmployee.setName(name);
dsEmployee.setDesignation(((DesignationManager)designationManager).getDSDesignationByCode(designation.getCode()));
dsEmployee.setDateOfBirth((Date)dateOfBirth.clone());
dsEmployee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
dsEmployee.setIsIndian(isIndian);
dsEmployee.setBasicSalary(basicSalary);
dsEmployee.setPANNumber(panNumber);
dsEmployee.setAadharCardNumber(aadharCardNumber);
//remove old data from data structures
this.employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeId().toUpperCase());
this.panNumberWiseEmployeesMap.remove(oldPANNumber.toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(oldAadharCardNumber.toUpperCase());
this.employeesSet.remove(dsEmployee);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(oldDesignation.getCode());
ets.remove(dsEmployee);


this.employeeIdWiseEmployeesMap.put(dsEmployee.getEmployeeId().toUpperCase(),dsEmployee);
this.panNumberWiseEmployeesMap.put(panNumber.toUpperCase(),dsEmployee);
this.aadharCardNumberWiseEmployeesMap.put(aadharCardNumber.toUpperCase(),dsEmployee);
this.employeesSet.add(dsEmployee);
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
if(ets==null)
{
ets=new TreeSet<>();
ets.add(dsEmployee);
this.designationCodeWiseEmployeesMap.put(new Integer(dsEmployee.getDesignation().getCode()),ets);
}
else
{
ets.add(dsEmployee);
}

}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeEmployee(String employeeId) throws BLException
{
if(employeeId==null)
{
BLException blException=new BLException();
blException.addException("employeeId","employee Id. required");
throw blException;
}
else
{
employeeId=employeeId.trim();
if(employeeId.length()==0)
{
BLException blException=new BLException();
blException.addException("employeeId","employee Id. required");
throw blException;
}
else
{
if(this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase())==false)
{
BLException blException=new BLException();
blException.addException("employeeId","Invalid employee Id."+employeeId);
throw blException;
}
}
}
try
{
EmployeeInterface dsEmployee;
dsEmployee=this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
employeeDAO.delete(employeeId);

//remove old data from data structures
this.employeeIdWiseEmployeesMap.remove(dsEmployee.getEmployeeId().toUpperCase());
this.panNumberWiseEmployeesMap.remove(dsEmployee.getPANNumber().toUpperCase());
this.aadharCardNumberWiseEmployeesMap.remove(dsEmployee.getAadharCardNumber().toUpperCase());
this.employeesSet.remove(dsEmployee);
Set<EmployeeInterface> ets;
ets=this.designationCodeWiseEmployeesMap.get(dsEmployee.getDesignation().getCode());
ets.remove(dsEmployee);
}catch(DAOException daoException)
{
BLException blException=new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<EmployeeInterface> getEmployees()
{
Set<EmployeeInterface> employees=new TreeSet<>();
EmployeeInterface employee;
DesignationInterface designation;
DesignationInterface dsDesignation;
for(EmployeeInterface dsEmployee: this.employeesSet)
{
employee =new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
char gender=dsEmployee.getGender();
employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public EmployeeInterface getEmployeeByEmployeeId(String employeeId) throws BLException
{
EmployeeInterface dsEmployee = this.employeeIdWiseEmployeesMap.get(employeeId.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("employeeId","Invalid employee Id.: "+employeeId);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
char gender=dsEmployee.getGender();
employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
public EmployeeInterface getEmployeeByPANNumber(String panNumber) throws BLException
{
EmployeeInterface dsEmployee = this.panNumberWiseEmployeesMap.get(panNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("panNumber","Invalid pan number: "+panNumber);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
char gender=dsEmployee.getGender();
employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
public EmployeeInterface getEmployeeByAadharCardNumber(String aadharCardNumber) throws BLException
{
EmployeeInterface dsEmployee = this.aadharCardNumberWiseEmployeesMap.get(aadharCardNumber.toUpperCase());
if(dsEmployee==null)
{
BLException blException=new BLException();
blException.addException("aadharCardNumber","Invalid aadhar card number: "+aadharCardNumber);
throw blException;
}
EmployeeInterface employee=new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
DesignationInterface dsDesignation=dsEmployee.getDesignation();
DesignationInterface designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
char gender=dsEmployee.getGender();
employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
return employee;
}
public boolean employeeIdExists(String employeeId)
{
return this.employeeIdWiseEmployeesMap.containsKey(employeeId.toUpperCase());
}
public boolean employeePANNumberExists(String panNumber)
{
return this.panNumberWiseEmployeesMap.containsKey(panNumber.toUpperCase());
}
public boolean employeeAadharCardNumberExists(String aadharCardNumber)
{
return this.aadharCardNumberWiseEmployeesMap.containsKey(aadharCardNumber.toUpperCase());
}
public int getEmployeeCount()
{
return this.employeesSet.size();
}
public Set<EmployeeInterface> getEmployeesByDesignationCode(int designationCode) throws BLException
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
if(designationManager.designationCodeExists(designationCode)==false)
{
BLException blException=new BLException();
blException.setGenericException("Invalid designation code "+designationCode);
}
Set<EmployeeInterface> employees=new TreeSet<>();
Set<EmployeeInterface> ets =this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null)
{
return employees;
}
EmployeeInterface employee;
DesignationInterface designation;
DesignationInterface dsDesignation;
for(EmployeeInterface dsEmployee: ets)
{
employee =new Employee();
employee.setEmployeeId(dsEmployee.getEmployeeId());
employee.setName(dsEmployee.getName());
dsDesignation=dsEmployee.getDesignation();
designation=new Designation();
designation.setCode(dsDesignation.getCode());
designation.setTitle(dsDesignation.getTitle());
employee.setDesignation(designation);
employee.setDateOfBirth((Date)dsEmployee.getDateOfBirth().clone());
char gender=dsEmployee.getGender();
employee.setGender((gender=='M')?GENDER.MALE:GENDER.FEMALE);
employee.setIsIndian(dsEmployee.getIsIndian());
employee.setBasicSalary(dsEmployee.getBasicSalary());
employee.setPANNumber(dsEmployee.getPANNumber());
employee.setAadharCardNumber(dsEmployee.getAadharCardNumber());
employees.add(employee);
}
return employees;
}
public int getEmployeeCountByDesignationCode(int designationCode) throws BLException
{
Set<EmployeeInterface> ets=this.designationCodeWiseEmployeesMap.get(designationCode);
if(ets==null) return 0;
return ets.size();
}
public boolean designationAlloted(int designationCode) throws BLException
{
return this.designationCodeWiseEmployeesMap.containsKey(designationCode);
}
}