package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseDesignationsMap;
private Map<String,DesignationInterface> titleWiseDesignationsMap;
private Set<DesignationInterface> designationsSet;
private static DesignationManager designationManager=null;
private DesignationManager() throws BLException
{
populateDataStructures();
}
public void populateDataStructures() throws BLException
{
this.codeWiseDesignationsMap= new HashMap<>();
this.titleWiseDesignationsMap=new HashMap<>();
this.designationsSet=new TreeSet<>();
try
{
Set<DesignationDTOInterface> dlDesignations;
DesignationDAOInterface designationDAO=new DesignationDAO();
dlDesignations=designationDAO.getAll();
DesignationInterface designation;
for(DesignationDTOInterface dlDesignation:dlDesignations)
{
designation=new Designation();
designation.setCode(dlDesignation.getCode());
designation.setTitle(dlDesignation.getTitle());
this.codeWiseDesignationsMap.put(new Integer(designation.getCode()),designation);
this.titleWiseDesignationsMap.put(designation.getTitle().toUpperCase(),designation);
this.designationsSet.add(designation);
}
}catch(DAOException daoException)
{
BLException blException =new BLException();
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public static DesignationManager getDesignationManager() throws BLException
{
if(designationManager==null) designationManager=new DesignationManager();
return designationManager;
}
public void addDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code!=0)
{
blException.addException("code","code should be zero");
}
if(title==null)
{
blException.addException("title","title required");
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","title required");
}
}
if(title.length()>0)
{
if(this.titleWiseDesignationsMap.containsKey(title.toUpperCase()))
{
blException.addException("title","Designation: "+title+" exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
DesignationDAOInterface designationDAO =new DesignationDAO();
designationDAO.add(designationDTO);
code=designationDTO.getCode();
designation.setCode(code);
DesignationInterface dsDesignation;
dsDesignation=new Designation();
dsDesignation.setCode(code);
dsDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(new Integer(code),dsDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void updateDesignation(DesignationInterface designation) throws BLException
{
BLException blException=new BLException();
if(designation==null)
{
blException.setGenericException("designation required");
throw blException;
}
int code=designation.getCode();
String title=designation.getTitle();
if(code<=0)
{
blException.addException("code","code should be zero");
}
if(code>0)
{
if(codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","code: "+code+" not exists");
throw blException;
}
}
if(title==null)
{
blException.addException("title","title required");
title="";
}
else
{
title=title.trim();
if(title.length()==0)
{
blException.addException("title","title required");
}
}
if(title.length()>0)
{
DesignationInterface d;
d=this.titleWiseDesignationsMap.get(title.toUpperCase());
if(d!=null && d.getCode()!=code)
{
blException.addException("title","Designation: "+title+" exists");
}
}
if(blException.hasExceptions())
{
throw blException;
}
try
{
DesignationInterface dsDesignation=this.codeWiseDesignationsMap.get(code);
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
new DesignationDAO().update(designationDTO);
//remove old data from data structure
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);
//updating new data to data structure
dsDesignation.setTitle(title);
this.codeWiseDesignationsMap.put(code,dsDesignation);
this.titleWiseDesignationsMap.put(title.toUpperCase(),dsDesignation);
this.designationsSet.add(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public void removeDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)
{
blException.addException("code","code should be zero");
throw blException;
}
if(code>0)
{
if(codeWiseDesignationsMap.containsKey(code)==false)
{
blException.addException("code","code: "+code+" not exists");
throw blException;
}
}  
try
{
DesignationInterface dsDesignation=codeWiseDesignationsMap.get(code);
new DesignationDAO().delete(code);
//remove old data from data structure
this.codeWiseDesignationsMap.remove(code);
this.titleWiseDesignationsMap.remove(dsDesignation.getTitle().toUpperCase());
this.designationsSet.remove(dsDesignation);
}catch(DAOException daoException)
{
blException.setGenericException(daoException.getMessage());
throw blException;
}
}
public Set<DesignationInterface> getDesignations()
{
Set<DesignationInterface> designations=new TreeSet<>();
designationsSet.forEach((designation)->{
DesignationInterface d= new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
designations.add(d);
});
return designations;
}
public DesignationInterface getDesignationByCode(int code) throws BLException
{
DesignationInterface designation= this.codeWiseDesignationsMap.get(code);
if(designation==null)
{
BLException blException=new BLException();
blException.addException("code","Invalid code:"+code);
throw blException;
}
DesignationInterface d= new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}

//for adding in data structures
DesignationInterface getDSDesignationByCode(int code)
{
DesignationInterface designation= this.codeWiseDesignationsMap.get(code);
return designation;
}

public DesignationInterface getDesignationByTitle(String title) throws BLException
{
DesignationInterface designation= this.titleWiseDesignationsMap.get(title.toUpperCase());
if(designation==null)
{
BLException blException=new BLException();
blException.addException("title","Invalid Designation:"+title);
throw blException;
}
DesignationInterface d= new Designation();
d.setCode(designation.getCode());
d.setTitle(designation.getTitle());
return d;
}


public boolean designationCodeExists(int code)
{
return this.codeWiseDesignationsMap.containsKey(code);
}
public boolean designationTitleExists(String title)
{
return this.titleWiseDesignationsMap.containsKey(title.toUpperCase());
}
public int getDesignationCount()
{
return this.designationsSet.size();
}
}