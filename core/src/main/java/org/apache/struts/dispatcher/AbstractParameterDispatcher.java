/*
 * $Id$
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.struts.dispatcher;

import org.apache.struts.chain.contexts.ActionContext;

/**
 * <p>
 * This abstract class is a template for choosing the target method based on a
 * servlet request parameter. It is based upon the functionality of
 * <code>org.apache.struts.actions.DispatchAction</code>.
 * </p>
 * <p>
 * To configure the use of this dispatcher in your configuration, create an
 * entry like below. The dispatcher will use the value of the request parameter
 * named "method" (or whatever specified) to pick the appropriate method on the
 * action.
 * </p>
 * 
 * <code>
 * &lt;action path=&quot;/saveSubscription&quot; 
 *            dispatcher=&quot;org.apache.struts.dispatcher.servlet.ServletParameterDispatcher&quot;
 *            name=&quot;subscriptionForm&quot; 
 *            scope=&quot;request&quot; 
 *            input=&quot;/subscription.jsp&quot;
 *            parameter=&quot;method&quot;/&gt;
 * </code>
 * <p>
 * 
 * @version $Rev$
 * @since Struts 1.4
 */
public abstract class AbstractParameterDispatcher extends AbstractDispatcher {

    /**
     * The default parameter name if no parameter is set.
     * 
     * @see #getDefaultParameterName()
     */
    protected static final String DEFAULT_PARAMETER_NAME = "method";

    /**
     * Retrieves the name of the parameter to fallback upon if the action
     * configuration did not set the <code>parameter</code> attribute. The
     * default implementation returns {@value #DEFAULT_PARAMETER_NAME}.
     * 
     * @return the fallback parameter name; can be <code>null</code>
     * @see #DEFAULT_PARAMETER_NAME
     * @see #getParameter(ActionContext)
     */
    protected String getDefaultParameterName() {
	return DEFAULT_PARAMETER_NAME;
    }

    /**
     * Retrieves the parameter name whose value will identity the intended
     * method to dispatch. The value is not necessarily a method name, but a key
     * that can resolve to one. The default implementation returns the mapping's
     * <code>parameter</code> attribute; otherwise fallback to the default
     * parameter name.
     * 
     * @param context the action context
     * @return the mapping's parameter name
     * @see #getDefaultParameterName()
     */
    protected String getParameter(ActionContext context) {
	String parameter = context.getActionConfig().getParameter();
	if ((parameter != null) && (parameter.trim().length() > 0)) {
	    return parameter;
	}
	return getDefaultParameterName();
    }

    protected final String resolveMethodName(ActionContext context) {
	String parameter = getParameter(context);
	if ("".equals(parameter)) {
	    parameter = null;
	}

	if (parameter != null) {
	    return resolveParameterValue(context, parameter);
	}

	return null;
    }

    protected abstract String resolveParameterValue(ActionContext context, String parameter);

}