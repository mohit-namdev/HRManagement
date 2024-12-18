import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeGetCountTestCase
{
public  static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
System.out.println("no. of employees are: "+employeeDAO.getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}