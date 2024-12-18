import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeAadharCardNumberExistsTestCase
{
public  static void main(String gg[])
{
String aadharCardNumber=gg[0];
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
System.out.println("Aadhar Card Number exists: "+employeeDAO.aadharCardNumberExists(aadharCardNumber));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}