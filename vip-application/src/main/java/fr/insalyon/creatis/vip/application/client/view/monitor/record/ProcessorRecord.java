/* Copyright CNRS-CREATIS
 *
 * Rafael Silva
 * rafael.silva@creatis.insa-lyon.fr
 * http://www.rafaelsilva.com
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
package fr.insalyon.creatis.vip.application.client.view.monitor.record;

import com.smartgwt.client.widgets.grid.ListGridRecord;
import fr.insalyon.creatis.vip.application.client.ApplicationConstants.ProcessorStatus;

/**
 *
 * @author Rafael Silva
 */
public class ProcessorRecord extends ListGridRecord {

    private final String ATT_NAME = "name";
    private final String ATT_STATUS = "status";
    private final String ATT_COMPLETED = "completed";
    private final String ATT_QUEUED = "queued";
    private final String ATT_FAILED = "failed";
    
    public ProcessorRecord() {
    }

    public ProcessorRecord(String name, ProcessorStatus status,
            int completed, int queued, int failed) {

        setAttribute(ATT_NAME, name);
        setAttribute(ATT_STATUS, status.name());
        setAttribute(ATT_COMPLETED, completed);
        setAttribute(ATT_QUEUED, queued);
        setAttribute(ATT_FAILED, failed);
    }

    public String getName() {
        return getAttributeAsString(ATT_NAME);
    }

    public ProcessorStatus getStatus() {
        return ProcessorStatus.valueOf(getAttributeAsString(ATT_STATUS));
    }

    public int getCompleted() {
        return getAttributeAsInt(ATT_COMPLETED);
    }

    public int getQueued() {
        return getAttributeAsInt(ATT_QUEUED);
    }

    public int getFailed() {
        return getAttributeAsInt(ATT_FAILED);
    }
}