package com.journal.journaling.controller;

import com.journal.journaling.entity.JournalEntry;
import com.journal.journaling.entity.User;
import com.journal.journaling.repository.JournalEntryRepository;
import com.journal.journaling.service.JournalEntryService;
import com.journal.journaling.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping(path = "/journal")
public class JournalEntryControllerV2 {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;


    @GetMapping("{username}")
    public ResponseEntity<List<JournalEntry>> getEntries(@PathVariable(name = "username") String username) {

        User user = userService.findByUsername(username);

        List<JournalEntry> entries = user.getJournalEntries();

        return new ResponseEntity<>(entries, HttpStatus.OK);
    }

    @PostMapping("{username}")
    public ResponseEntity<JournalEntry> addEntry(@RequestBody JournalEntry journalEntry, @PathVariable(name = "username") String username) {

        JournalEntry entry = journalEntryService.saveEntry(journalEntry, username);
        return new ResponseEntity<>(entry, HttpStatus.CREATED);

    }

    @GetMapping("id/{journalEntryId}")
    public ResponseEntity<JournalEntry> getEntry(@PathVariable(name = "journalEntryId") ObjectId journalEntryId) {
        Optional<JournalEntry> entry = journalEntryService.findById(journalEntryId);

        if (entry.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entry.get(), HttpStatus.OK);

    }

    @DeleteMapping("{username}/{journalEntryId}")
    public ResponseEntity<JournalEntry> deleteEntryByUser(@PathVariable(name = "username") String username, @PathVariable(name = "journalEntryId") ObjectId journalEntryId) {

        Optional<JournalEntry> entry = journalEntryService.findById(journalEntryId);

        if (entry.isPresent()) {
            journalEntryService.deleteById(journalEntryId, username);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping("{username}/{journalEntryId}")
    public ResponseEntity<JournalEntry> editEntry(@PathVariable(name = "journalEntryId") ObjectId journalEntryId, @RequestBody JournalEntry journalEntry) {
        JournalEntry entry = journalEntryService.updateEntry(journalEntryId, journalEntry);
        return new ResponseEntity<>(entry, HttpStatus.OK);
    }



}
