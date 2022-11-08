package com.example.microservice_scrap_rss.cassandra;

import static com.datastax.driver.core.querybuilder.QueryBuilder.bindMarker;
import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

import com.datastax.driver.core.querybuilder.Delete;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.relation.Relation;
import com.datastax.oss.driver.api.querybuilder.schema.CreateTable;
import com.example.microservice_scrap_rss.ProjectConstants;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.Columns;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public class FeedByUserRepo {
    private final CqlSession session;
    private final CassandraOperations template;


    public FeedByUserRepo(CqlSession session) {
        this.session = session;
        this.template = new CassandraTemplate(session);
    }

    private ResultSet executeStatement(SimpleStatement statement, String keyspace) {
        if (keyspace != null) {
            statement.setKeyspace(CqlIdentifier.fromCql(keyspace));
        }

        return session.execute(statement);
    }

    public void createTable(String keyspace) {
        CreateTable createTable = SchemaBuilder.createTable(ProjectConstants.TABLE_FEED_BY_USER.env()).ifNotExists()
                .withPartitionKey("userId", DataTypes.UUID)
                .withClusteringColumn("feedId", DataTypes.UUID);
        executeStatement(createTable.build(), keyspace);
    }



    public FeedByUser insertFeedToUser(UUID userId, UUID feedId) {
        var a= new FeedByUser(userId,feedId);
        template.insert(a);
        return a;
    }

    public void removeFeedFromUser(UUID userId, UUID feedID) {
        var delete =  QueryBuilder.deleteFrom(ProjectConstants.KEYSPACE.env(),"feedbyuser")
                .whereColumn("userId")
                .isEqualTo(literal(userId)).
                whereColumn("feedid").
                isEqualTo(literal(feedID));
        executeStatement(delete.build(), ProjectConstants.KEYSPACE.env());
    }

    public List<FeedByUser> getAllFeedsOf(UUID userId) {
        return template.select(Query.query(Criteria.where("userid").is(userId)), FeedByUser.class);
    }
}
