import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.text.*;
import java.util.*;
class EmployeeAddTestCase
{
public  static void main(String gg[])
{
String name=gg[0];
int designationCode=Integer.parseInt(gg[1]);
SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth=null;
try
{
dateOfBirth= simpleDateFormat.parse(gg[2]);
}catch(ParseException parseException)
{
//do nothing
}
BigDecimal basicSalary= new BigDecimal(gg[3]);
char gender=gg[4].charAt(0);
boolean isIndian=Boolean.parseBoolean(gg[5]);
String panNumber=gg[6];
String aadharCardNumber=gg[7];
try
{
EmployeeDTOInterface employeeDTO =new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
GENDER Gender=null;
if(gender=='M')Gender=GENDER.MALE;
if(gender=='F')Gender=GENDER.FEMALE;
employeeDTO.setGender(Gender);
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO= new EmployeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("Employee added with Employee ID: "+employeeDTO.getEmployeeId());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}