/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ViewScoped
public class LogPagKontroler implements Serializable {

String data;

String id;

private int offset = 0 , preOffset=0;
private int pageSize = 30;
// Path for Glassfish directory with log files
String path = utility.putZaLog;

// Constructor
public  LogPagKontroler(){
// Get the ID value
try {
    
    // We load the first page initially!
    this.actionNextPage();
} catch (Exception e) {
    this.id = null;
}
}

public String actionClearOffset() {
this.offset = 0; 
this.actionNextPage();
return null;
}

public String actionNextPage() {
StringBuilder page = new StringBuilder();


for (int i = 0; i < this.pageSize; i++) {
    String line = this.readLine(this.offset);
    if (line == null) {
    break;
    }
    //System.out.println(this.offset);
    this.offset += line.length() + 2;
    page.append(line).append(System.getProperty("line.separator"));
}
this.data = page.toString();
return null;
}

public String getData() {
return this.data;
}

public int getPageSize() {
return this.pageSize;
}

public String readLine(long offset) {
String strLine = null;
DataInputStream in = null;
try {
    // Open the file that is the first
    // command line parameter
    FileInputStream fstream = new FileInputStream(new File(this.path));
    // Get the object of DataInputStream
    in = new DataInputStream(fstream);
    BufferedReader br = new BufferedReader(new InputStreamReader(in));

    br.skip(offset);

    strLine = br.readLine();
    // Close the input stream
} catch (Exception e) {// Catch exception if any
    System.err.println("Error: " + e.getMessage());
} finally {
    try {
    in.close();
    } catch (IOException e) {
    e.printStackTrace();
    }
}
return strLine;
}

public void setData(String data) {

}
}