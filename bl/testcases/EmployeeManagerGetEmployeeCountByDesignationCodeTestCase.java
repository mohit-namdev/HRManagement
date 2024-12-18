import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
import java.text.*;
public class EmployeeManagerGetEmployeeCountByDesignationCodeTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeManager employeeManager=EmployeeManager.getEmployeeManager();
int count=employeeManager.getEmployeeCountByDesignationCode(designationCode);
System.out.printf("no. of employees with designation code %d is %d\n",designationCode,count);
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