import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import java.util.*;
public class DesignationManagerAddTestCase
{
public static void main(String gg[])
{
DesignationInterface designation= new Designation();
designation.setTitle("liftman");
try
{
DesignationManagerInterface designationManager=DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("designation is added with designation code: "+designation.getCode());
}catch(BLException blException)
{
if(blException.hasGenericException())
{
System.out.println(blException.getGenericException());
}
List<String> properties=blException.getProperties();
for(String property:properties)
{
System.out.println(blException.getException(property));
}
}
}
}