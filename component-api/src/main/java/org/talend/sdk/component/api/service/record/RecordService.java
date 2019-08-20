/**
 * Copyright (C) 2006-2019 Talend Inc. - www.talend.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.talend.sdk.component.api.service.record;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collector;

import org.talend.sdk.component.api.record.Record;
import org.talend.sdk.component.api.record.Schema;

/**
 * Helping class to build records.
 */
public interface RecordService {

    /**
     * Forward an entry from the source record if it exists.
     *
     * @param source the source record to read data from.
     * @param builder the current builder to fill.
     * @param sourceColumn the column name.
     * @param entry the entry to use for the output column.
     * @return true if the entry was forwarded.
     */
    boolean forwardEntry(Record source, Record.Builder builder, String sourceColumn, Schema.Entry entry);

    /**
     * Method providing a collector enabling to create a record from another one in a custom fashion.
     *
     * @param schema the schema of the record being built.
     * @param fallbackRecord the source record used when the custom handler does not handle current entry.
     * @param customHandler a processor of entry enabling to inject custom data in the record being built.
     * @param beforeFinish a callback before the record is actually built, it enables to add data if not already there.
     * @return a collector enabling to build a record.
     */
    Collector<Schema.Entry, Record.Builder, Record> toRecord(Schema schema, Record fallbackRecord,
            BiFunction<Schema.Entry, Record.Builder, Boolean> customHandler,
            BiConsumer<Record.Builder, Boolean> beforeFinish);

    /**
     * Shortcut to build a record using {@link RecordService#toRecord(Schema, Record, BiFunction, BiConsumer)}.
     *
     * @param schema the schema of the record being built.
     * @param fallbackRecord the source record used when the custom handler does not handle current entry.
     * @param customHandler a processor of entry enabling to inject custom data in the record being built.
     * @param beforeFinish a callback before the record is actually built, it enables to add data if not already there.
     * @return a collector enabling to build a record.
     */
    Record create(Schema schema, Record fallbackRecord, BiFunction<Schema.Entry, Record.Builder, Boolean> customHandler,
            BiConsumer<Record.Builder, Boolean> beforeFinish);
}
