import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
public class DesignationDeleteTestCase
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO= new DesignationDAO();
designationDAO.delete(code);
System.out.println("designation with code: "+code+" has been deleted");
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
} 
}
}
