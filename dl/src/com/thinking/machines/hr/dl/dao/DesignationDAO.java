package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.util.*;
import java.io.*;
public class DesignationDAO implements DesignationDAOInterface
{
private static final String FILE_NAME="Designation.data";
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Designation is null");
String title= designationDTO.getTitle();
if(title==null) throw new DAOException("Title is null");
title=title.trim();
if(title.length()==0)throw new DAOException("Length of title is zero");
try
{
File file=new File(FILE_NAME);
RandomAccessFile randomAccessFile =new RandomAccessFile(file,"rw");
int lastGeneratedCode=0;
int recordCount=0;
String lastGeneratedCodeString="";
String recordCountString="";
if(randomAccessFile.length()==0)
{
lastGeneratedCodeString="0";
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString="0";
while(recordCountString.length()<10) recordCountString+=" ";
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
}
else
{
lastGeneratedCodeString=randomAccessFile.readLine().trim();
lastGeneratedCode=Integer.parseInt(lastGeneratedCodeString);
recordCountString=randomAccessFile.readLine().trim();
recordCount=Integer.parseInt(recordCountString);
}
int fCode=0;
String fTitle="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title)) 
{
randomAccessFile.close();
throw new DAOException("Designation: "+title+" exists.");
}
}
designationDTO.setCode(lastGeneratedCode+1);
randomAccessFile.writeBytes(String.valueOf(designationDTO.getCode()));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(title);
randomAccessFile.writeBytes("\n");
lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString=String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString+=" ";
recordCountString=String.valueOf(recordCount);
while(recordCountString.length()<10) recordCountString+=" ";
randomAccessFile.seek(0);
randomAccessFile.writeBytes(lastGeneratedCodeString);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(recordCountString);
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO==null) throw new DAOException("Invalid designation");
int code=designationDTO.getCode();
if(code<=0)throw new DAOException("code: "+code+" is Invalid");
String title=designationDTO.getTitle();
if(title==null)throw new DAOException("title is null");
title=title.trim();
if(title.length()==0)throw new DAOException("length of title is null");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("file not found");
RandomAccessFile randomAccessFile;
randomAccessFile= new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("file is empty");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
String fTitle;
int fCode;
boolean codeFound=false;
boolean titleFound=false;
int codeAgainstTitle=0;
int x;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(codeFound==false && fCode==code) codeFound=true;
if(titleFound==false && fTitle.equalsIgnoreCase(title))
{
titleFound=true;
codeAgainstTitle=fCode;
}
if(codeFound && titleFound) break;
}
if(codeFound==false) 
{
randomAccessFile.close();
throw new DAOException("code not found");
}
if(titleFound==true && codeAgainstTitle!=code)
{
randomAccessFile.close();
throw new DAOException("title already exists with another code: "+codeAgainstTitle);
}
randomAccessFile.seek(0);
File tmpFile= new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
tmpRandomAccessFile.writeBytes(code+"\n");
tmpRandomAccessFile.writeBytes(title+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(fCode+"\n");
tmpRandomAccessFile.writeBytes(fTitle+"\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(int code) throws DAOException
{
if(code<=0)throw new DAOException("code: "+code+" is Invalid");
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("file not found");
RandomAccessFile randomAccessFile;
randomAccessFile= new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("file is empty");
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
boolean codeFound=false;
String fTitle="";
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code) 
{
codeFound=true;
break;
}
}
if(codeFound==false) 
{
randomAccessFile.close();
throw new DAOException("code not found");
}
if(new EmployeeDAO().isDesignationAlloted(code))
{
randomAccessFile.close();
throw new DAOException("employee exists with designation: "+fTitle);
}
randomAccessFile.seek(0);
File tmpFile= new File("tmp.tmp");
if(tmpFile.exists())tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode!=code)
{
tmpRandomAccessFile.writeBytes(fCode+"\n");
tmpRandomAccessFile.writeBytes(fTitle+"\n");
}
}
tmpRandomAccessFile.seek(0);
randomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.seek(0);
randomAccessFile.readLine();
String recordCountString=randomAccessFile.readLine().trim();
int recordCount=Integer.parseInt(recordCountString);
recordCount--;
recordCountString=String.format("%-10d",recordCount);
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.writeBytes(recordCountString+"\n");
randomAccessFile.close();
tmpRandomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public Set<DesignationDTOInterface> getAll() throws DAOException
{
Set<DesignationDTOInterface> designations=new TreeSet<>();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return designations;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return designations;
}
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationDTOInterface designationDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(randomAccessFile.readLine()));
designationDTO.setTitle(randomAccessFile.readLine());
designations.add(designationDTO);
}
randomAccessFile.close();
return designations;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}


public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code<=0) throw new DAOException("Invalid code: "+code);
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid code: "+code);
RandomAccessFile randomAccessFile= new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid code: "+code);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode==code)
{
randomAccessFile.close();
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
throw new DAOException("Invalid code: "+code);
}

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title==null || title.trim().length()==0) throw new DAOException("Invalid title: "+title);
title=title.trim();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) throw new DAOException("Invalid title: "+title);
RandomAccessFile randomAccessFile= new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
throw new DAOException("Invalid title: "+title);
}
int fCode;
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
DesignationDTOInterface designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
throw new DAOException("Invalid title: "+title);
}

public boolean codeExists(int code) throws DAOException
{
if(code<=0) return false;
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile= new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
return false;
}
int fCode;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
if(fCode==code)
{
randomAccessFile.close();
return true;
}
randomAccessFile.readLine();
}
randomAccessFile.close();
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
return false;
}

public boolean titleExists(String title) throws DAOException
{
if(title==null || title.trim().length()==0) return false;
title=title.trim();
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return false;
RandomAccessFile randomAccessFile= new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0) 
{
randomAccessFile.close();
return false;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
if(recordCount==0) 
{
randomAccessFile.close();
return false;
}
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(title))
{
randomAccessFile.close();
return true;
}
}
}catch(IOException ioException)
{
System.out.println(ioException.getMessage());
}
return false;
}
public int getCount() throws DAOException
{
try
{
File file=new File(FILE_NAME);
if(file.exists()==false) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int recordCount=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return recordCount;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
}