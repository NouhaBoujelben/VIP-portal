/* Copyright CNRS-CREATIS
 *
 * Rafael Silva
 * rafael.silva@creatis.insa-lyon.fr
 * http://www.creatis.insa-lyon.fr/~silva
 *
 * This software is a grid-enabled data-driven workflow manager and editor.
 *
 * This software is governed by the CeCILL  license under French law and
 * abiding by the rules of distribution of free software.  You can  use,
 * modify and/ or redistribute the software under the terms of the CeCILL
 * license as circulated by CEA, CNRS and INRIA at the following URL
 * "http://www.cecill.info".
 *
 * As a counterpart to the access to the source code and  rights to copy,
 * modify and redistribute granted by the license, users are provided only
 * with a limited warranty  and the software's author,  the holder of the
 * economic rights,  and the successive licensors  have only  limited
 * liability.
 *
 * In this respect, the user's attention is drawn to the risks associated
 * with loading,  using,  modifying and/or developing or reproducing the
 * software by the user in light of its specific status of free software,
 * that may mean  that it is complicated to manipulate,  and  that  also
 * therefore means  that it is reserved for developers  and  experienced
 * professionals having in-depth computer knowledge. Users are therefore
 * encouraged to load and test the software's suitability as regards their
 * requirements in conditions enabling the security of their systems and/or
 * data to be ensured and,  more generally, to use and operate it in the
 * same conditions as regards security.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL license and that you accept its terms.
 */
package fr.insalyon.creatis.vip.portal.client.view.application.monitor.record;

import com.smartgwt.client.widgets.grid.ListGridRecord;

/**
 *
 * @author Rafael Silva
 */
public class JobRecord extends ListGridRecord {

    public JobRecord() {
    }

    public JobRecord(String jobID, String status, String command, String fileName, int exitCode) {
        setJobID(jobID);
        setStatus(status);
        setCommand(command);
        setFileName(fileName);
        setExitCode(exitCode);
        setMinorStatus(status, exitCode);
    }

    public String getCommand() {
        return getAttributeAsString("command");
    }

    public void setCommand(String command) {
        setAttribute("command", command);
    }

    public String getExitCode() {
        return getAttributeAsString("exitCode");
    }

    public void setExitCode(int exitCode) {
        setAttribute("exitCode", exitCode);
    }

    public String getFileName() {
        return getAttributeAsString("fileName");
    }

    public void setFileName(String fileName) {
        setAttribute("fileName", fileName);
    }

    public String getJobID() {
        return getAttributeAsString("jobID");
    }

    public void setJobID(String id) {
        setAttribute("jobID", id);
    }

    public String getNodeName() {
        return getAttributeAsString("nodeName");
    }

    public void setNodeName(String nodeName) {
        setAttribute("nodeName", nodeName);
    }

    public String getSiteName() {
        return getAttributeAsString("siteName");
    }

    public void setSiteName(String siteName) {
        setAttribute("siteName", siteName);
    }

    public String getStatus() {
        return getAttributeAsString("status");
    }

    public void setStatus(String status) {
        setAttribute("status", status);
    }
    
    public String getMinorStatus() {
        return getAttributeAsString("minorStatus");
    }
    
    public void setMinorStatus(String status, int exitCode) {
        if (status.equals("COMPLETED") || status.equals("ERROR")) {
            switch (exitCode) {
                case 0:
                    setAttribute("minorStatus", "Execution Completed");
                    break;
                case 1:
                    setAttribute("minorStatus", "Inputs Download Failed");
                    break;
                case 2:
                    setAttribute("minorStatus", "Outputs Upload Failed");
                    break;
                case 6:
                    setAttribute("minorStatus", "Application Execution Failed");
                    break;
                case 7:
                    setAttribute("minorStatus", "Directories Creation Failed");
                    break;
                default:
                    setAttribute("minorStatus", "Retrieving Status");
            }
        }
    }
}
