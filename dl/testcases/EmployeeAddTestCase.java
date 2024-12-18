import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.math.*;
import java.util.*;
class EmployeeAddTestCase
{
public  static void main(String gg[])
{
try
{
EmployeeDTOInterface employeeDTO1 =new EmployeeDTO();
employeeDTO1.setName("Mohit");
employeeDTO1.setDesignationCode(4);
Date date=new Date("07/14/2002");
employeeDTO1.setDateOfBirth(date);
char gender='M';
if(gender=='M')
{
employeeDTO1.setGender(GENDER.MALE);
}
if(gender=='F')
{
employeeDTO1.setGender(GENDER.FEMALE);
}
employeeDTO1.setIsIndian(true);
BigDecimal b=new BigDecimal("80000.00");
employeeDTO1.setBasicSalary(b);
employeeDTO1.setPANNumber("AR1234585");
employeeDTO1.setAadharCardNumber("123412345678");
EmployeeDAOInterface employeeDAO= new EmployeeDAO();
employeeDAO.add(employeeDTO1);
System.out.println("Employee added with Employee ID: "+employeeDTO1.getEmployeeId());
EmployeeDTOInterface employeeDTO2 =new EmployeeDTO();
employeeDTO2.setName("Mohan");
employeeDTO2.setDesignationCode(6);
Date date2=new Date("03/12/2003");
employeeDTO2.setDateOfBirth(date2);
gender='M';
if(gender=='M')
{
employeeDTO2.setGender(GENDER.MALE);
}
if(gender=='F')
{
employeeDTO2.setGender(GENDER.FEMALE);
}
employeeDTO2.setIsIndian(true);
BigDecimal b2=new BigDecimal("80000.00");
employeeDTO2.setBasicSalary(b2);
employeeDTO2.setPANNumber("AR1234584");
employeeDTO2.setAadharCardNumber("123412345673");
employeeDAO.add(employeeDTO2);
System.out.println("Employee added with Employee ID: "+employeeDTO2.getEmployeeId());
EmployeeDTOInterface employeeDTO3 =new EmployeeDTO();
employeeDTO3.setName("Mohini");
employeeDTO3.setDesignationCode(6);
Date date3=new Date("03/12/2003");
employeeDTO3.setDateOfBirth(date2);
gender='F';
if(gender=='M')
{
employeeDTO3.setGender(GENDER.MALE);
}
if(gender=='F')
{
employeeDTO3.setGender(GENDER.FEMALE);
}
employeeDTO3.setIsIndian(false);
BigDecimal b3=new BigDecimal("80000.00");
employeeDTO3.setBasicSalary(b3);
employeeDTO3.setPANNumber("ARe1234584");
employeeDTO3.setAadharCardNumber("1234123456734");
employeeDAO.add(employeeDTO3);
System.out.println("Employee added with Employee ID: "+employeeDTO3.getEmployeeId());

}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}