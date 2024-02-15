package com.journal.journaling.repository;

import com.journal.journaling.entity.JournalEntry;
import com.journal.journaling.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {




}
