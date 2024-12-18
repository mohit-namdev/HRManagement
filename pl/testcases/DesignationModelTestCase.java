import java.awt.*;
import javax.swing.*;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
class DesignationModelTestCase extends JFrame
{
private JTable table;
private JScrollPane jsp;
private DesignationModel designationModel;
private Container container;
DesignationModelTestCase()
{
designationModel =new DesignationModel();
table=new JTable(designationModel);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setLocation(100,200);
setSize(500,500);
setVisible(true);
}
public static void main(String []gg)
{
DesignationModelTestCase dmtc=new DesignationModelTestCase();
}
}