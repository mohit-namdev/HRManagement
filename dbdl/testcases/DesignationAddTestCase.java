import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class DesignationAddTestCase
{
public static void main(String gg[])
{
String title=gg[0];
try
{
DesignationDTOInterface designationDTO= new DesignationDTO();
DesignationDAOInterface designationDAO= new DesignationDAO();
designationDTO.setTitle(title);
designationDAO.add(designationDTO);
System.out.println("designation: "+designationDTO.getTitle()+" has added and given a code: "+designationDTO.getCode());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
} 
}
}
