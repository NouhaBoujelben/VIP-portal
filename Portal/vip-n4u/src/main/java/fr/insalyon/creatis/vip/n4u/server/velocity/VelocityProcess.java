/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.insalyon.creatis.vip.n4u.server.velocity;

import fr.insalyon.creatis.vip.n4u.client.rpc.N4uException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Nouha boujelben
 */
public interface VelocityProcess {

    public void gassFile(Map<Integer,Map> listInput, ArrayList listOutput, String applicationName, String wrapperScriptPath, String applicationLocation, String dir, String date, String sandbox, String environementFile) throws VelocityException;

    public void wrapperScriptFile(Map<Integer,Map> listInput, ArrayList listOutput, String applicationName, String scriptFile, String applicationLocation, String environementFile, String dir, String date) throws VelocityException;

    public String gwendiaFile(Map<Integer,Map> listInput, ArrayList listOutput, String applicationName, String description, String applicationLocation, String dir, String date) throws VelocityException;
}