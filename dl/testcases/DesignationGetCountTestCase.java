import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class DesignationGetCountTestCase
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO= new DesignationDAO();
int count=designationDAO.getCount();
System.out.println("no. of elements are "+count);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
} 
}
}
