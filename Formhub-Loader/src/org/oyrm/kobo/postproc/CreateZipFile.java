package org.oyrm.kobo.postproc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CreateZipFile {

	/* public static void main(String[] a) throws Exception {
		    zipFolder("/Users/Arindam/Desktop/TestFolder/Test", "/Users/Arindam/Desktop/Test.zip");
		  }
     /*
     * Zip function zip all files and folders
     */
   public void zipFolder(String srcFolder, String destZipFile) throws Exception {
	    ZipOutputStream zip = null;
	    FileOutputStream fileWriter = null;
	    //destZipFile = destZipFile.concat("/Test.zip");

	    fileWriter = new FileOutputStream(destZipFile);
	    zip = new ZipOutputStream(fileWriter);
	    addFolderToZip("", srcFolder, zip);
	    
	    File directory = new File(srcFolder);
	    if(!directory.exists()){
        }else{
        	try{
               delete(directory);
           }catch(IOException e){
               e.printStackTrace();
               System.exit(0);
           }
        }
	    zip.flush();
	    zip.close();
	  }

	  static private void addFileToZip(String path, String srcFile, ZipOutputStream zip)
	      throws Exception {

	    File folder = new File(srcFile);
	    if (folder.isDirectory()) {
	      addFolderToZip(path, srcFile, zip);
	    } else {
	      byte[] buf = new byte[1024];
	      int len;
	      FileInputStream in = new FileInputStream(srcFile);
	      zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
	      while ((len = in.read(buf)) > 0) {
	        zip.write(buf, 0, len);
	      }
	    }
	  }

	  static private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip)
	      throws Exception {
	    File folder = new File(srcFolder);

	    for (String fileName : folder.list()) {
	      if (path.equals("")) {
	        addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
	      } else {
	        addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
	      }
	    }
	  }
	  
	  public static void delete(File file)
		    	throws IOException{
		 
		    	if(file.isDirectory()){
		 
		    		if(file.list().length==0){
		    		   file.delete();
		 
		    		}else{
		 
		    		   //list all the directory contents
		        	   String files[] = file.list();
		 
		        	   for (String temp : files) {
		        	      File fileDelete = new File(file, temp);
		        	     delete(fileDelete);
		        	   }
		 
		        	   if(file.list().length==0){
		           	     file.delete();
		        	     System.out.println("Directory is deleted : " 
		                                                  + file.getAbsolutePath());
		        	   }
		    		}
		 
		    	}else{
		    		file.delete();
		    		System.out.println("File is deleted : " + file.getAbsolutePath());
		    	}
		    }
	}
   
   