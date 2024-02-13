package com.journal.journaling.service;

import com.journal.journaling.entity.JournalEntry;
import com.journal.journaling.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;



    public JournalEntry saveEntry(JournalEntry journalEntry) {
        return journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getEntries() {
        return journalEntryRepository.findAll();
    }


    public Optional<JournalEntry> findById(ObjectId journalEntryId) {
        return journalEntryRepository.findById(journalEntryId);
    }

    public boolean deleteById(ObjectId journalEntryId) {
        journalEntryRepository.deleteById(journalEntryId);
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
}
