import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
class EmployeeManagerAddTestCase
{
public  static void main(String gg[])
{
try
{
String name="ram";
DesignationInterface designation=new Designation();
designation.setCode(4);
Date dateOfBirth=new Date("24/011/2000");
boolean isIndian=true;
BigDecimal basicSalary =new BigDecimal("80000.00");
String panNumber="A2398";
String aadharCardNumber="23498";
EmployeeInterface employee =new Employee();
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager =EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
System.out.printf("Employee added with employee Id. %s\n",employee.getEmployeeId());
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