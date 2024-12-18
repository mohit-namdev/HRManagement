import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
class EmployeeManagerUpdateTestCase
{
public  static void main(String gg[])
{
try
{
String employeeId="A10000001";
String name="mohit namdev";
DesignationInterface designation=new Designation();
designation.setCode(1);
Date dateOfBirth=new Date("04/07/2002");
boolean isIndian=true;
BigDecimal basicSalary =new BigDecimal("100000.00");
String panNumber="A234567";
String aadharCardNumber="2341234";
EmployeeInterface employee =new Employee();
employee.setEmployeeId(employeeId);
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager =EmployeeManager.getEmployeeManager();
employeeManager.updateEmployee(employee);
System.out.println("Employee updated");
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties=blException.getProperties();
for(String property: properties)
{
System.out.println(blException.getException(property));
}
}
}
}