package com.journal.journaling.service;

import com.journal.journaling.entity.JournalEntry;
import com.journal.journaling.entity.User;
import com.journal.journaling.repository.JournalEntryRepository;
import com.journal.journaling.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;



    public JournalEntry saveEntry(JournalEntry journalEntry, String username) {

        User user = userRepository.findByUsername(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);

        user.getJournalEntries().add(saved);
        userRepository.save(user);
        return saved;
    }
    public JournalEntry saveEntry(JournalEntry journalEntry) {

        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepository.save(journalEntry);

        return saved;
    }

    public List<JournalEntry> getEntries() {
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> findById(ObjectId journalEntryId) {
        return journalEntryRepository.findById(journalEntryId);
    }

    public boolean deleteById(ObjectId journalEntryId, String username) {
        User user = userRepository.findByUsername(username);
        journalEntryRepository.deleteById(journalEntryId);

        user.getJournalEntries().removeIf(x -> x.getId().equals(journalEntryId));
        userRepository.save(user);
        return true;
    }

    public JournalEntry updateEntry(ObjectId journalEntryId, JournalEntry journalEntry) {
        JournalEntry entryFound = this.findById(journalEntryId).orElse(null);

        if (entryFound == null) {
            throw new NullPointerException("Entry not found");
        }

        if (journalEntry.getTitle() != null && !journalEntry.getTitle().isEmpty()) {
            entryFound.setTitle(journalEntry.getTitle());
        }

        if (journalEntry.getContent() != null && !journalEntry.getContent().isEmpty()) {
            entryFound.setContent(journalEntry.getContent());
        }

        this.saveEntry(entryFound);
        return entryFound;

    }

//    public Optional<JournalEntry> findEntryByUser(ObjectId journalEntryId, String username) {
//        User user = userRepository.findByUsername(username);
//
//        if (user != null) {
//            user.getJournalEntries().forEach(x -> x.getId().equals(journalEntryId));
//        }
//    }
}
