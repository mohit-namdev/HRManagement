import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeDeleteTestCase
{
public  static void main(String gg[])
{
String employeeId=gg[0];
try
{
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
employeeDAO.delete(employeeId);
System.out.println("employee with employee id: "+employeeId+" is deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}