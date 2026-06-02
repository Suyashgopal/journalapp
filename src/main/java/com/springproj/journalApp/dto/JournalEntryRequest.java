package com.springproj.journalApp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** Validated payload for creating/updating a journal entry. */
public class JournalEntryRequest {

    @NotBlank(message = "title is required")
    @Size(max = 200, message = "title must be at most 200 characters")
    private String title;

    @NotBlank(message = "content is required")
    private String content;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
