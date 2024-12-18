import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeIsDesignationCodeAllotedTestCase
{
public  static void main(String gg[])
{
int designationCode=Integer.parseInt(gg[0]);
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
boolean isDesignationCodeAlloted=employeeDAO.isDesignationAlloted(designationCode);
System.out.println("Designation code "+designationCode+" is alloted: "+isDesignationCodeAlloted);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}