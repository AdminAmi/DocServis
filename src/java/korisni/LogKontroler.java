/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisni;

 import java.io.File;
    import java.io.FileInputStream;
    import java.io.IOException;
    import java.io.Serializable;
    import javax.annotation.PostConstruct;
    import javax.faces.bean.ViewScoped;
    import javax.faces.bean.ManagedBean;
    

    @ManagedBean
    @ViewScoped
    public class LogKontroler implements Serializable
    {

        
        private String fileData;

        // Constructor
        public LogKontroler()  {        
        }
        // Path for Glassfish directory with log files
        String path = utility.putZaLog;

        @PostConstruct
        public void redFile()
        {
            File file = new File(path);

            try (FileInputStream fis = new FileInputStream(file))
            {

                int content;
                while ((content = fis.read()) != -1)
                {
                    // convert to char and display it
                    setFileData(getFileData() + (char)content);
                }

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

       

    /**
     * @return the fileData
     */
    public String getFileData() {
        return fileData;
    }

    /**
     * @param fileData the fileData to set
     */
    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
    }
