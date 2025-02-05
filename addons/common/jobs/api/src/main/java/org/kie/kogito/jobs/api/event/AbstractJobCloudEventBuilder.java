/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

package org.kie.kogito.jobs.api.event;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.UUID;

import io.cloudevents.CloudEvent;

public abstract class AbstractJobCloudEventBuilder<B extends AbstractJobCloudEventBuilder<B, T, E>, T, E extends JobCloudEvent<T>> {

    protected E event;

    protected AbstractJobCloudEventBuilder(E event) {
        this.event = event;
        this.event.setSpecVersion(JobCloudEvent.SPEC_VERSION);
        this.event.setId(UUID.randomUUID().toString());
        this.event.setTime(ZonedDateTime.now());
    }

    public B specVersion(String specVersion) {
        event.setSpecVersion(specVersion);
        return cast();
    }

    public B id(String id) {
        event.setId(id);
        return cast();
    }

    public B source(URI source) {
        event.setSource(source);
        return cast();
    }

    public B type(String type) {
        event.setType(type);
        return cast();
    }

    public B time(ZonedDateTime time) {
        event.setTime(time);
        return cast();
    }

    public B subject(String subject) {
        event.setSubject(subject);
        return cast();
    }

    public B dataContentType(String dataContentType) {
        event.setDataContentType(dataContentType);
        return cast();
    }

    public B dataSchema(String dataSchema) {
        event.setDataSchema(dataSchema);
        return cast();
    }

    public B data(T data) {
        event.setData(data);
        return cast();
    }

    @SuppressWarnings("squid:S2583")
    public B withValuesFrom(CloudEvent cloudEvent) {
        return specVersion(cloudEvent.getSpecVersion().toString())
                .id(cloudEvent.getId())
                .source(cloudEvent.getSource())
                .type(cloudEvent.getType())
                .time(cloudEvent.getTime() != null ? cloudEvent.getTime().toZonedDateTime() : null)
                .dataContentType(cloudEvent.getDataContentType())
                .dataSchema(cloudEvent.getDataSchema() != null ? cloudEvent.getDataSchema().toString() : null)
                .subject(cloudEvent.getSubject());
    }

    public E build() {
        return event;
    }

    @SuppressWarnings("unchecked")
    protected B cast() {
        return (B) this;
    }
}
