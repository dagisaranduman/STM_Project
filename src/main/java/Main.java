import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        User user = createUser();
        DBObject document = createDBObject(user);
        MongoClient mongo = new MongoClient("localhost", 27017);
        DB db = mongo.getDB("mydatabase");

        DBCollection col = db.getCollection("Users");

        /**
         * creating user
         */
        WriteResult result = col.insert(document);
        System.out.println(" User create successful!");
        /**
         * id=3 (manuel olarak girdiğimiz) olanları sorgu ile getiriyor
         */
        DBObject query = BasicDBObjectBuilder.start().add("_id", user.getId()).get();

        DBCursor curs = col.find(query);

        while(curs.hasNext()){
            System.out.println(curs.next());
        }

        /**
         * updating example
         */
        user.setName("Rumeysa Oz");
        document = createDBObject(user);
        result = col.update(query, document);

        System.out.println(" Update successful!");

        /**
         * deleting example
         */
        result = col.remove(query);

        System.out.println(" Delete successful!");

        /**
         * close resources
         */
        mongo.close();
    }

    private static DBObject createDBObject(User user) {
        BasicDBObjectBuilder Builder = BasicDBObjectBuilder.start();

        Builder.append("_id", user.getId());
        Builder.append("name", user.getName());
        Builder.append("role", user.getRole());
        Builder.append("isEmployee", user.isEmployee());
        return Builder.get();
    }

    /**
     * creating user with its id-name-isEmployee-job_role
     * @return user
     */
    private static User createUser() {
        User user = new User();
        user.setId(3);
        user.setName("Rumeysa B");
        user.setEmployee(true);
        user.setRole("Big Data B");
        return user;
    }

    }

