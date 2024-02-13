package com.journal.journaling.controller;

import com.journal.journaling.entity.JournalEntry;
import com.journal.journaling.repository.JournalEntryRepository;
import com.journal.journaling.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping(path = "/journal")
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;


    @GetMapping
    public List<JournalEntry> getEntries() {
        return journalEntryService.getEntries();
    }

    @PostMapping
    public JournalEntry addEntry(@RequestBody JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());

        return journalEntryService.saveEntry(journalEntry);

    }

    @GetMapping("{journalEntryId}")
    public Optional<JournalEntry> getEntry(@PathVariable(name = "journalEntryId") ObjectId journalEntryId) {

        return journalEntryService.findById(journalEntryId);
    }

    @DeleteMapping("{journalEntryId}")
    public boolean deleteEntry(@PathVariable(name = "journalEntryId") ObjectId journalEntryId) {
        return journalEntryService.deleteById(journalEntryId);
    }

    @PutMapping("{journalEntryId}")
    public JournalEntry editEntry(@PathVariable(name = "journalEntryId") ObjectId journalEntryId, @RequestBody JournalEntry journalEntry) {
        return journalEntryService.updateEntry(journalEntryId, journalEntry);
    }



}
