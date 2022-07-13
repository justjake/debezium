/*
 * Copyright Debezium Authors.
 *
 * Licensed under the Apache Software License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package io.debezium.schema;

import java.util.Properties;

import io.debezium.config.CommonConnectorConfig;
import io.debezium.spi.schema.DataCollectionId;
import io.debezium.util.Collect;

public class DefaultTopicNamingStrategy extends AbstractTopicNamingStrategy<DataCollectionId> {

    public DefaultTopicNamingStrategy(Properties props) {
        super(props);
    }

    public DefaultTopicNamingStrategy(Properties props, String logicalName) {
        super(props, logicalName);
    }

    public static DefaultTopicNamingStrategy create(CommonConnectorConfig config) {
        return new DefaultTopicNamingStrategy(config.getConfig().asProperties(), config.getLogicalName());
    }

    @Override
    public String dataChangeTopic(DataCollectionId id) {
        String topicName = mkString(Collect.arrayListOf(prefix, id.databaseParts()), delimiter);
        return topicNames.computeIfAbsent(id, t -> sanitizedTopicName(topicName));
    }
}