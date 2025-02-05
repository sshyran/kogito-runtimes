/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jbpm.process.instance.event;

import java.util.List;

import org.drools.core.event.ProcessVariableChangedEventImpl;
import org.kie.api.runtime.KieRuntime;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.kogito.internal.process.event.KogitoProcessVariableChangedEvent;
import org.kie.kogito.internal.process.runtime.KogitoNodeInstance;

public class KogitoProcessVariableChangedEventImpl extends ProcessVariableChangedEventImpl implements KogitoProcessVariableChangedEvent {

    private KogitoNodeInstance nodeInstance;

    public KogitoProcessVariableChangedEventImpl(final String id, final String instanceId,
            final Object oldValue, final Object newValue, List<String> tags,
            final ProcessInstance processInstance, KogitoNodeInstance nodeInstance, KieRuntime kruntime) {
        super(id, instanceId, oldValue, newValue, tags, processInstance, kruntime);
        this.nodeInstance = nodeInstance;
    }

    @Override
    public KogitoNodeInstance getNodeInstance() {
        return this.nodeInstance;
    }

    public String toString() {
        return "==>[ProcessVariableChanged(id=" + getVariableId() + "; instanceId=" + getVariableInstanceId() + "; oldValue=" + getOldValue() + "; newValue=" + getNewValue()
                + "; processName=" + getProcessInstance().getProcessName() + "; processId=" + getProcessInstance().getProcessId() + ")]";
    }
}