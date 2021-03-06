/*
 * Copyright 2010 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.eclipse.flow.ruleflow.view.property.workitem;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.jbpm.workflow.core.node.WorkItemNode;

public class WorkItemResultMappingPropertyDescriptor extends PropertyDescriptor {

    private WorkItemNode workItemNode;
    
    public WorkItemResultMappingPropertyDescriptor(Object id, String displayName, WorkItemNode workItemNode) {
        super(id, displayName);
        this.workItemNode = workItemNode;
    }
    
    public CellEditor createPropertyEditor(Composite parent) {
        WorkItemParameterMappingCellEditor editor = new WorkItemParameterMappingCellEditor(parent, workItemNode);
        if (getValidator() != null) {
            editor.setValidator(getValidator());
        }
        return editor;
    }
    
}
