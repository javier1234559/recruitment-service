package vn.unigap.api.repository.mongodb;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.unigap.api.entity.mongodb.RequestResponseLogging;

@Repository
public interface RequestResponseLoggingRepository extends MongoRepository<RequestResponseLogging, String> {

}
