import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.util.*;
public class EmployeeManagerDesignationAllotedTestCase
{
public static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeManager employeeManager=EmployeeManager.getEmployeeManager();
System.out.println("designation "+ designationCode+" is alloted: "+employeeManager.designationAlloted(designationCode));
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