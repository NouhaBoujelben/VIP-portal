/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.n4u.client.rpc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nouha Boujelben
 */
public interface FileProcessService extends RemoteService {

    public static final String SERVICE_URI = "/fileProcessService";

    public static class Util {

        public static FileProcessServiceAsync getInstance() {

            FileProcessServiceAsync instance = (FileProcessServiceAsync) GWT.create(FileProcessService.class);
            ServiceDefTarget target = (ServiceDefTarget) instance;
            target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
            return instance;
        }
    }
    
     List<List<String>> fileTraitement(String param1);
     int[] fileJobTraitement(String jobFile,String expressFile) ;
     void generateScriptFile(ArrayList listInput,ArrayList listOutput,String wrapperScriptPath,String scriptFile,String applicationName,String applicationLocation,String description) ;
    String generateGwendiaFile(ArrayList listInput,ArrayList listOutput,String wrapperScriptPath,String scriptFile,String applicationName,String applicationLocation,String description) ;
    void generateGaswFile(ArrayList listInput,ArrayList listOutput,String wrapperScriptPath,String scriptFile,String applicationName,String applicationLocation,String description) ;
   
    String getApplicationClasse() ;
}