/*
 * Copyright and authors: see LICENSE.txt in base repository.
 *
 * This software is a web portal for pipeline execution on distributed systems.
 *
 * This software is governed by the CeCILL-B license under French law and
 * abiding by the rules of distribution of free software.  You can  use, 
 * modify and/ or redistribute the software under the terms of the CeCILL-B
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
 * knowledge of the CeCILL-B license and that you accept its terms.
 */
package fr.insalyon.creatis.vip.cowork.client.view;

import com.google.gwt.user.client.Cookies;
import com.smartgwt.client.widgets.HTMLPane;
import fr.insalyon.creatis.vip.core.client.view.CoreConstants;

/**
 *
 * @author Rafael Ferreira da Silva
 */
public class AppletHTMLPane extends HTMLPane {

    public AppletHTMLPane(String title, String code, String archive, int width,
            int height) {

        this.setWidth100();
        this.setHeight100();
        this.setShowEdges(true);
       
        this.setContents(
                "<html>"
                + "<head>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; "
                + "charset=UTF-8\">"
                + "<title>" + title + "</title>"
                + "</head>"
                + "<body>"
                + "<applet width=\"" + width + "\" height=\"" + height + "\" "
                + "code=\"" + code + "\" "
                + "archive=\"applets/" + archive + "\" MAYSCRIPT>"
                + "<param name=\"sessionId\" value=\"" +Cookies.getCookie(CoreConstants.COOKIES_SESSION) + "\"/>"
                + "<param name=\"email\" value=\"" + Cookies.getCookie(CoreConstants.COOKIES_USER) + "\"/>"
                + "</applet>"
                + "</body>"
                + "</html>");
    }
}