import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeGetByPanNumberTestCase
{
public  static void main(String gg[])
{
String panNumber=gg[0];
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
EmployeeDTOInterface employeeDTO;
employeeDTO=employeeDAO.getByPANNumber(panNumber);
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
System.out.println("employee ID: "+employeeDTO.getEmployeeId());
System.out.println("name: "+employeeDTO.getName());
System.out.println("Designation code: "+employeeDTO.getDesignationCode());
System.out.println("Date of birth: "+simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender: "+employeeDTO.getGender());
System.out.println("Is indian: "+employeeDTO.getIsIndian());
System.out.println("Basic salary: "+employeeDTO.getBasicSalary().toPlainString());
System.out.println("Pan number: "+employeeDTO.getPANNumber());
System.out.println("Aadhar card number: "+employeeDTO.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}