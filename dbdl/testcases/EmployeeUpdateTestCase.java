import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeUpdateTestCase
{
public  static void main(String gg[])
{
String employeeId=gg[0];
String name=gg[1];
int designationCode=Integer.parseInt(gg[2]);
java.text.SimpleDateFormat simpleDateFormat= new java.text.SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth= simpleDateFormat.parse(gg[3]);
}catch(ParseException parseException)
{
//do nothing
}
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
BigDecimal basicSalary= new BigDecimal(gg[6]);
String panNumber=gg[7];
String aadharCardNumber=gg[8];
try
{
EmployeeDTOInterface employeeDTO =new EmployeeDTO();
employeeDTO.setEmployeeId(employeeId);
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
GENDER Gender;
if(gender=='M')Gender=GENDER.MALE;
else Gender=GENDER.FEMALE;
employeeDTO.setGender(Gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO = new EmployeeDAO();
employeeDAO.update(employeeDTO);
System.out.println("employee with employee id: "+employeeId+" is updated");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}