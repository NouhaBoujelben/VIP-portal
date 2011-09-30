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
package fr.insalyon.creatis.vip.application.client.view.monitor;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.toolbar.ToolStrip;
import com.smartgwt.client.widgets.toolbar.ToolStripButton;
import fr.insalyon.creatis.vip.application.client.ApplicationConstants;
import fr.insalyon.creatis.vip.application.client.ApplicationConstants.SimulationStatus;
import fr.insalyon.creatis.vip.application.client.rpc.WorkflowService;
import fr.insalyon.creatis.vip.application.client.rpc.WorkflowServiceAsync;
import fr.insalyon.creatis.vip.application.client.view.monitor.record.SimulationRecord;
import fr.insalyon.creatis.vip.core.client.CoreModule;
import fr.insalyon.creatis.vip.core.client.view.CoreConstants;
import fr.insalyon.creatis.vip.core.client.view.ModalWindow;
import fr.insalyon.creatis.vip.core.client.view.layout.Layout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael Silva
 */
public class SimulationsToolStrip extends ToolStrip {

    private ModalWindow modal;

    public SimulationsToolStrip(ModalWindow modal) {

        this.modal = modal;
        this.setWidth100();

        ToolStripButton refreshButton = new ToolStripButton();
        refreshButton.setIcon(CoreConstants.ICON_REFRESH);
        refreshButton.setTitle("Refresh");
        refreshButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                getSimulationsTab().loadData();
            }
        });
        this.addButton(refreshButton);

        ToolStripButton searchButton = new ToolStripButton();
        searchButton.setIcon(ApplicationConstants.ICON_SEARCH);
        searchButton.setTitle("Search");
        searchButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                getSimulationsTab().expandSearchSection();
            }
        });
        this.addButton(searchButton);

        ToolStripButton killButton = new ToolStripButton();
        killButton.setIcon(ApplicationConstants.ICON_KILL);
        killButton.setTitle("Kill Simulations");
        killButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                SC.confirm("Do you really want to kill the selected running simulations?", new BooleanCallback() {

                    public void execute(Boolean value) {
                        if (value != null && value) {
                            killSimulations();
                        }
                    }
                });
            }
        });
        this.addButton(killButton);

        ToolStripButton cleanButton = new ToolStripButton();
        cleanButton.setIcon(ApplicationConstants.ICON_CLEAN);
        cleanButton.setTitle("Clean Simulations");
        cleanButton.addClickHandler(new ClickHandler() {

            public void onClick(ClickEvent event) {
                SC.confirm("Do you really want to clean the selected completed/killed simulations?", new BooleanCallback() {

                    public void execute(Boolean value) {
                        if (value != null && value) {
                            cleanSimulations();
                        }
                    }
                });
            }
        });
        this.addButton(cleanButton);

        if (CoreModule.user.isSystemAdministrator()) {
            
            ToolStripButton purgeButton = new ToolStripButton();
            purgeButton.setIcon(CoreConstants.ICON_CLEAR);
            purgeButton.setTitle("Purge Simulations");
            purgeButton.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event) {
                    SC.confirm("Do you really want to purge the selected cleaned simulations?", new BooleanCallback() {

                        public void execute(Boolean value) {
                            if (value != null && value) {
                                purgeSimulations();
                            }
                        }
                    });
                }
            });
            this.addButton(purgeButton);

            ToolStripButton statsButton = new ToolStripButton();
            statsButton.setIcon(ApplicationConstants.ICON_CHART);
            statsButton.setTitle("Performance Statistics");
            statsButton.addClickHandler(new ClickHandler() {

                public void onClick(ClickEvent event) {
                    Layout.getInstance().addTab(new StatsTab());
                    StatsTab statsTab = (StatsTab) Layout.getInstance().getTab(ApplicationConstants.TAB_STATS);
                    statsTab.setSimulationsList(getSimulationsTab().getSimulationsList());
                }
            });

            this.addSeparator();
            this.addButton(statsButton);
        }
    }

    /**
     * Sends a request to kill the selected running simulations
     * 
     */
    private void killSimulations() {

        ListGridRecord[] records = getSimulationsTab().getGridSelection();
        List<String> simulationIDs = new ArrayList<String>();

        for (ListGridRecord record : records) {
            SimulationRecord data = (SimulationRecord) record;
            SimulationStatus status = SimulationStatus.valueOf(data.getStatus());
            
            if (status == SimulationStatus.Running) {
                simulationIDs.add(data.getSimulationId());
            }
        }

        WorkflowServiceAsync service = WorkflowService.Util.getInstance();
        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                modal.hide();
                SC.warn("Unable to kill simulations:<br />" + caught.getMessage());
            }

            public void onSuccess(Void result) {
                modal.hide();
                getSimulationsTab().loadData();
            }
        };
        service.killSimulations(simulationIDs, callback);
        modal.show("Sending killing signal to selected simulations...", true);
    }

    /**
     * Sends a request to clean the selected completed/killed simulations
     * 
     */
    private void cleanSimulations() {

        ListGridRecord[] records = getSimulationsTab().getGridSelection();
        List<String> simulationIDs = new ArrayList<String>();

        for (ListGridRecord record : records) {
            SimulationRecord data = (SimulationRecord) record;
            SimulationStatus status = SimulationStatus.valueOf(data.getStatus());
            
            if (status == SimulationStatus.Completed 
                    || status == SimulationStatus.Killed) {

                simulationIDs.add(data.getSimulationId());
            }
        }

        WorkflowServiceAsync service = WorkflowService.Util.getInstance();
        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                modal.hide();
                SC.warn("Unable to clean simulations:<br />" + caught.getMessage());
            }

            public void onSuccess(Void result) {
                modal.hide();
                getSimulationsTab().loadData();
            }
        };
        service.cleanSimulations(simulationIDs, callback);
        modal.show("Cleaning selected simulations...", true);
    }

    /**
     * Sends a request to purge the selected cleaned simulations
     * 
     */
    private void purgeSimulations() {

        ListGridRecord[] records = getSimulationsTab().getGridSelection();
        List<String> simulationIDs = new ArrayList<String>();

        for (ListGridRecord record : records) {
            SimulationRecord data = (SimulationRecord) record;
            SimulationStatus status = SimulationStatus.valueOf(data.getStatus());
            
            if (status == SimulationStatus.Cleaned) {
                simulationIDs.add(data.getSimulationId());
            }
        }

        WorkflowServiceAsync service = WorkflowService.Util.getInstance();
        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                modal.hide();
                SC.warn("Unable to purge simulations:<br />" + caught.getMessage());
            }

            public void onSuccess(Void result) {
                modal.hide();
                getSimulationsTab().loadData();
            }
        };
        service.purgeSimulations(simulationIDs, callback);
        modal.show("Purging selected simulations...", true);
    }
    
    private SimulationsTab getSimulationsTab() {
        return (SimulationsTab) Layout.getInstance().getTab(ApplicationConstants.TAB_MONITOR);
    }
}
