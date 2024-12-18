import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeManagerGetEmployeesByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeManager employeeManager=EmployeeManager.getEmployeeManager();
Set<EmployeeInterface> employees;
employees=employeeManager.getEmployeesByDesignationCode(designationCode);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
for(EmployeeInterface employee: employees)
{
System.out.println("EmployeeId: "+employee.getEmployeeId());
System.out.println("Name: "+employee.getName());
System.out.println("Designation code "+employee.getDesignation().getCode());
System.out.println("Date of birth: "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender: "+employee.getGender());
System.out.println("Is indian: "+employee.getIsIndian());
System.out.println("Basic salary: "+employee.getBasicSalary().toPlainString());
System.out.println("Pan number: "+employee.getPANNumber());
System.out.println("Aadhar card number: "+employee.getAadharCardNumber());
}
}catch(BLException blException)
{
if(blException.hasGenericException())System.out.println(blException.getGenericException());
List<String> properties= blException.getProperties();
for(String property: properties)
{
System.out.println(blException.getException(property));
}
}
}
}