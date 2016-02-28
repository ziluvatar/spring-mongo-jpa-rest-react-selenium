package com.eduardods.companies.acceptance.support;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class DBClient {

  private static DBClient INSTANCE = new DBClient();

  private DBCollection databaseCollection;

  private DBClient() {
    try {
      MongoClient mongoClient = new MongoClient( new MongoClientURI(getMongoUri()) );
      DB database = mongoClient.getDB("companies-test");
      this.databaseCollection = database.getCollection("companies");
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }
  }

  public static DBClient getInstance() {
    return INSTANCE;
  }

  public void clearDatabase() {
    databaseCollection.drop();
  }

  public static String getMongoUri() {
    return "mongodb://localhost/companies-test";
  }
}
