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
package fr.insalyon.creatis.vip.application.client.view.monitor.menu;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.menu.MenuItem;
import com.smartgwt.client.widgets.menu.events.ClickHandler;
import com.smartgwt.client.widgets.menu.events.MenuItemClickEvent;
import fr.insalyon.creatis.vip.application.client.ApplicationConstants;
import fr.insalyon.creatis.vip.application.client.ApplicationConstants.SimulationStatus;
import fr.insalyon.creatis.vip.application.client.rpc.WorkflowService;
import fr.insalyon.creatis.vip.application.client.rpc.WorkflowServiceAsync;
import fr.insalyon.creatis.vip.application.client.view.monitor.SimulationTab;
import fr.insalyon.creatis.vip.application.client.view.monitor.SimulationsTab;
import fr.insalyon.creatis.vip.core.client.view.CoreConstants;
import fr.insalyon.creatis.vip.core.client.view.ModalWindow;
import fr.insalyon.creatis.vip.core.client.view.layout.Layout;

/**
 *
 * @author Rafael Silva
 */
public class SimulationsContextMenu extends Menu {

    private ModalWindow modal;
    private String simulationID;
    private String title;
    
    public SimulationsContextMenu(ModalWindow modal, final String simulationID,
            final String title, final SimulationStatus status) {
        
        this.modal = modal;
        this.simulationID = simulationID;
        this.title = title;

        this.setShowShadow(true);
        this.setShadowDepth(10);
        this.setWidth(90);

        MenuItem viewItem = new MenuItem("View Simulation");
        viewItem.setIcon(ApplicationConstants.ICON_SIMULATION_VIEW);
        viewItem.addClickHandler(new ClickHandler() {

            public void onClick(MenuItemClickEvent event) {
                Layout.getInstance().addTab(new SimulationTab(simulationID, 
                        title, status));
            }
        });

        MenuItem killItem = new MenuItem("Kill Simulation");
        killItem.setIcon(ApplicationConstants.ICON_KILL);
        killItem.addClickHandler(new ClickHandler() {

            public void onClick(MenuItemClickEvent event) {
                SC.confirm("Do you really want to kill this simulation ("
                        + title + ")?", new BooleanCallback() {

                    public void execute(Boolean value) {
                        if (value != null && value) {
                            killSimulation();
                        }
                    }
                });
            }
        });

        MenuItem cleanItem = new MenuItem("Clean Simulation");
        cleanItem.setIcon(ApplicationConstants.ICON_CLEAN);
        cleanItem.addClickHandler(new ClickHandler() {

            public void onClick(MenuItemClickEvent event) {
                SC.confirm("Do you really want to clean this simulation ("
                        + title + ")?", new BooleanCallback() {

                    public void execute(Boolean value) {
                        if (value != null && value) {
                            cleanSimulation();
                        }
                    }
                });

            }
        });

        MenuItem purgeItem = new MenuItem("Purge Simulation");
        purgeItem.setIcon(CoreConstants.ICON_CLEAR);
        purgeItem.addClickHandler(new ClickHandler() {

            public void onClick(MenuItemClickEvent event) {
                SC.confirm("Do you really want to purge this simulation ("
                        + title + ")?", new BooleanCallback() {

                    public void execute(Boolean value) {
                        if (value != null && value) {
                            purgeSimulation();
                        }
                    }
                });

            }
        });

        if (status == SimulationStatus.Running) {
            this.setItems(viewItem, killItem);
        } else if (status == SimulationStatus.Completed || status == SimulationStatus.Killed) {
            this.setItems(viewItem, cleanItem);
        } else if (status == SimulationStatus.Cleaned) {
            this.setItems(viewItem, purgeItem);
        }
    }
    
    /**
     * Sends a request to kill the simulation
     */
    private void killSimulation() {
        
        WorkflowServiceAsync service = WorkflowService.Util.getInstance();
        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                modal.hide();
                SC.warn("Unable to kill simulation:<br />" + caught.getMessage());
            }

            public void onSuccess(Void result) {
                modal.hide();
                getSimulationsTab().loadData();
            }
        };
        service.killWorkflow(simulationID, callback);
        modal.show("Sending killing signal to " + title + "...", true);
    }
    
    /**
     * Sends a request to clean the simulation
     */
    private void cleanSimulation() {
        
        WorkflowServiceAsync service = WorkflowService.Util.getInstance();
        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                modal.hide();
                SC.warn("Unable to clean simulation:<br />" + caught.getMessage());
            }

            public void onSuccess(Void result) {
                modal.hide();
                getSimulationsTab().loadData();
            }
        };
        service.cleanWorkflow(simulationID, callback);
        modal.show("Cleaning simulation " + title + "...", true);
    }
    
    /**
     * Sends a request to purge the simulation
     */
    private void purgeSimulation() {
        
        WorkflowServiceAsync service = WorkflowService.Util.getInstance();
        final AsyncCallback<Void> callback = new AsyncCallback<Void>() {

            public void onFailure(Throwable caught) {
                modal.hide();
                SC.warn("Unable to purge simulation:<br />" + caught.getMessage());
            }

            public void onSuccess(Void result) {
                modal.hide();
                getSimulationsTab().loadData();
            }
        };
        service.purgeWorkflow(simulationID, callback);
        modal.show("Purging simulation " + title + "...", true);
    }
    
    private SimulationsTab getSimulationsTab() {
        return (SimulationsTab) Layout.getInstance().getTab(ApplicationConstants.TAB_MONITOR);
    }
}
