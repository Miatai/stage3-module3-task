package com.mjc.school.repository.utils;

import java.util.Set;

public class NewsParams implements Params{
    private String title;
    private String content;
    private String authorName;
    private Set<Long> tagIds;
    private Set<String> tagNames;

    public NewsParams() {
    }

    public NewsParams(String title, String content, String authorName, Set<Long> tagIds, Set<String> tagNames) {
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        this.tagIds = tagIds;
        this.tagNames = tagNames;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Set<Long> getTagIds() {
        return tagIds;
    }

    public void setTagIds(Set<Long> tagIds) {
        this.tagIds = tagIds;
    }

    public Set<String> getTagNames() {
        return tagNames;
    }

    public void setTagNames(Set<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((content == null) ? 0 : content.hashCode());
        result = prime * result + ((authorName == null) ? 0 : authorName.hashCode());
        result = prime * result + ((tagIds == null) ? 0 : tagIds.hashCode());
        result = prime * result + ((tagNames == null) ? 0 : tagNames.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        NewsParams other = (NewsParams) obj;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        if (authorName == null) {
            if (other.authorName != null)
                return false;
        } else if (!authorName.equals(other.authorName))
            return false;
        if (tagIds == null) {
            if (other.tagIds != null)
                return false;
        } else if (!tagIds.equals(other.tagIds))
            return false;
        if (tagNames == null) {
            if (other.tagNames != null)
                return false;
        } else if (!tagNames.equals(other.tagNames))
            return false;
        return true;
    }
    
}
