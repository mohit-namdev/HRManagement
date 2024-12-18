import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeGetAllTestCase
{
public  static void main(String gg[])
{
try
{
EmployeeDAOInterface employeeDAO=new EmployeeDAO();
Set<EmployeeDTOInterface> employees=employeeDAO.getAll();
if(employees==null)
{
System.out.println("no elements present in set");
return;
}
SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
employees.forEach((employee)->{
System.out.println("employee ID: "+employee.getEmployeeId());
System.out.println("name: "+employee.getName());
System.out.println("Designation code: "+employee.getDesignationCode());
System.out.println("Date of birth: "+simpleDateFormat.format(employee.getDateOfBirth()));
System.out.println("Gender: "+employee.getGender());
System.out.println("Is indian: "+employee.getIsIndian());
System.out.println("Basic salary: "+employee.getBasicSalary().toPlainString());
System.out.println("Pan number: "+employee.getPANNumber());
System.out.println("Aadhar card number: "+employee.getAadharCardNumber());
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}